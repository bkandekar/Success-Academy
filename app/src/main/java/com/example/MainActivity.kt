package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AcademyViewModel
import com.example.ui.components.CourseDetailModal
import com.example.ui.components.CoursesSection
import com.example.ui.components.EnquiryModal
import com.example.ui.components.FacultySection
import com.example.ui.components.FeeEstimatorSection
import com.example.ui.components.FinalCtaSection
import com.example.ui.components.FooterSection
import com.example.ui.components.HeaderBar
import com.example.ui.components.HeroSection
import com.example.ui.components.PainPointsSection
import com.example.ui.components.TestimonialsSection
import com.example.ui.components.ToppersSection
import com.example.ui.components.WhyChooseUsSection
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.SuccessAcademyTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuccessAcademyTheme {
                SuccessAcademyApp()
            }
        }
    }
}

@Composable
fun SuccessAcademyApp(
    viewModel: AcademyViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // ViewModel Flow States
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val detailCourse by viewModel.detailCourse.collectAsStateWithLifecycle()

    val estimatorCourse by viewModel.estimatorCourse.collectAsStateWithLifecycle()
    val estimatorMode by viewModel.estimatorMode.collectAsStateWithLifecycle()
    val estimatorDuration by viewModel.estimatorDuration.collectAsStateWithLifecycle()
    val estimatedFeeRange by viewModel.estimatedFeeRange.collectAsStateWithLifecycle()

    val isEnquiryModalOpen by viewModel.isEnquiryModalOpen.collectAsStateWithLifecycle()
    val formName by viewModel.formName.collectAsStateWithLifecycle()
    val formPhone by viewModel.formPhone.collectAsStateWithLifecycle()
    val formCourse by viewModel.formCourse.collectAsStateWithLifecycle()
    val formMode by viewModel.formMode.collectAsStateWithLifecycle()
    val formQualification by viewModel.formQualification.collectAsStateWithLifecycle()
    val formNotes by viewModel.formNotes.collectAsStateWithLifecycle()

    val nameError by viewModel.nameError.collectAsStateWithLifecycle()
    val phoneError by viewModel.phoneError.collectAsStateWithLifecycle()
    val isSubmitting by viewModel.isSubmitting.collectAsStateWithLifecycle()
    val submissionSuccessMessage by viewModel.submissionSuccessMessage.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeaderBar(
                onEnquireClick = { viewModel.openEnquiryModal() },
                onCallClick = { viewModel.makeDirectCall(context) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // 1. Hero Section
                HeroSection(
                    onEnquireClick = { viewModel.openEnquiryModal() },
                    onEstimateClick = {
                        // Scroll down to fee estimator section (~800px offset)
                        coroutineScope.launch {
                            scrollState.animateScrollTo(850)
                        }
                    }
                )

                // 2. Pain Points vs Solutions Section
                PainPointsSection()

                // 3. Course Fee & Batch Estimator Section
                FeeEstimatorSection(
                    selectedCourse = estimatorCourse,
                    selectedMode = estimatorMode,
                    selectedDuration = estimatorDuration,
                    estimatedFeeRange = estimatedFeeRange,
                    onCourseSelected = { viewModel.setEstimatorCourse(it) },
                    onModeSelected = { viewModel.setEstimatorMode(it) },
                    onDurationSelected = { viewModel.setEstimatorDuration(it) },
                    onEnquireBatchClick = {
                        viewModel.openEnquiryModal(
                            prefilledCourse = estimatorCourse.title,
                            prefilledMode = estimatorMode.displayName
                        )
                    }
                )

                // 4. Courses & Batches Catalog Section
                CoursesSection(
                    selectedCategoryFilter = selectedCategory,
                    onCategoryFilterSelect = { viewModel.setCategoryFilter(it) },
                    onEnquireCourseClick = { course ->
                        viewModel.openEnquiryModal(
                            prefilledCourse = course.title,
                            prefilledMode = estimatorMode.displayName
                        )
                    },
                    onViewDetailClick = { course ->
                        viewModel.openCourseDetail(course)
                    }
                )

                // 5. Why Choose Us / Animated Stats Counter Section
                WhyChooseUsSection()

                // 6. Faculty Profile Cards Section
                FacultySection()

                // 7. Results & Toppers Gallery Section
                ToppersSection()

                // 8. Student & Parent Reviews / Testimonials Section
                TestimonialsSection()

                // 9. Final Call-To-Action Banner Section
                FinalCtaSection(
                    onEnquireClick = { viewModel.openEnquiryModal() },
                    onCallClick = { viewModel.makeDirectCall(context) }
                )

                // 10. Footer Section with Contact Details, Map Launcher & Credit Line
                FooterSection(
                    onCallClick = { viewModel.makeDirectCall(context) },
                    onMapClick = { viewModel.openMapLocation(context) }
                )
            }

            // Modals & Overlays
            EnquiryModal(
                isOpen = isEnquiryModalOpen,
                onDismiss = { viewModel.closeEnquiryModal() },
                name = formName,
                phone = formPhone,
                course = formCourse,
                mode = formMode,
                qualification = formQualification,
                notes = formNotes,
                nameError = nameError,
                phoneError = phoneError,
                isSubmitting = isSubmitting,
                successMessage = submissionSuccessMessage,
                onNameChange = { viewModel.updateFormName(it) },
                onPhoneChange = { viewModel.updateFormPhone(it) },
                onCourseChange = { viewModel.updateFormCourse(it) },
                onModeChange = { viewModel.updateFormMode(it) },
                onQualificationChange = { viewModel.updateFormQualification(it) },
                onNotesChange = { viewModel.updateFormNotes(it) },
                onSubmitClick = { viewModel.submitEnquiryAndLaunchWhatsApp(context) }
            )

            CourseDetailModal(
                course = detailCourse,
                onDismiss = { viewModel.closeCourseDetail() },
                onEnquireClick = { course ->
                    viewModel.openEnquiryModal(
                        prefilledCourse = course.title,
                        prefilledMode = estimatorMode.displayName
                    )
                }
            )
        }
    }
}

