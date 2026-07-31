/* Success Academy Coaching Classes Interactivity & UI Script */

/**
 * ==========================================================================
 * RULE 1 — SINGLE SOURCE OF TRUTH FOR BUSINESS CONTACT INFO
 * All interactive booking/dialer code pulls strictly from this single object.
 * ==========================================================================
 */
const BUSINESS_CONFIG = {
    phone: "9067257872",
    whatsapp: "918329931123",
    businessName: "Success Academy Coaching Classes"
};

// Course Data Source
const coursesData = [
    {
        id: "ssc-cgl",
        title: "SSC CGL (Combined Graduate Level)",
        category: "Staff Selection",
        tag: "High Demand",
        duration: "6 Months",
        fee: "15,000",
        batchDate: "15th Nov 2026",
        modes: "Online / Offline / Hybrid",
        description: "Comprehensive preparation for Tier 1 & Tier 2 exams with Tier-wise strategies, Quantitative Aptitude, Reasoning, English & GK.",
        highlights: [
            "100+ Sectional & Full Mock Tests with All India Rank",
            "Special Speed Math & Shortcut Tricks Workshops",
            "Daily Current Affairs & Static GK PDF Handouts",
            "1-on-1 Personal Doubt Clearing Sessions"
        ],
        syllabus: [
            "Quantitative Aptitude & Advanced Mathematics",
            "General Intelligence & Logical Reasoning",
            "English Language & Comprehension Skills",
            "General Awareness & Current Affairs"
        ]
    },
    {
        id: "mpsc",
        title: "MPSC Rajyaseva / Combine Group B & C",
        category: "Civil Services",
        tag: "Top Selection Rate",
        duration: "12 Months",
        fee: "28,000",
        batchDate: "20th Nov 2026",
        modes: "Offline / Hybrid",
        description: "In-depth coaching for Prelims and Mains with focused answer writing practice, Marathi language study, and Maharashtra history/geography modules.",
        highlights: [
            "Complete GS Subject Coverage by Expert Mentors",
            "Weekly Mains Answer Writing Evaluation & Feedback",
            "Special Maharashtra History & Geography Focus",
            "Mock Interview Preparation Panel with Retd. Officers"
        ],
        syllabus: [
            "General Studies Paper I & II (Prelims)",
            "Mains Descriptive & Objective Papers",
            "CSAT Comprehensive Preparation",
            "Maharashtra State Specific Subject Modules"
        ]
    },
    {
        id: "upsc",
        title: "UPSC Civil Services (IAS / IPS / IFS)",
        category: "Civil Services",
        tag: "Foundation Batch",
        duration: "15 Months",
        fee: "45,000",
        batchDate: "1st Dec 2026",
        modes: "Offline / Hybrid",
        description: "Holistic IAS/IPS foundation batch covering Prelims GS + CSAT, Mains General Studies Papers I-IV, Essay writing, and Interview guidance.",
        highlights: [
            "Mentoring by Ex-Civil Servants & Top Subject Experts",
            "Daily Newspaper Analysis & Editorial Discussions",
            "Comprehensive Mains Test Series with Personal Mentorship",
            "Regular Guest Lectures by Serving Administrative Officers"
        ],
        syllabus: [
            "GS Paper I-IV Complete Systematic Syllabus",
            "CSAT Aptitude, Comprehension & Decision Making",
            "Essay Writing Techniques & Strategy",
            "Current Affairs Integration & Value Addition"
        ]
    },
    {
        id: "ibps-po",
        title: "IBPS PO / Clerk & SBI PO",
        category: "Banking & Finance",
        tag: "Fast Track Batch",
        duration: "4 Months",
        fee: "12,000",
        batchDate: "10th Nov 2026",
        modes: "Online / Offline / Hybrid",
        description: "Targeted banking batch emphasizing speed, accuracy, Data Interpretation, high-level Reasoning puzzles, and Banking Awareness.",
        highlights: [
            "Computer-based Speed Test Lab Access",
            "High Level Data Interpretation & Complex Puzzles Mastery",
            "Banking Awareness & Financial GK Study Notes",
            "Mock Interviews for PO Candidates"
        ],
        syllabus: [
            "Numerical Ability & High-Level Data Interpretation",
            "Reasoning Ability & Analytical Puzzles",
            "English Language for Banking & Grammar",
            "Banking & Financial General Awareness"
        ]
    },
    {
        id: "rrb-ntpc",
        title: "RRB NTPC & Group D Railway",
        category: "Defense & Railway",
        tag: "Popular Choice",
        duration: "4 Months",
        fee: "10,000",
        batchDate: "18th Nov 2026",
        modes: "Online / Offline",
        description: "Specialized batch covering General Science, Mathematics, General Intelligence, and Technical modules tailored for Indian Railways exams.",
        highlights: [
            "2000+ Topic-wise Practice Question Bank",
            "Science & Technology Focus Modules",
            "Previous 10 Years Question Paper Analysis",
            "Online Speed Test Platform with Real Exam UI"
        ],
        syllabus: [
            "Mathematics & Calculation Shortcuts",
            "General Intelligence & Logical Reasoning",
            "General Science (Physics, Chemistry, Biology)",
            "General Awareness & Railways Special Notes"
        ]
    },
    {
        id: "nda-cds",
        title: "NDA & CDS Defense Academy Batch",
        category: "Defense & Railway",
        tag: "Physical + Written",
        duration: "6 Months",
        fee: "18,000",
        batchDate: "25th Nov 2026",
        modes: "Offline",
        description: "Integrated preparation for written examination alongside SSB Interview guidance, psychological testing orientation, and physical stamina training.",
        highlights: [
            "Mathematics & General Ability Test (GAT) Intensive Coverage",
            "Ex-Defense Personnel Orientation Seminars",
            "SSB Interview Screening & PPDT Practice Sessions",
            "Weekly Outdoor Physical Fitness Guidance"
        ],
        syllabus: [
            "Higher & Secondary Level Mathematics",
            "English & Officer Like Communication Skills",
            "General Knowledge & Strategic Defense Awareness",
            "SSB Psychology, GTO Tasks & Interview Overview"
        ]
    }
];

// Fee Structure Dictionary by Exam, Mode & Duration Multipliers
const baseFees = {
    "ssc-cgl": 15000,
    "mpsc": 28000,
    "upsc": 45000,
    "ibps-po": 12000,
    "rrb-ntpc": 10000,
    "nda-cds": 18000
};

const modeMultipliers = {
    "ONLINE": 0.8,
    "OFFLINE": 1.0,
    "HYBRID": 1.15
};

const durationMultipliers = {
    "THREE": 0.7,
    "SIX": 1.0,
    "TWELVE": 1.6
};

let currentMode = "OFFLINE";
let currentDuration = "SIX";
let currentTheme = "classic"; // "classic", "modern-gold", "emerald-pro"

document.addEventListener("DOMContentLoaded", () => {
    initEstimatorOptions();
    renderCourses("All");
    calculateFee();
    initFAQAccordion();
});

// Populate Estimator Dropdown and Modal Dropdown
function initEstimatorOptions() {
    const estimatorSelect = document.getElementById("estimator-course-select");
    const enquiryCourseSelect = document.getElementById("input-course");

    if (estimatorSelect) {
        estimatorSelect.innerHTML = coursesData.map(c => 
            `<option value="${c.id}">${c.title}</option>`
        ).join("");
    }

    if (enquiryCourseSelect) {
        enquiryCourseSelect.innerHTML = coursesData.map(c => 
            `<option value="${c.title}">${c.title}</option>`
        ).join("");
    }
}

// Set Mode for Estimator
function setMode(mode) {
    currentMode = mode;
    const modeBtns = document.querySelectorAll("#mode-control .segment-btn");
    modeBtns.forEach(btn => {
        if (btn.getAttribute("data-mode") === mode) {
            btn.classList.add("active");
        } else {
            btn.classList.remove("active");
        }
    });
    calculateFee();
}

// Set Duration for Estimator
function setDuration(duration) {
    currentDuration = duration;
    const durationBtns = document.querySelectorAll("#duration-control .segment-btn");
    durationBtns.forEach(btn => {
        if (btn.getAttribute("data-duration") === duration) {
            btn.classList.add("active");
        } else {
            btn.classList.remove("active");
        }
    });
    calculateFee();
}

// Calculate Estimated Fee
function calculateFee() {
    const courseSelect = document.getElementById("estimator-course-select");
    if (!courseSelect) return;

    const courseId = courseSelect.value || "ssc-cgl";
    const base = baseFees[courseId] || 15000;

    const modeFactor = modeMultipliers[currentMode] || 1.0;
    const durationFactor = durationMultipliers[currentDuration] || 1.0;

    const estimatedValue = Math.round(base * modeFactor * durationFactor);
    const lowerRange = Math.round(estimatedValue * 0.9);
    const upperRange = Math.round(estimatedValue * 1.1);

    const feeDisplay = document.getElementById("fee-amount");
    if (feeDisplay) {
        feeDisplay.innerText = `₹${lowerRange.toLocaleString("en-IN")} – ₹${upperRange.toLocaleString("en-IN")}`;
    }
}

// Enquire Selected Batch from Estimator
function enquireCurrentEstimatorBatch() {
    const courseSelect = document.getElementById("estimator-course-select");
    const courseObj = coursesData.find(c => c.id === courseSelect.value);
    const courseName = courseObj ? courseObj.title : "SSC CGL";
    openEnquiryModal(courseName);
}

// Filter Courses by Category
function filterCategory(category) {
    const tabBtns = document.querySelectorAll("#category-filter-tabs .tab-btn");
    tabBtns.forEach(btn => {
        if (btn.innerText.trim() === category) {
            btn.classList.add("active");
        } else {
            btn.classList.remove("active");
        }
    });

    renderCourses(category);
}

// Render Courses Grid
function renderCourses(category) {
    const grid = document.getElementById("courses-grid");
    if (!grid) return;

    grid.innerHTML = "";

    const filtered = category === "All" 
        ? coursesData 
        : coursesData.filter(c => c.category === category);

    filtered.forEach(course => {
        const card = document.createElement("div");
        card.className = "course-card";
        card.innerHTML = `
            <div>
                <div class="course-header">
                    <div class="course-title-group">
                        <div class="icon-circle"><i class="fa-solid fa-graduation-cap"></i></div>
                        <div>
                            <h3 class="course-title">${course.title}</h3>
                            <span class="course-category">${course.category}</span>
                        </div>
                    </div>
                    <span class="course-badge">${course.tag}</span>
                </div>
                <p class="course-desc">${course.description}</p>
                <ul class="course-highlights">
                    ${course.highlights.slice(0, 2).map(h => `<li><i class="fa-solid fa-circle-check"></i> ${h}</li>`).join('')}
                </ul>
            </div>
            <div class="course-footer">
                <div>
                    <span class="batch-date"><i class="fa-solid fa-calendar-check"></i> Next Batch: ${course.batchDate}</span>
                    <span class="price-tag">₹${course.fee}</span>
                </div>
                <div class="btn-group-sm">
                    <button class="btn-sm-outline" onclick="openCourseDetails('${course.id}')">
                        <i class="fa-solid fa-book-open"></i> Syllabus
                    </button>
                    <button class="btn-sm-primary" onclick="openEnquiryModal('${course.title}')">
                        Enquire <i class="fa-solid fa-arrow-right"></i>
                    </button>
                </div>
            </div>
        `;
        grid.appendChild(card);
    });
}

// Smooth scroll to section
function scrollToSection(sectionId) {
    const elem = document.getElementById(sectionId);
    if (elem) {
        elem.scrollIntoView({ behavior: 'smooth' });
    }
}

// Open Enquiry Modal
function openEnquiryModal(preselectedCourse) {
    const modal = document.getElementById("enquiry-modal");
    if (preselectedCourse) {
        const courseSelect = document.getElementById("input-course");
        if (courseSelect) {
            courseSelect.value = preselectedCourse;
        }
    }
    if (modal) {
        modal.classList.add("open");
    }
}

// Close Enquiry Modal
function closeEnquiryModal() {
    const modal = document.getElementById("enquiry-modal");
    if (modal) {
        modal.classList.remove("open");
    }
}

// Open Course Syllabus Detail Modal
function openCourseDetails(courseId) {
    const course = coursesData.find(c => c.id === courseId);
    if (!course) return;

    const modal = document.getElementById("course-modal");
    const body = document.getElementById("course-modal-content");

    if (body) {
        body.innerHTML = `
            <div class="modal-header">
                <div>
                    <span class="modal-overline">${course.category.toUpperCase()}</span>
                    <h3 class="modal-title">${course.title}</h3>
                </div>
                <button class="btn-close" onclick="closeCourseModal()"><i class="fa-solid fa-xmark"></i></button>
            </div>
            
            <div class="modal-course-badge">
                <i class="fa-solid fa-clock"></i> Duration: ${course.duration} &nbsp;|&nbsp; 
                <i class="fa-solid fa-chalkboard-user"></i> ${course.modes}
            </div>

            <p style="font-size: 13px; color: #D1D5DB; margin-bottom: 16px; line-height: 1.5;">${course.description}</p>

            <h4 style="color: var(--gold-accent); font-size: 14px; margin-bottom: 8px; font-weight:700;">
                <i class="fa-solid fa-star"></i> Key Program Highlights
            </h4>
            <ul class="course-highlights" style="margin-bottom: 16px;">
                ${course.highlights.map(h => `<li style="color:#FFFFFF; margin-bottom:6px;"><i class="fa-solid fa-circle-check" style="color: var(--gold-accent);"></i> ${h}</li>`).join('')}
            </ul>

            <h4 style="color: var(--gold-accent); font-size: 14px; margin-bottom: 8px; font-weight:700;">
                <i class="fa-solid fa-list-check"></i> Core Syllabus Coverage
            </h4>
            <ul class="course-highlights" style="margin-bottom: 20px;">
                ${course.syllabus.map(s => `<li style="color:#FFFFFF; margin-bottom:6px;"><i class="fa-solid fa-book" style="color: var(--gold-accent);"></i> ${s}</li>`).join('')}
            </ul>

            <button class="btn-whatsapp-submit" onclick="closeCourseModal(); openEnquiryModal('${course.title}');">
                <i class="fa-solid fa-paper-plane"></i> Enquire For Next Batch (${course.batchDate})
            </button>
        `;
    }

    if (modal) {
        modal.classList.add("open");
    }
}

// Close Course Syllabus Modal
function closeCourseModal() {
    const modal = document.getElementById("course-modal");
    if (modal) {
        modal.classList.remove("open");
    }
}

// Handle Form Submit to WhatsApp
function handleFormSubmit(e) {
    e.preventDefault();

    const name = document.getElementById("input-name").value.trim();
    const phone = document.getElementById("input-phone").value.trim();
    const course = document.getElementById("input-course").value;
    const mode = document.getElementById("input-mode").value;
    const qual = document.getElementById("input-qualification")?.value || "Graduate";
    const notes = document.getElementById("input-notes")?.value || "None";

    let valid = true;

    if (!name) {
        document.getElementById("name-error").innerText = "Please enter your full name";
        valid = false;
    } else {
        document.getElementById("name-error").innerText = "";
    }

    if (!phone || phone.length < 10) {
        document.getElementById("phone-error").innerText = "Enter a valid 10-digit mobile number";
        valid = false;
    } else {
        document.getElementById("phone-error").innerText = "";
    }

    if (!valid) return;

    // Message greeting and WhatsApp number both pull from BUSINESS_CONFIG (Rule 1)
    const message = `Hello ${BUSINESS_CONFIG.businessName} (Rahul Patil Sir), I want to enquire for coaching.%0A%0A*Full Name:* ${encodeURIComponent(name)}%0A*Phone:* ${encodeURIComponent(phone)}%0A*Course:* ${encodeURIComponent(course)}%0A*Batch Mode:* ${encodeURIComponent(mode)}%0A*Qualification:* ${encodeURIComponent(qual)}%0A*Notes:* ${encodeURIComponent(notes)}`;

    const whatsappUrl = `https://wa.me/${BUSINESS_CONFIG.whatsapp}?text=${message}`;

    window.open(whatsappUrl, "_blank");
    closeEnquiryModal();
}

// FAQ Accordion Toggle
function initFAQAccordion() {
    const faqQuestions = document.querySelectorAll(".faq-question");
    faqQuestions.forEach(q => {
        q.addEventListener("click", () => {
            const faqItem = q.parentElement;
            faqItem.classList.toggle("active");
        });
    });
}

// Switch UI Design Theme Variant
function switchTheme(themeName) {
    const body = document.body;
    body.classList.remove("theme-modern-gold", "theme-emerald-pro");
    
    if (themeName === "modern-gold") {
        body.classList.add("theme-modern-gold");
    } else if (themeName === "emerald-pro") {
        body.classList.add("theme-emerald-pro");
    }
    
    currentTheme = themeName;
    
    const themeBtns = document.querySelectorAll(".theme-switch-btn");
    themeBtns.forEach(btn => {
        if (btn.getAttribute("data-theme") === themeName) {
            btn.classList.add("active");
        } else {
            btn.classList.remove("active");
        }
    });
}
