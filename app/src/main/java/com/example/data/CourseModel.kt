package com.example.data

enum class BatchMode(val displayName: String, val multiplier: Float) {
    ONLINE("Online", 1.0f),
    OFFLINE("Offline", 1.20f),
    HYBRID("Hybrid", 1.10f)
}

enum class DurationOption(val displayName: String, val months: Int) {
    THREE_MONTHS("3 Months", 3),
    SIX_MONTHS("6 Months", 6),
    TWELVE_MONTHS("12 Months", 12)
}

data class ExamCourse(
    val id: String,
    val title: String,
    val category: String, // e.g., "Civil Services", "Staff Selection", "Banking & Finance", "Defense & Railway"
    val badge: String,
    val description: String,
    val syllabusHighlights: List<String>,
    val basePrice3Months: Int,
    val basePrice6Months: Int,
    val basePrice12Months: Int,
    val upcomingBatchDate: String,
    val iconName: String
)

data class PainPointSolution(
    val id: Int,
    val painTitle: String,
    val painDescription: String,
    val solutionTitle: String,
    val solutionDescription: String,
    val icon: String
)

data class FacultyMember(
    val id: String,
    val name: String,
    val role: String,
    val subject: String,
    val experience: String,
    val bio: String,
    val achievements: String
)

data class Topper(
    val id: String,
    val name: String,
    val rank: String,
    val exam: String,
    val year: String,
    val post: String,
    val quote: String,
    val location: String
)

data class Testimonial(
    val id: String,
    val studentName: String,
    val courseExam: String,
    val rating: Int,
    val review: String,
    val batchYear: String,
    val studentRole: String // e.g. "Selected Candidate" or "Parent Review"
)

object AcademyData {
    val courses = listOf(
        ExamCourse(
            id = "ssc_cgl",
            title = "SSC CGL",
            category = "Staff Selection",
            badge = "High Success Rate",
            description = "Comprehensive Tier I + Tier II preparation covering Quant, Reasoning, English, and General Awareness.",
            syllabusHighlights = listOf("Advanced Mathematics & Algebra", "Logical Reasoning & Puzzles", "English Grammar & Comprehension", "General Awareness & Current Affairs"),
            basePrice3Months = 9500,
            basePrice6Months = 16000,
            basePrice12Months = 24000,
            upcomingBatchDate = "Starts 5th August",
            iconName = "assignment"
        ),
        ExamCourse(
            id = "mpsc",
            title = "MPSC State Services",
            category = "Civil Services",
            badge = "Top Choice Kolhapur",
            description = "Dedicated Prelims + Mains guidance with Maharashtra GS focus, Answer Writing, and Mock Interview prep.",
            syllabusHighlights = listOf("Maharashtra History & Geography", "Indian Polity & Constitution", "CSAT Aptitude & Comprehension", "Answer Writing & Interview Guidance"),
            basePrice3Months = 12500,
            basePrice6Months = 22000,
            basePrice12Months = 34000,
            upcomingBatchDate = "Starts 10th August",
            iconName = "gavel"
        ),
        ExamCourse(
            id = "upsc",
            title = "UPSC CSE",
            category = "Civil Services",
            badge = "Foundation & Integrated",
            description = "Rigorous Civil Services prep focusing on GS Papers 1-4, CSAT, Essay Writing, and Current Affairs analysis.",
            syllabusHighlights = listOf("GS 1-4 Complete Syllabus", "CSAT Strategy & Practice", "Daily Current Affairs Analysis", "Option Skill Building & Answer Tests"),
            basePrice3Months = 15000,
            basePrice6Months = 28000,
            basePrice12Months = 45000,
            upcomingBatchDate = "Starts 12th August",
            iconName = "account_balance"
        ),
        ExamCourse(
            id = "ibps_po",
            title = "IBPS PO",
            category = "Banking & Finance",
            badge = "Fast-Track Prep",
            description = "Targeted Prelims + Mains course with speed math tricks, high-level puzzles, and interview sessions.",
            syllabusHighlights = listOf("Speed Mathematics & DI", "Complex Seating Puzzles", "Banking & Financial Awareness", "Descriptive English & Essay"),
            basePrice3Months = 8500,
            basePrice6Months = 15000,
            basePrice12Months = 22000,
            upcomingBatchDate = "Starts 7th August",
            iconName = "payments"
        ),
        ExamCourse(
            id = "rrb_ntpc",
            title = "RRB NTPC",
            category = "Defense & Railway",
            badge = "High Selection Batch",
            description = "CBT-1 & CBT-2 syllabus coverage with 100+ topic-wise tests and past paper problem walkthroughs.",
            syllabusHighlights = listOf("General Science & Physics", "Quantitative Aptitude", "General Intelligence", "Railway Special GK & Current Affairs"),
            basePrice3Months = 8000,
            basePrice6Months = 14000,
            basePrice12Months = 20000,
            upcomingBatchDate = "Starts 15th August",
            iconName = "train"
        ),
        ExamCourse(
            id = "nda",
            title = "NDA Exam",
            category = "Defense & Railway",
            badge = "Written + SSB Prep",
            description = "Intensive coaching for 11th/12th students focusing on Mathematics, GAT, Physical fitness, and SSB guidance.",
            syllabusHighlights = listOf("11th & 12th Mathematics", "Physics & Chemistry Brush-up", "English & General Knowledge", "SSB Interview & Screening Skills"),
            basePrice3Months = 11000,
            basePrice6Months = 19000,
            basePrice12Months = 29000,
            upcomingBatchDate = "Starts 8th August",
            iconName = "security"
        ),
        ExamCourse(
            id = "cds",
            title = "CDS Exam",
            category = "Defense & Railway",
            badge = "Graduates Batch",
            description = "Specialized training for IMA, OTA, AFA, and NA entry. Complete coverage of English, GK, and Elementary Math.",
            syllabusHighlights = listOf("Elementary Mathematics", "General Knowledge & Defense News", "English Language Proficiency", "SSB Personality Test Training"),
            basePrice3Months = 10500,
            basePrice6Months = 18000,
            basePrice12Months = 27000,
            upcomingBatchDate = "Starts 14th August",
            iconName = "military_tech"
        ),
        ExamCourse(
            id = "banking",
            title = "Banking All-In-One",
            category = "Banking & Finance",
            badge = "Includes SBI & RBI",
            description = "Single master course covering SBI PO/Clerk, IBPS PO/Clerk, RRB Assistant, and RBI Assistant exams.",
            syllabusHighlights = listOf("Quantitative Aptitude Mastery", "Reasoning & Coding-Decoding", "Banking Awareness & Economy", "Interview & GD Preparation"),
            basePrice3Months = 9000,
            basePrice6Months = 16500,
            basePrice12Months = 24500,
            upcomingBatchDate = "Starts 6th August",
            iconName = "account_tree"
        ),
        ExamCourse(
            id = "railway",
            title = "Railway Master Batch",
            category = "Defense & Railway",
            badge = "ALP + Group D + NTPC",
            description = "Combined preparation for Railway Group D, Assistant Loco Pilot (ALP), and RPF Constable exams.",
            syllabusHighlights = listOf("Basic Science & Engineering", "Reasoning Aptitude", "General Knowledge & Static GK", "Speed & Accuracy Test Series"),
            basePrice3Months = 7500,
            basePrice6Months = 13500,
            basePrice12Months = 19000,
            upcomingBatchDate = "Starts 18th August",
            iconName = "directions_railway"
        )
    )

    val painPointsSolutions = listOf(
        PainPointSolution(
            id = 1,
            painTitle = "Overcrowded Classrooms",
            painDescription = "Drowning in huge batches with 200+ students where teachers can't clear individual doubts or monitor progress.",
            solutionTitle = "Limited Batch Size (Max 35)",
            solutionDescription = "Personalized focus for every student with dedicated 1-on-1 doubt clearing sessions after every lecture.",
            icon = "groups"
        ),
        PainPointSolution(
            id = 2,
            painTitle = "Outdated Study Material",
            painDescription = "Studying old patterns and irrelevant questions that waste precious time and lower exam confidence.",
            solutionTitle = "Latest Pattern Test Series",
            solutionDescription = "Updated 2026 pattern test series, daily current affairs booklets, and previous 10-year topicwise solved papers.",
            icon = "menu_book"
        ),
        PainPointSolution(
            id = 3,
            painTitle = "Inflexible Timings & Attendance",
            painDescription = "Missing lectures due to college/job schedules with no backup or recorded sessions available.",
            solutionTitle = "Hybrid Flexibility (Online + Offline)",
            solutionDescription = "Attend live offline classes or stream online with 24/7 app access to recorded lectures & PDF notes.",
            icon = "schedule"
        ),
        PainPointSolution(
            id = 4,
            painTitle = "Lack of Exam Strategy",
            painDescription = "Knowing concept basics but struggling with time management, negative marking, and cutoff scores.",
            solutionTitle = "Direct Mentorship by Rahul Patil",
            solutionDescription = "Master short-cut tricks, speed math techniques, and custom study planners designed by experienced toppers.",
            icon = "psychology"
        )
    )

    val facultyList = listOf(
        FacultyMember(
            id = "f1",
            name = "Rahul Patil",
            role = "Founder & Managing Director",
            subject = "General Studies & Aptitude Strategy",
            experience = "12+ Years Experience",
            bio = "Mentored over 8,500+ government job aspirants across Maharashtra. Expert in GS paper structuring and speed aptitude shortcuts.",
            achievements = "420+ Selections under direct guidance • MPSC & UPSC Subject Specialist"
        ),
        FacultyMember(
            id = "f2",
            name = "Prof. Sneha Deshmukh",
            role = "Senior Faculty",
            subject = "Quantitative Aptitude & Advanced Math",
            experience = "9+ Years Experience",
            bio = "Renowned for shortcut formulas and Vedic math methods that cut calculation time by 60% in competitive exams.",
            achievements = "Ex-Banking Officer • Author of 'Speed Maths for Competitive Exams'"
        ),
        FacultyMember(
            id = "f3",
            name = "Prof. Vikas Jadhav",
            role = "Senior Faculty",
            subject = "Logical Reasoning & CSAT",
            experience = "8+ Years Experience",
            bio = "Master instructor for high-level seating arrangement puzzles, syllogisms, and critical reasoning concepts.",
            achievements = "Cracked IBPS PO & SSC CGL Mains • Top Rated CSAT Mentor"
        ),
        FacultyMember(
            id = "f4",
            name = "Dr. Anjali Shinde",
            role = "Head of English & Essay Writing",
            subject = "English Language & Descriptive Mains",
            experience = "10+ Years Experience",
            bio = "Specialist in English grammar accuracy, comprehension techniques, and descriptive essay writing for Mains exams.",
            achievements = "Ph.D. in Applied English • MPSC Answer Evaluation Specialist"
        )
    )

    val toppers = listOf(
        Topper(
            id = "t1",
            name = "Akash Kulkarni",
            rank = "AIR 14",
            exam = "SSC CGL 2025",
            year = "2025",
            post = "Income Tax Inspector",
            quote = "Success Academy's daily mock tests and Rahul Sir's shortcut techniques were the game changer in my 1st attempt selection!",
            location = "Kolhapur"
        ),
        Topper(
            id = "t2",
            name = "Pooja Patil",
            rank = "Rank 8 (State)",
            exam = "MPSC State Services",
            year = "2025",
            post = "Dy. Collector (Class 1)",
            quote = "The personal answer writing evaluation and guidance by Success Academy faculty made all the difference in Mains.",
            location = "Ichalkaranji"
        ),
        Topper(
            id = "t3",
            name = "Rohan Kamble",
            rank = "Selection",
            exam = "IBPS PO 2024",
            year = "2024",
            post = "Probationary Officer (SBI)",
            quote = "From zero speed in quant to clearing SBI PO in my very first attempt. Thank you Success Academy Team!",
            location = "Sangli"
        ),
        Topper(
            id = "t4",
            name = "Swapnil More",
            rank = "AIR 42",
            exam = "RRB NTPC",
            year = "2024",
            post = "Station Master",
            quote = "Top-notch study materials and 24/7 library facility in Station Road, Kolhapur gave me the best study environment.",
            location = "Satara"
        )
    )

    val testimonials = listOf(
        Testimonial(
            id = "r1",
            studentName = "Suraj Chavan",
            courseExam = "MPSC & SSC CGL Student",
            rating = 5,
            review = "Best coaching institute in Kolhapur! Rahul Patil Sir provides personal attention to every single student. The hybrid batch feature saved me a lot of travel time during college exams.",
            batchYear = "2025-2026 Batch",
            studentRole = "Selected Assistant Section Officer"
        ),
        Testimonial(
            id = "r2",
            studentName = "Priya Salokhe",
            courseExam = "IBPS Banking Batch",
            rating = 5,
            review = "The test series and weekly doubt solving sessions are unmatched. I cleared IBPS PO and Clerk Mains back-to-back thanks to the dedicated faculty.",
            batchYear = "2024-2025 Batch",
            studentRole = "Selected Bank PO"
        ),
        Testimonial(
            id = "r3",
            studentName = "Dattatray Patil (Parent)",
            courseExam = "Parent of MPSC Aspirant",
            rating = 5,
            review = "We sent our daughter from Ichalkaranji to Success Academy Kolhapur. The strict attendance tracking, weekly progress reports to parents, and disciplined environment gave us immense peace of mind.",
            batchYear = "Parent Review",
            studentRole = "Parent of Pooja Patil (Dy. Collector)"
        ),
        Testimonial(
            id = "r4",
            studentName = "Mahesh Bhosale",
            courseExam = "NDA & Defense Batch",
            rating = 5,
            review = "Rahul Sir and team don't just teach for written exams; they build your confidence and SSB interview personality from day one.",
            batchYear = "2025 Batch",
            studentRole = "Recommended NDA Candidate"
        )
    )
}
