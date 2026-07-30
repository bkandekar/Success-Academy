package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enquiries")
data class EnquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val phone: String,
    val courseTitle: String,
    val batchMode: String,
    val duration: String,
    val qualification: String,
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
