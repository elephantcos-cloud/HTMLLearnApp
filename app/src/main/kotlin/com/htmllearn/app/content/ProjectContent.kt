package com.htmllearn.app.content

enum class ProjectDifficulty { EASY, MEDIUM, HARD }

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val difficulty: ProjectDifficulty,
    val estimatedMinutes: Int,
    val tags: List<String>,
    val starterCode: String,
    val hints: List<String>,
    val requiredChapter: String
)

object ProjectContent {

    fun getAllProjects(): List<Project> = listOf(

        Project(
            id = "proj_1",
            title = "ব্যক্তিগত পরিচিতি পেজ",
            description = "তোমার নিজের পরিচিতি পেজ বানাও। নাম, ছবি, পরিচয়, দক্ষতা সব থাকবে।",
            difficulty = ProjectDifficulty.EASY,
            estimatedMinutes = 20,
            tags = listOf("HTML", "Structure", "Text"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>আমার পরিচিতি</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      background: #0D1117;
      color: #E6EDF3;
    }
    /* এখানে তোমার CSS লেখো */
  </style>
</head>
<body>
  <!-- এখানে তোমার HTML লেখো -->
  <!-- ১. তোমার নাম heading হিসেবে -->
  <!-- ২. তোমার ছবি -->
  <!-- ৩. তোমার পরিচয় paragraph -->
  <!-- ৪. তোমার দক্ষতার list -->
  <!-- ৫. যোগাযোগের লিংক -->
</body>
</html>""",
            hints = listOf(
                "h1 দিয়ে তোমার নাম লেখো",
                "img tag দিয়ে ছবি যোগ করো, alt text দিতে ভুলো না",
                "ul বা ol দিয়ে দক্ষতার list তৈরি করো",
                "a href='mailto:...' দিয়ে email link যোগ করো",
                "section tag ব্যবহার করে অংশগুলো আলাদা করো"
            ),
            requiredChapter = "ch3"
        ),

        Project(
            id = "proj_2",
            title = "রেসিপি পেজ",
            description = "তোমার পছন্দের একটি রান্নার রেসিপি পেজ বানাও। উপকরণ, পদ্ধতি সব সুন্দরভাবে সাজাও।",
            difficulty = ProjectDifficulty.EASY,
            estimatedMinutes = 25,
            tags = listOf("HTML", "Lists", "Images"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>রান্নার রেসিপি</title>
  <style>
    body { background: #0D1117; color: #E6EDF3; 
           font-family: Arial; max-width: 700px; margin: auto; padding: 20px; }
    img { max-width: 100%; border-radius: 10px; }
  </style>
</head>
<body>
  <!-- রেসিপির নাম -->
  <!-- রান্নার ছবি -->
  <!-- সময় ও serving তথ্য -->
  <!-- উপকরণের list -->
  <!-- রান্নার ধাপগুলো (ordered list) -->
  <!-- Tips section -->
</body>
</html>""",
            hints = listOf(
                "figure ও figcaption দিয়ে ছবি যুক্ত করো",
                "ul দিয়ে উপকরণের তালিকা করো",
                "ol দিয়ে ধাপগুলো numbered list-এ লেখো",
                "time tag দিয়ে রান্নার সময় semantic ভাবে দেখাও",
                "details ও summary দিয়ে extra tips লুকিয়ে রাখো"
            ),
            requiredChapter = "ch6"
        ),

        Project(
            id = "proj_3",
            title = "ক্লাস রুটিন টেবিল",
            description = "HTML table দিয়ে সাপ্তাহিক ক্লাস রুটিন তৈরি করো।",
            difficulty = ProjectDifficulty.EASY,
            estimatedMinutes = 20,
            tags = listOf("HTML", "Table"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>ক্লাস রুটিন</title>
  <style>
    body { background: #0D1117; color: #E6EDF3; padding: 20px; }
    table { width: 100%; border-collapse: collapse; }
    th, td { border: 1px solid #30363D; padding: 12px; text-align: center; }
    th { background: #161B22; color: #58A6FF; }
    tr:nth-child(even) { background: #0D1117; }
    tr:nth-child(odd) { background: #161B22; }
  </style>
</head>
<body>
  <h1>আমার সাপ্তাহিক রুটিন</h1>
  <!-- table এখানে বানাও -->
  <!-- thead: সময়, রবিবার, সোমবার, মঙ্গলবার... -->
  <!-- tbody: প্রতিটি সময়ের জন্য এক row -->
  <!-- colspan ব্যবহার করো যেখানে ক্লাস নেই -->
</body>
</html>""",
            hints = listOf(
                "thead-এ দিনগুলো th দিয়ে লেখো",
                "tbody-এ প্রতিটি row হবে একটি সময়কাল",
                "colspan দিয়ে খালি period মার্জ করো",
                "scope='col' দিয়ে column header accessibility বাড়াও",
                "caption tag দিয়ে table-এর শিরোনাম দাও"
            ),
            requiredChapter = "ch7"
        ),

        Project(
            id = "proj_4",
            title = "যোগাযোগ ফর্ম",
            description = "একটি পূর্ণাঙ্গ Contact Form তৈরি করো। নাম, ইমেইল, বিষয়, বার্তা সব থাকবে।",
            difficulty = ProjectDifficulty.MEDIUM,
            estimatedMinutes = 30,
            tags = listOf("HTML", "Forms", "Validation"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>যোগাযোগ করুন</title>
  <style>
    * { box-sizing: border-box; }
    body { background: #0D1117; color: #E6EDF3; 
           font-family: Arial; max-width: 600px; margin: auto; padding: 20px; }
    input, textarea, select { 
      width: 100%; padding: 10px; margin: 8px 0;
      background: #161B22; border: 1px solid #30363D;
      color: #E6EDF3; border-radius: 6px; }
    button { background: #238636; color: white;
             padding: 12px 24px; border: none; 
             border-radius: 6px; cursor: pointer; }
    label { display: block; margin-top: 16px; color: #8B949E; }
  </style>
</head>
<body>
  <h1>যোগাযোগ করুন</h1>
  <!-- Form বানাও -->
  <!-- নাম, ইমেইল, ফোন (optional), বিষয় (select), বার্তা, submit -->
</body>
</html>""",
            hints = listOf(
                "form action='#' method='POST' দিয়ে শুরু করো",
                "label এর for ও input এর id একই হতে হবে",
                "required attribute দিয়ে validation যোগ করো",
                "select tag দিয়ে বিষয়ের dropdown তৈরি করো",
                "textarea দিয়ে message field তৈরি করো",
                "pattern attribute দিয়ে phone number validate করো"
            ),
            requiredChapter = "ch8"
        ),

        Project(
            id = "proj_5",
            title = "প্রোডাক্ট কার্ড",
            description = "একটি e-commerce product card তৈরি করো। ছবি, নাম, দাম, rating, buy button থাকবে।",
            difficulty = ProjectDifficulty.MEDIUM,
            estimatedMinutes = 35,
            tags = listOf("HTML", "CSS", "Semantic"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>Product Card</title>
  <style>
    body { background: #0D1117; display: flex; 
           justify-content: center; align-items: center; 
           min-height: 100vh; margin: 0; }
    .card { background: #161B22; border: 1px solid #30363D;
            border-radius: 12px; width: 300px; overflow: hidden; }
    /* বাকি CSS তুমি লেখো */
  </style>
</head>
<body>
  <article class="card">
    <!-- Product image -->
    <!-- Product name (h2/h3) -->
    <!-- Rating (SVG stars বা text) -->
    <!-- Description (p) -->
    <!-- Price (original ও discounted) -->
    <!-- Add to cart button -->
  </article>
</body>
</html>""",
            hints = listOf(
                "article tag semantic product card-এর জন্য উপযুক্ত",
                "figure ও figcaption দিয়ে product image রাখো",
                "s tag দিয়ে পুরনো দাম দেখাও",
                "strong tag দিয়ে নতুন দাম highlight করো",
                "button type='button' দিয়ে add to cart বানাও",
                "meta itemprop দিয়ে structured data যোগ করো"
            ),
            requiredChapter = "ch9"
        ),

        Project(
            id = "proj_6",
            title = "নিউজলেটার Signup পেজ",
            description = "একটি attractive newsletter signup page বানাও। Email input ও subscribe button থাকবে।",
            difficulty = ProjectDifficulty.MEDIUM,
            estimatedMinutes = 30,
            tags = listOf("HTML", "Forms", "Design"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>নিউজলেটার</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { background: linear-gradient(135deg, #0D1117, #161B22);
           min-height: 100vh; display: flex; align-items: center;
           justify-content: center; font-family: Arial; }
    /* বাকি style তুমি লেখো */
  </style>
</head>
<body>
  <!-- সুন্দর একটি centered card -->
  <!-- Icon/Logo area -->
  <!-- Heading: "আমাদের নিউজলেটারে যোগ দাও" -->
  <!-- Description paragraph -->
  <!-- Email input + Subscribe button -->
  <!-- Privacy note -->
</body>
</html>""",
            hints = listOf(
                "header tag দিয়ে icon ও heading রাখো",
                "form এর ভেতরে input[type=email] ও button রাখো",
                "small tag দিয়ে privacy note লেখো",
                "fieldset ও legend ব্যবহার করে form organize করো",
                "autofocus attribute দিলে page load-এ email field focus হবে"
            ),
            requiredChapter = "ch8"
        ),

        Project(
            id = "proj_7",
            title = "Music Player UI",
            description = "HTML ও CSS দিয়ে একটি music player-এর UI তৈরি করো। Play, pause, progress bar থাকবে।",
            difficulty = ProjectDifficulty.HARD,
            estimatedMinutes = 45,
            tags = listOf("HTML", "CSS", "Audio", "UI"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>Music Player</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { background: #0D1117; display: flex; 
           align-items: center; justify-content: center;
           min-height: 100vh; font-family: Arial; color: #E6EDF3; }
    .player { background: #161B22; border-radius: 20px;
              padding: 30px; width: 320px; text-align: center; }
  </style>
</head>
<body>
  <div class="player">
    <!-- Album art (figure + img) -->
    <!-- Song name ও artist -->
    <!-- Progress bar (input type=range) -->
    <!-- Time display (current / total) -->
    <!-- Control buttons (prev, play/pause, next) -->
    <!-- Volume control -->
    <!-- HTML audio element (hidden) -->
  </div>
</body>
</html>""",
            hints = listOf(
                "figure দিয়ে album art রাখো",
                "input[type=range] দিয়ে progress ও volume slider বানাও",
                "audio element রেখে JavaScript দিয়ে control করো",
                "button গুলোতে SVG icon ব্যবহার করো",
                "time tag দিয়ে song duration semantic ভাবে দেখাও",
                "aria-label দিয়ে buttons accessible করো"
            ),
            requiredChapter = "ch13"
        ),

        Project(
            id = "proj_8",
            title = "Portfolio Website",
            description = "তোমার নিজের portfolio website বানাও। Home, About, Skills, Projects, Contact section থাকবে।",
            difficulty = ProjectDifficulty.HARD,
            estimatedMinutes = 60,
            tags = listOf("HTML", "CSS", "Semantic", "Responsive", "Full Page"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="আমার Portfolio">
  <title>Portfolio | তোমার নাম</title>
  <style>
    :root {
      --bg: #0D1117;
      --surface: #161B22;
      --border: #30363D;
      --text: #E6EDF3;
      --primary: #58A6FF;
      --accent: #3FB950;
    }
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { background: var(--bg); color: var(--text); font-family: Arial; }
    /* Navigation, Sections, Cards এর CSS লেখো */
  </style>
</head>
<body>
  <!-- Sticky Navigation -->
  <header>
    <nav>...</nav>
  </header>
  
  <!-- Hero Section -->
  <main>
    <section id="home">...</section>
    <section id="about">...</section>
    <section id="skills">...</section>
    <section id="projects">...</section>
    <section id="contact">...</section>
  </main>
  
  <footer>...</footer>
</body>
</html>""",
            hints = listOf(
                "CSS custom properties (variables) ব্যবহার করো",
                "nav-এ anchor links দিয়ে same-page navigation করো",
                "CSS Grid দিয়ে skills ও projects grid বানাও",
                "Responsive meta viewport অবশ্যই দাও",
                "form action দিয়ে contact form connect করো",
                "scroll-behavior: smooth CSS দিয়ে smooth scrolling করো",
                "JavaScript দিয়ে active nav item highlight করো"
            ),
            requiredChapter = "ch14"
        ),

        Project(
            id = "proj_9",
            title = "Canvas Animation",
            description = "HTML5 Canvas ব্যবহার করে একটি particle animation তৈরি করো।",
            difficulty = ProjectDifficulty.HARD,
            estimatedMinutes = 50,
            tags = listOf("HTML", "Canvas", "JavaScript", "Animation"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>Canvas Animation</title>
  <style>
    * { margin: 0; padding: 0; }
    body { background: #0D1117; overflow: hidden; }
    canvas { display: block; }
    #info { position: absolute; top: 20px; left: 20px;
            color: #58A6FF; font-family: Arial; font-size: 14px; }
  </style>
</head>
<body>
  <canvas id="canvas"></canvas>
  <div id="info">Particles: <span id="count">0</span></div>
  
  <script>
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    
    // Canvas resize
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    window.addEventListener('resize', () => {
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
    });
    
    // তোমার particle class এখানে বানাও
    // class Particle { ... }
    
    // Particles array তৈরি করো
    // Animation loop লেখো (requestAnimationFrame)
    // Mouse interaction যোগ করো
  </script>
</body>
</html>""",
            hints = listOf(
                "Particle class বানাও: x, y, vx, vy, radius, color property দিয়ে",
                "requestAnimationFrame দিয়ে animation loop তৈরি করো",
                "ctx.clearRect() দিয়ে প্রতি frame-এ canvas clear করো",
                "mousemove event দিয়ে mouse interaction যোগ করো",
                "particles-এর মধ্যে distance calculate করে lines আঁকো"
            ),
            requiredChapter = "ch10"
        ),

        Project(
            id = "proj_10",
            title = "Quiz App",
            description = "HTML, CSS ও JavaScript দিয়ে একটি সম্পূর্ণ Quiz app তৈরি করো।",
            difficulty = ProjectDifficulty.HARD,
            estimatedMinutes = 60,
            tags = listOf("HTML", "CSS", "JavaScript", "Logic"),
            starterCode = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quiz App</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { background: #0D1117; color: #E6EDF3; 
           font-family: Arial; display: flex;
           align-items: center; justify-content: center; 
           min-height: 100vh; padding: 20px; }
    .quiz-container { background: #161B22; border-radius: 16px;
                      padding: 30px; max-width: 600px; width: 100%; }
  </style>
</head>
<body>
  <div class="quiz-container">
    <!-- Progress bar -->
    <!-- Question number -->
    <!-- Question text -->
    <!-- Options (4 buttons) -->
    <!-- Next button -->
    <!-- Result screen (hidden initially) -->
  </div>
  
  <script>
    const questions = [
      {
        question: "HTML-এর পূর্ণ নাম কী?",
        options: ["HyperText Markup Language", "Home Tool Markup Language",
                  "HyperText Making Language", "High Text Markup Language"],
        correct: 0
      },
      // আরো questions যোগ করো
    ];
    
    // Quiz logic এখানে লেখো
    // currentQuestion tracker
    // score tracker  
    // showQuestion() function
    // checkAnswer() function
    // showResult() function
  </script>
</body>
</html>""",
            hints = listOf(
                "questions array-এ সব প্রশ্ন রাখো",
                "showQuestion() function দিয়ে current question দেখাও",
                "options-এ click করলে checkAnswer() call হবে",
                "সঠিক উত্তরে green, ভুলে red highlight করো",
                "Progress bar এর width = (current/total) * 100 + '%'",
                "result screen-এ score percentage দেখাও"
            ),
            requiredChapter = "ch13"
        )
    )
}
