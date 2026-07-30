package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRailway
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AcademyData
import com.example.data.ExamCourse
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.TextOnGold
import com.example.ui.theme.TextOnNavy

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoursesSection(
    selectedCategoryFilter: String,
    onCategoryFilterSelect: (String) -> Unit,
    onEnquireCourseClick: (ExamCourse) -> Unit,
    onViewDetailClick: (ExamCourse) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Staff Selection", "Civil Services", "Banking & Finance", "Defense & Railway")

    val filteredCourses = if (selectedCategoryFilter == "All") {
        AcademyData.courses
    } else {
        AcademyData.courses.filter { it.category == selectedCategoryFilter }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundLight)
            .padding(20.dp)
            .testTag("courses_section")
    ) {
        Text(
            text = "OUR EXAM PROGRAMS",
            color = GoldAccent,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Targeted Coaching Courses & Batches",
            color = NavyPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Designed according to 2026 updated exam patterns with regular test series and doubt clearing.",
            color = Color(0xFF4B5563),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Filter Chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategoryFilter == category
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategoryFilterSelect(category) },
                    label = {
                        Text(
                            text = category,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NavyPrimary,
                        selectedLabelColor = GoldAccent,
                        containerColor = Color.White,
                        labelColor = NavyPrimary
                    ),
                    modifier = Modifier.testTag("filter_chip_$category")
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Course Cards Grid / List
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            filteredCourses.forEach { course ->
                CourseCardItem(
                    course = course,
                    onEnquireClick = { onEnquireCourseClick(course) },
                    onViewDetailClick = { onViewDetailClick(course) }
                )
            }
        }
    }
}

@Composable
private fun CourseCardItem(
    course: ExamCourse,
    onEnquireClick: () -> Unit,
    onViewDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(NavyPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (course.iconName) {
                                "assignment" -> Icons.Default.Assignment
                                "gavel" -> Icons.Default.Gavel
                                "account_balance" -> Icons.Default.AccountBalance
                                "payments" -> Icons.Default.Payments
                                "train" -> Icons.Default.Train
                                "security" -> Icons.Default.Security
                                "military_tech" -> Icons.Default.MilitaryTech
                                "account_tree" -> Icons.Default.AccountTree
                                else -> Icons.Default.DirectionsRailway
                            },
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = course.title,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = course.category,
                            color = Color(0xFF6B7280),
                            fontSize = 12.sp
                        )
                    }
                }

                // Badge Tag
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = GoldContainer
                ) {
                    Text(
                        text = course.badge,
                        color = NavyDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = course.description,
                color = Color(0xFF374151),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Syllabus Highlights List
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                course.syllabusHighlights.take(2).forEach { highlight ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = NavyPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = highlight,
                            color = Color(0xFF4B5563),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer / Price / Action Row - Rule 3 alignment
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Next Batch: ${course.upcomingBatchDate}",
                        color = Color(0xFF059669),
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                    Text(
                        text = "From ₹${"%,d".format(course.basePrice3Months)}",
                        color = NavyPrimary,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onViewDetailClick,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("course_detail_btn_${course.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Details",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Button(
                        onClick = onEnquireClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyPrimary,
                            contentColor = GoldAccent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("course_enquire_btn_${course.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Enquire",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
