package com.example.data

import kotlinx.coroutines.flow.Flow

class AcademyRepository(private val enquiryDao: EnquiryDao) {

    val allEnquiries: Flow<List<EnquiryEntity>> = enquiryDao.getAllEnquiries()

    suspend fun saveEnquiry(enquiry: EnquiryEntity): Long {
        return enquiryDao.insertEnquiry(enquiry)
    }

    suspend fun deleteEnquiry(id: Int) {
        enquiryDao.deleteEnquiryById(id)
    }

    // Calculation logic for Course Fee Estimator
    fun calculateFeeRange(
        course: ExamCourse,
        mode: BatchMode,
        duration: DurationOption
    ): Pair<Int, Int> {
        val baseFee = when (duration) {
            DurationOption.THREE_MONTHS -> course.basePrice3Months
            DurationOption.SIX_MONTHS -> course.basePrice6Months
            DurationOption.TWELVE_MONTHS -> course.basePrice12Months
        }

        // Apply mode multiplier (Offline slightly higher due to infrastructure, Hybrid slightly above Online)
        val calculatedCenter = (baseFee * mode.multiplier).toInt()
        
        // Fee range display: min range (-5%) to max range (+8%)
        val minRange = (calculatedCenter * 0.95f).toInt()
        val maxRange = (calculatedCenter * 1.08f).toInt()

        // Round to nearest 500 for clean Indian fee display
        val roundedMin = (minRange / 500) * 500
        val roundedMax = (maxRange / 500) * 500

        return Pair(roundedMin, roundedMax)
    }
}
