package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AcademyData
import com.example.data.AcademyRepository
import com.example.data.AppDatabase
import com.example.data.BatchMode
import com.example.data.BusinessConfig
import com.example.data.DurationOption
import com.example.data.EnquiryEntity
import com.example.data.ExamCourse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLEncoder

class AcademyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AcademyRepository

    init {
        val dao = AppDatabase.getDatabase(application).enquiryDao()
        repository = AcademyRepository(dao)
    }

    val savedEnquiries: StateFlow<List<EnquiryEntity>> = repository.allEnquiries
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Filter category for Courses grid
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Course Detail Modal state
    private val _detailCourse = MutableStateFlow<ExamCourse?>(null)
    val detailCourse: StateFlow<ExamCourse?> = _detailCourse.asStateFlow()

    // Estimator State
    private val _estimatorCourse = MutableStateFlow<ExamCourse>(AcademyData.courses.first())
    val estimatorCourse: StateFlow<ExamCourse> = _estimatorCourse.asStateFlow()

    private val _estimatorMode = MutableStateFlow(BatchMode.OFFLINE)
    val estimatorMode: StateFlow<BatchMode> = _estimatorMode.asStateFlow()

    private val _estimatorDuration = MutableStateFlow(DurationOption.SIX_MONTHS)
    val estimatorDuration: StateFlow<DurationOption> = _estimatorDuration.asStateFlow()

    private val _estimatedFeeRange = MutableStateFlow(Pair(16000, 20000))
    val estimatedFeeRange: StateFlow<Pair<Int, Int>> = _estimatedFeeRange.asStateFlow()

    // Enquiry Modal State
    private val _isEnquiryModalOpen = MutableStateFlow(false)
    val isEnquiryModalOpen: StateFlow<Boolean> = _isEnquiryModalOpen.asStateFlow()

    // Form Fields
    private val _formName = MutableStateFlow("")
    val formName: StateFlow<String> = _formName.asStateFlow()

    private val _formPhone = MutableStateFlow("")
    val formPhone: StateFlow<String> = _formPhone.asStateFlow()

    private val _formCourse = MutableStateFlow(AcademyData.courses.first().title)
    val formCourse: StateFlow<String> = _formCourse.asStateFlow()

    private val _formMode = MutableStateFlow(BatchMode.OFFLINE.displayName)
    val formMode: StateFlow<String> = _formMode.asStateFlow()

    private val _formQualification = MutableStateFlow("Graduate")
    val formQualification: StateFlow<String> = _formQualification.asStateFlow()

    private val _formNotes = MutableStateFlow("")
    val formNotes: StateFlow<String> = _formNotes.asStateFlow()

    // Errors & Submit Feedback
    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _phoneError = MutableStateFlow<String?>(null)
    val phoneError: StateFlow<String?> = _phoneError.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _submissionSuccessMessage = MutableStateFlow<String?>(null)
    val submissionSuccessMessage: StateFlow<String?> = _submissionSuccessMessage.asStateFlow()

    init {
        recalculateFee()
    }

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
    }

    fun openCourseDetail(course: ExamCourse) {
        _detailCourse.value = course
    }

    fun closeCourseDetail() {
        _detailCourse.value = null
    }

    // Estimator setters
    fun setEstimatorCourse(course: ExamCourse) {
        _estimatorCourse.value = course
        recalculateFee()
    }

    fun setEstimatorMode(mode: BatchMode) {
        _estimatorMode.value = mode
        recalculateFee()
    }

    fun setEstimatorDuration(duration: DurationOption) {
        _estimatorDuration.value = duration
        recalculateFee()
    }

    private fun recalculateFee() {
        _estimatedFeeRange.value = repository.calculateFeeRange(
            _estimatorCourse.value,
            _estimatorMode.value,
            _estimatorDuration.value
        )
    }

    // Modal Control
    fun openEnquiryModal(
        prefilledCourse: String? = null,
        prefilledMode: String? = null
    ) {
        prefilledCourse?.let { _formCourse.value = it } ?: run {
            _formCourse.value = _estimatorCourse.value.title
        }
        prefilledMode?.let { _formMode.value = it } ?: run {
            _formMode.value = _estimatorMode.value.displayName
        }
        _nameError.value = null
        _phoneError.value = null
        _submissionSuccessMessage.value = null
        _isEnquiryModalOpen.value = true
    }

    fun closeEnquiryModal() {
        _isEnquiryModalOpen.value = false
    }

    fun updateFormName(value: String) {
        _formName.value = value
        if (value.isNotBlank()) _nameError.value = null
    }

    fun updateFormPhone(value: String) {
        _formPhone.value = value
        if (value.length >= 10) _phoneError.value = null
    }

    fun updateFormCourse(value: String) {
        _formCourse.value = value
    }

    fun updateFormMode(value: String) {
        _formMode.value = value
    }

    fun updateFormQualification(value: String) {
        _formQualification.value = value
    }

    fun updateFormNotes(value: String) {
        _formNotes.value = value
    }

    fun submitEnquiryAndLaunchWhatsApp(context: Context) {
        val name = _formName.value.trim()
        val phone = _formPhone.value.trim()

        var hasError = false
        if (name.isBlank()) {
            _nameError.value = "Please enter your full name"
            hasError = true
        }
        if (phone.isBlank() || phone.length < 10) {
            _phoneError.value = "Please enter a valid 10-digit phone number"
            hasError = true
        }

        if (hasError) return

        _isSubmitting.value = true

        val course = _formCourse.value
        val mode = _formMode.value
        val qual = _formQualification.value
        val notes = _formNotes.value

        // Save enquiry to Room Database
        viewModelScope.launch {
            repository.saveEnquiry(
                EnquiryEntity(
                    fullName = name,
                    phone = phone,
                    courseTitle = course,
                    batchMode = mode,
                    duration = _estimatorDuration.value.displayName,
                    qualification = qual,
                    notes = notes
                )
            )

            // Construct formatted WhatsApp Message String
            val rawMessage = """
                *NEW ENQUIRY - SUCCESS ACADEMY*
                -----------------------------------
                👤 *Name:* $name
                📞 *Phone:* $phone
                📚 *Course Interested:* $course
                🏫 *Batch Mode:* $mode
                🎓 *Qualification:* $qual
                ${if (notes.isNotBlank()) "📝 *Notes:* $notes\n" else ""}-----------------------------------
                _Sent via Success Academy App_
            """.trimIndent()

            val encodedMessage = URLEncoder.encode(rawMessage, "UTF-8")
            val whatsappUrl = "https://wa.me/${BusinessConfig.WHATSAPP_NUMBER}?text=$encodedMessage"

            _submissionSuccessMessage.value = "Enquiry saved! Redirecting to WhatsApp..."

            // Open WhatsApp Intent
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            _isSubmitting.value = false
            _isEnquiryModalOpen.value = false

            // Reset form
            _formName.value = ""
            _formPhone.value = ""
            _formNotes.value = ""
        }
    }

    fun makeDirectCall(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${BusinessConfig.PHONE_TEL}"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openMapLocation(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BusinessConfig.MAPS_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
