package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AcademyData
import com.example.data.BatchMode
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.TextOnGold
import com.example.ui.theme.TextOnNavy
import com.example.ui.theme.WhatsAppGreen

@Composable
fun EnquiryModal(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    name: String,
    phone: String,
    course: String,
    mode: String,
    qualification: String,
    notes: String,
    nameError: String?,
    phoneError: String?,
    isSubmitting: Boolean,
    successMessage: String?,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onCourseChange: (String) -> Unit,
    onModeChange: (String) -> Unit,
    onQualificationChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    if (!isOpen) return

    var courseDropdownExpanded by remember { mutableStateOf(false) }
    var modeDropdownExpanded by remember { mutableStateOf(false) }
    var qualDropdownExpanded by remember { mutableStateOf(false) }

    val qualifications = listOf("12th Pass", "Graduate", "Final Year Student", "Post Graduate")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 20.dp)
                .testTag("enquiry_modal_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = NavyDark
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "OFFICIAL ENQUIRY FORM",
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Success Academy Kolhapur",
                            color = TextOnNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("modal_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Modal",
                            tint = Color(0xFF9CA3AF)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (successMessage != null) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = WhatsAppGreen,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = successMessage,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Field 1: Full Name (Required)
                Text(
                    text = "Full Name *",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    placeholder = { Text("e.g. Rahul Patil", color = Color(0xFF6B7280)) },
                    isError = nameError != null,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NavyLight,
                        unfocusedContainerColor = NavyLight,
                        focusedTextColor = TextOnNavy,
                        unfocusedTextColor = TextOnNavy,
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = Color(0xFF374151)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("modal_input_name")
                )
                if (nameError != null) {
                    Text(
                        text = nameError,
                        color = Color(0xFFEF4444),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Field 2: Phone Number (Required)
                Text(
                    text = "Phone Number (WhatsApp) *",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    placeholder = { Text("e.g. 9067257872", color = Color(0xFF6B7280)) },
                    isError = phoneError != null,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NavyLight,
                        unfocusedContainerColor = NavyLight,
                        focusedTextColor = TextOnNavy,
                        unfocusedTextColor = TextOnNavy,
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = Color(0xFF374151)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("modal_input_phone")
                )
                if (phoneError != null) {
                    Text(
                        text = phoneError,
                        color = Color(0xFFEF4444),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Field 3: Course Interested In
                Text(
                    text = "Exam / Course Interested In",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = course,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.clickable { courseDropdownExpanded = true }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavyLight,
                            unfocusedContainerColor = NavyLight,
                            focusedTextColor = TextOnNavy,
                            unfocusedTextColor = TextOnNavy,
                            focusedBorderColor = GoldAccent,
                            unfocusedBorderColor = Color(0xFF374151)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { courseDropdownExpanded = true }
                            .testTag("modal_input_course")
                    )

                    DropdownMenu(
                        expanded = courseDropdownExpanded,
                        onDismissRequest = { courseDropdownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(NavyLight)
                    ) {
                        AcademyData.courses.forEach { c ->
                            DropdownMenuItem(
                                text = { Text(c.title, color = TextOnNavy) },
                                onClick = {
                                    onCourseChange(c.title)
                                    courseDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Field 4: Preferred Batch Mode
                Text(
                    text = "Preferred Batch Mode",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = mode,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.clickable { modeDropdownExpanded = true }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavyLight,
                            unfocusedContainerColor = NavyLight,
                            focusedTextColor = TextOnNavy,
                            unfocusedTextColor = TextOnNavy,
                            focusedBorderColor = GoldAccent,
                            unfocusedBorderColor = Color(0xFF374151)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { modeDropdownExpanded = true }
                            .testTag("modal_input_mode")
                    )

                    DropdownMenu(
                        expanded = modeDropdownExpanded,
                        onDismissRequest = { modeDropdownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(NavyLight)
                    ) {
                        BatchMode.values().forEach { b ->
                            DropdownMenuItem(
                                text = { Text(b.displayName, color = TextOnNavy) },
                                onClick = {
                                    onModeChange(b.displayName)
                                    modeDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Field 5: Qualification
                Text(
                    text = "Qualification / Current Year",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = qualification,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.clickable { qualDropdownExpanded = true }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavyLight,
                            unfocusedContainerColor = NavyLight,
                            focusedTextColor = TextOnNavy,
                            unfocusedTextColor = TextOnNavy,
                            focusedBorderColor = GoldAccent,
                            unfocusedBorderColor = Color(0xFF374151)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { qualDropdownExpanded = true }
                            .testTag("modal_input_qualification")
                    )

                    DropdownMenu(
                        expanded = qualDropdownExpanded,
                        onDismissRequest = { qualDropdownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(NavyLight)
                    ) {
                        qualifications.forEach { q ->
                            DropdownMenuItem(
                                text = { Text(q, color = TextOnNavy) },
                                onClick = {
                                    onQualificationChange(q)
                                    qualDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Field 6: Additional Notes (Optional)
                Text(
                    text = "Additional Questions / Notes (Optional)",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = onNotesChange,
                    placeholder = { Text("e.g. Interested in hostel facility or weekend batches", color = Color(0xFF6B7280)) },
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NavyLight,
                        unfocusedContainerColor = NavyLight,
                        focusedTextColor = TextOnNavy,
                        unfocusedTextColor = TextOnNavy,
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = Color(0xFF374151)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("modal_input_notes")
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Submit Button
                Button(
                    onClick = onSubmitClick,
                    enabled = !isSubmitting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("modal_submit_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isSubmitting) "Redirecting..." else "Submit & Connect on WhatsApp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
