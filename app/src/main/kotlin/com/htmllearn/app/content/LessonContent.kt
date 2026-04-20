package com.htmllearn.app.content

enum class Difficulty { BEGINNER, INTERMEDIATE, ADVANCED }

enum class ContentType { THEORY, CODE_EXAMPLE, NOTE, WARNING, TIP, DEFINITION }

data class ContentBlock(
    val type: ContentType,
    val text: String,
    val code: String = ""
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanation: String
)

data class Lesson(
    val id: String,
    val chapterId: String,
    val title: String,
    val subtitle: String,
    val estimatedMinutes: Int,
    val difficulty: Difficulty,
    val xpReward: Int = 50,
    val content: List<ContentBlock>,
    val codeExample: String,
    val quiz: List<QuizQuestion>
)

data class Chapter(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconKey: String,
    val color: Long,
    val lessons: List<Lesson>
)

object LessonContent {

    fun getAllLessons(): List<Lesson> = getAllChapters().flatMap { it.lessons }

    fun getLessonById(id: String): Lesson? = getAllLessons().find { it.id == id }

    fun getAllChapters(): List<Chapter> = listOf(
        chapter1(), chapter2(), chapter3(), chapter4(), chapter5(),
        chapter6(), chapter7(), chapter8(), chapter9(), chapter10(),
        chapter11(), chapter12(), chapter13(), chapter14(), chapter15()
    )

    // ==================== CHAPTER 1 ====================
    private fun chapter1() = Chapter(
        id = "ch1", title = "HTML পরিচিতি", subtitle = "HTML কী ও কেন শিখবো",
        iconKey = "ic_intro", color = 0xFF4FC3F7,
        lessons = listOf(
            Lesson(
                id = "ch1_l1", chapterId = "ch1",
                title = "HTML কী?", subtitle = "ওয়েব ভাষার পরিচয়",
                estimatedMinutes = 8, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML (HyperText Markup Language) হলো ওয়েব পেজ তৈরির মূল ভাষা। এটি কোনো প্রোগ্রামিং ভাষা নয়, বরং একটি Markup Language — অর্থাৎ এটি দিয়ে আমরা content-কে চিহ্নিত করি বা সাজাই।"),
                    ContentBlock(ContentType.DEFINITION, "HyperText: এমন text যা অন্য page-এর সাথে link করা যায়।\nMarkup Language: Content-কে tags দিয়ে চিহ্নিত করার ভাষা।"),
                    ContentBlock(ContentType.THEORY, "১৯৯১ সালে Tim Berners-Lee HTML আবিষ্কার করেন। তখন থেকে আজ পর্যন্ত সব ওয়েবসাইটের ভিত্তি হলো HTML। Facebook, YouTube, Google — সব কিছুর ভেতরে HTML আছে।"),
                    ContentBlock(ContentType.NOTE, "HTML ফাইলের extension হয় .html বা .htm"),
                    ContentBlock(ContentType.TIP, "HTML শিখতে শুধু একটি text editor (Notepad/VS Code) এবং Browser লাগে — আর কিছু লাগে না!")
                ),
                codeExample = """<!DOCTYPE html>
<html>
  <head>
    <title>আমার প্রথম পেজ</title>
  </head>
  <body>
    <h1>নমস্কার, পৃথিবী!</h1>
    <p>এটি আমার প্রথম HTML পেজ।</p>
  </body>
</html>""",
                quiz = listOf(
                    QuizQuestion("HTML-এর পূর্ণ নাম কী?",
                        listOf("HyperText Markup Language","HyperText Making Language","High Text Markup Language","HyperText Modern Language"),
                        0, "HTML = HyperText Markup Language। এটি ওয়েব পেজের মূল ভাষা।"),
                    QuizQuestion("HTML কে আবিষ্কার করেন?",
                        listOf("Bill Gates","Steve Jobs","Tim Berners-Lee","Mark Zuckerberg"),
                        2, "১৯৯১ সালে Tim Berners-Lee HTML আবিষ্কার করেন।"),
                    QuizQuestion("HTML কী ধরনের ভাষা?",
                        listOf("Programming Language","Markup Language","Database Language","Script Language"),
                        1, "HTML একটি Markup Language, Programming Language নয়।"),
                    QuizQuestion("HTML ফাইলের সঠিক extension কোনটি?",
                        listOf(".xml",".html",".css",".txt"),
                        1, "HTML ফাইলের extension হয় .html বা .htm"),
                    QuizQuestion("HTML শিখতে কোনটি প্রয়োজন?",
                        listOf("শুধু Browser","Text Editor ও Browser","শুধু Server","Database"),
                        1, "HTML শিখতে শুধু একটি Text Editor ও Browser দরকার।")
                )
            ),
            Lesson(
                id = "ch1_l2", chapterId = "ch1",
                title = "Browser কীভাবে কাজ করে", subtitle = "HTML থেকে ওয়েবপেজ হওয়ার যাত্রা",
                estimatedMinutes = 10, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "আমরা যখন browser-এ একটি website-এর address লিখি, তখন browser সেই server থেকে HTML ফাইল নামিয়ে নেয়। তারপর সেই HTML কোড পড়ে ও বুঝে এবং সুন্দরভাবে আমাদের সামনে দেখায়।"),
                    ContentBlock(ContentType.THEORY, "এই প্রক্রিয়াকে Rendering বলে। Browser HTML কে parse করে একটি DOM (Document Object Model) তৈরি করে। এই DOM-ই আসলে আমরা screen-এ দেখি।"),
                    ContentBlock(ContentType.DEFINITION, "DOM = Document Object Model। HTML-এর প্রতিটি element একটি Object হিসেবে browser-এ থাকে, যেগুলো একটি tree structure-এ সাজানো।"),
                    ContentBlock(ContentType.NOTE, "Chrome, Firefox, Safari, Edge — সব browser HTML render করতে পারে। তবে কিছু ক্ষেত্রে সামান্য পার্থক্য দেখা যেতে পারে।"),
                    ContentBlock(ContentType.TIP, "Chrome DevTools (F12) দিয়ে যেকোনো website-এর HTML দেখা ও edit করা যায়। এটি শেখার দারুণ উপায়!")
                ),
                codeExample = """<!-- Browser এই HTML পড়ে screen-এ দেখায় -->
<!DOCTYPE html>
<html lang="bn">
  <head>
    <meta charset="UTF-8">
    <title>Browser Test</title>
  </head>
  <body>
    <h1>Browser এটি দেখাবে</h1>
    <!-- এটি comment — browser দেখাবে না -->
    <p>এই paragraph টি screen-এ দেখাবে।</p>
  </body>
</html>""",
                quiz = listOf(
                    QuizQuestion("Browser HTML কোড process করাকে কী বলে?",
                        listOf("Compiling","Rendering","Debugging","Parsing"), 1,
                        "Browser HTML কে Render করে অর্থাৎ screen-এ দেখানোর উপযোগী করে।"),
                    QuizQuestion("DOM এর পূর্ণ নাম কী?",
                        listOf("Data Object Model","Document Object Model","Design Object Model","Dynamic Object Model"), 1,
                        "DOM = Document Object Model। Browser HTML থেকে এটি তৈরি করে।"),
                    QuizQuestion("HTML Comment কোন চিহ্ন দিয়ে লেখা হয়?",
                        listOf("// comment //","/* comment */","<!-- comment -->","## comment ##"), 2,
                        "HTML comment লেখা হয় <!-- --> এই চিহ্ন দিয়ে।"),
                    QuizQuestion("Chrome DevTools খুলতে কোন key চাপতে হয়?",
                        listOf("F5","F10","F12","F1"), 2,
                        "F12 চাপলে Chrome DevTools খুলে যায়।"),
                    QuizQuestion("lang=\"bn\" attribute কী কাজ করে?",
                        listOf("ভাষা বাংলা করে","Page বাংলায় translate করে","Browser কে জানায় page-এর ভাষা বাংলা","Font বাংলা করে"), 2,
                        "lang attribute browser ও search engine কে জানায় page-এর ভাষা কী।")
                )
            ),
            Lesson(
                id = "ch1_l3", chapterId = "ch1",
                title = "প্রথম HTML পেজ তৈরি", subtitle = "হাতে-কলমে শেখা শুরু",
                estimatedMinutes = 12, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "এখন আমরা নিজেই একটি HTML পেজ তৈরি করবো। Step by step দেখি।"),
                    ContentBlock(ContentType.THEORY, "ধাপ ১: Notepad বা VS Code খোলো।\nধাপ ২: নিচের code লেখো।\nধাপ ৩: ফাইলটি 'mypage.html' নামে save করো।\nধাপ ৪: ফাইলটি Browser-এ open করো।"),
                    ContentBlock(ContentType.DEFINITION, "<!DOCTYPE html> — এটি browser কে জানায় যে এটি HTML5 document। এটি সবসময় প্রথম line-এ থাকে।"),
                    ContentBlock(ContentType.NOTE, "<html> tag দিয়ে পেজ শুরু হয় এবং </html> দিয়ে শেষ হয়। সব content এর ভেতরে থাকে।"),
                    ContentBlock(ContentType.TIP, "VS Code ব্যবহার করলে '!' লিখে Tab চাপলে পুরো HTML structure automatically তৈরি হয়!")
                ),
                codeExample = """<!DOCTYPE html>
<html lang="bn">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>আমার ওয়েবসাইট</title>
  </head>
  <body>
    <h1>স্বাগতম!</h1>
    <h2>আমার প্রথম ওয়েবসাইট</h2>
    <p>আমি HTML শিখছি। এটি আমার প্রথম পেজ।</p>
    <p>HTML শেখা অনেক মজার!</p>
  </body>
</html>""",
                quiz = listOf(
                    QuizQuestion("HTML file save করার সময় কোন extension দিতে হয়?",
                        listOf(".txt",".html",".doc",".web"), 1,
                        "HTML ফাইলের extension হয় .html"),
                    QuizQuestion("<!DOCTYPE html> কোথায় লিখতে হয়?",
                        listOf("যেকোনো জায়গায়","Body-র ভেতরে","সবার প্রথমে","Head-এর ভেতরে"), 2,
                        "<!DOCTYPE html> সবসময় file-এর একদম প্রথমে লিখতে হয়।"),
                    QuizQuestion("Page-এর title কোথায় লেখা হয়?",
                        listOf("<body> tag-এ","<head> tag-এর ভেতরে <title> tag-এ","<h1> tag-এ","<title> tag যেকোনো জায়গায়"), 1,
                        "Page title লেখা হয় <head> এর ভেতরে <title> tag-এ।"),
                    QuizQuestion("VS Code-এ HTML structure তৈরি করতে কী লিখতে হয়?",
                        listOf("html","html5","!","#html"), 2,
                        "VS Code-এ '!' লিখে Tab চাপলে HTML boilerplate তৈরি হয়।"),
                    QuizQuestion("charset=\"UTF-8\" কী কাজ করে?",
                        listOf("পেজ encrypt করে","বাংলাসহ সব ভাষার character সঠিক দেখায়","Internet speed বাড়ায়","Font set করে"), 1,
                        "UTF-8 character encoding বাংলাসহ সব ভাষার character সঠিকভাবে দেখাতে সাহায্য করে।")
                )
            )
        )
    )

    // ==================== CHAPTER 2 ====================
    private fun chapter2() = Chapter(
        id = "ch2", title = "HTML গঠন", subtitle = "Tags ও Elements বোঝা",
        iconKey = "ic_structure", color = 0xFF81C784,
        lessons = listOf(
            Lesson(
                id = "ch2_l1", chapterId = "ch2",
                title = "HTML Tags ও Elements", subtitle = "মূল ধারণা বোঝা",
                estimatedMinutes = 10, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML-এ সব কিছু লেখা হয় Tags দিয়ে। Tag হলো angle brackets (<>) দিয়ে ঘেরা keyword। যেমন: <p>, <h1>, <div>।"),
                    ContentBlock(ContentType.DEFINITION, "Opening Tag: <p> — শুরু নির্দেশ করে\nClosing Tag: </p> — শেষ নির্দেশ করে (/ চিহ্ন থাকে)\nElement = Opening Tag + Content + Closing Tag"),
                    ContentBlock(ContentType.THEORY, "কিছু tag self-closing — এদের আলাদা closing tag নেই। যেমন: <br>, <img>, <input>। এগুলোকে Void Elements বলে।"),
                    ContentBlock(ContentType.NOTE, "HTML case-sensitive নয় — <P> আর <p> একই। তবে lowercase লেখার অভ্যাস করাই ভালো।"),
                    ContentBlock(ContentType.TIP, "Attributes হলো tag-এর ভেতরে extra information। যেমন: <a href='url'> — এখানে href হলো attribute।")
                ),
                codeExample = """<!-- Tag ও Element-এর উদাহরণ -->
<p>এটি একটি paragraph element।</p>

<!-- Attribute সহ element -->
<a href="https://google.com">Google-এ যাও</a>

<!-- Self-closing tag -->
<br>
<img src="photo.jpg" alt="ছবি">
<input type="text" placeholder="লেখো...">

<!-- Nested elements (একটার ভেতরে আরেকটা) -->
<p>এই text-এ <strong>bold অংশ</strong> আছে।</p>""",
                quiz = listOf(
                    QuizQuestion("Closing tag-এ কোন চিহ্ন থাকে?",
                        listOf("*","/","#","@"), 1, "Closing tag-এ / চিহ্ন থাকে, যেমন </p>"),
                    QuizQuestion("Self-closing tag-এর উদাহরণ কোনটি?",
                        listOf("<p>","<div>","<br>","<span>"), 2, "<br> একটি self-closing void element।"),
                    QuizQuestion("Element = ?",
                        listOf("Opening Tag","Closing Tag","Opening Tag + Content + Closing Tag","Content only"), 2,
                        "Element = Opening Tag + Content + Closing Tag"),
                    QuizQuestion("<a href='url'> — এখানে href কী?",
                        listOf("Element","Tag","Attribute","Class"), 2,
                        "href হলো একটি attribute যা <a> tag-এ link address দেয়।"),
                    QuizQuestion("HTML case-sensitive কি?",
                        listOf("হ্যাঁ, সবসময়","না, case-sensitive নয়","শুধু tag-এর ক্ষেত্রে","শুধু attribute-এর ক্ষেত্রে"), 1,
                        "HTML case-sensitive নয়, তবে lowercase লেখার অভ্যাস ভালো।")
                )
            ),
            Lesson(
                id = "ch2_l2", chapterId = "ch2",
                title = "HTML Document Structure", subtitle = "সঠিক কাঠামো শেখা",
                estimatedMinutes = 12, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "একটি সম্পূর্ণ HTML document-এর নির্দিষ্ট কাঠামো থাকে। এই কাঠামো মেনে চলা জরুরি।"),
                    ContentBlock(ContentType.DEFINITION, "<!DOCTYPE html> : Document type declaration\n<html> : Root element, পুরো page ধারণ করে\n<head> : Page-এর metadata (title, charset, links)\n<body> : দৃশ্যমান সব content এখানে"),
                    ContentBlock(ContentType.THEORY, "<head> section-এ যা থাকে: Page title, character encoding, CSS links, meta tags, JavaScript links। এগুলো সরাসরি screen-এ দেখা যায় না কিন্তু page-এর জন্য গুরুত্বপূর্ণ।"),
                    ContentBlock(ContentType.NOTE, "<body> section-এ যা আছে তাই browser screen-এ দেখায় — text, images, buttons, forms সব কিছু।"),
                    ContentBlock(ContentType.WARNING, "Proper indentation (ভেতরে সরিয়ে লেখা) করলে code পড়া সহজ হয়। এটি অভ্যাস করা উচিত।")
                ),
                codeExample = """<!DOCTYPE html>
<html lang="bn">

  <!-- HEAD: browser ও search engine এর জন্য তথ্য -->
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="আমার ওয়েবসাইটের বিবরণ">
    <meta name="author" content="Shohan">
    <title>সুন্দর একটি পেজ</title>
    <!-- CSS link এখানে আসবে -->
    <!-- JavaScript link এখানে আসবে -->
  </head>

  <!-- BODY: user যা দেখবে -->
  <body>
    <header>
      <h1>ওয়েবসাইটের নাম</h1>
      <nav>Navigation এখানে</nav>
    </header>

    <main>
      <h2>মূল Content</h2>
      <p>এখানে লেখা থাকবে।</p>
    </main>

    <footer>
      <p>Footer information</p>
    </footer>
  </body>

</html>""",
                quiz = listOf(
                    QuizQuestion("Page-এর দৃশ্যমান content কোথায় লেখা হয়?",
                        listOf("<head>","<html>","<body>","<meta>"), 2,
                        "দৃশ্যমান সব content <body> tag-এর ভেতরে লেখা হয়।"),
                    QuizQuestion("<meta charset='UTF-8'> কোথায় থাকে?",
                        listOf("<body>","<head>","<html>","<footer>"), 1,
                        "<meta> tag সবসময় <head> section-এ থাকে।"),
                    QuizQuestion("Root element কোনটি?",
                        listOf("<head>","<body>","<html>","<!DOCTYPE>"), 2,
                        "<html> হলো root element যা সব কিছু ধারণ করে।"),
                    QuizQuestion("Indentation কেন করা হয়?",
                        listOf("Page দ্রুত load করতে","Code পড়া সহজ করতে","Browser error কমাতে","File size কমাতে"), 1,
                        "Indentation code-এর readability বাড়ায়।"),
                    QuizQuestion("<meta name='description'> কী কাজ করে?",
                        listOf("Page speed বাড়ায়","Search result-এ page-এর বিবরণ দেখায়","Page title set করে","Font set করে"), 1,
                        "description meta tag search engine-এ page-এর বিবরণ দেখাতে ব্যবহৃত হয়।")
                )
            ),
            Lesson(
                id = "ch2_l3", chapterId = "ch2",
                title = "Nesting ও Indentation", subtitle = "পরিষ্কার কোড লেখার কৌশল",
                estimatedMinutes = 10, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML-এ একটি element-এর ভেতরে আরেকটি element রাখাকে Nesting বলে। এটি HTML-এর একটি মূল বৈশিষ্ট্য।"),
                    ContentBlock(ContentType.THEORY, "Nesting করার সময় মনে রাখতে হবে: যে tag আগে খোলে, সে tag পরে বন্ধ হয়। ভুল nesting browser-এ সমস্যা তৈরি করতে পারে।"),
                    ContentBlock(ContentType.WARNING, "ভুল: <b><i>text</b></i>\nসঠিক: <b><i>text</i></b>\nনিয়ম: ভেতরের tag আগে বন্ধ হবে।"),
                    ContentBlock(ContentType.NOTE, "Indentation মানে প্রতিটি nested element কে tab বা space দিয়ে ভেতরে সরিয়ে লেখা। এতে code-এর structure স্পষ্ট হয়।"),
                    ContentBlock(ContentType.TIP, "VS Code-এ Prettier extension install করলে automatic indentation হয়ে যায়!")
                ),
                codeExample = """<!-- সঠিক Nesting -->
<div>
  <h2>শিরোনাম</h2>
  <p>এই paragraph-এ <strong>bold</strong> এবং 
    <em>italic</em> text আছে।</p>
  <ul>
    <li>প্রথম item</li>
    <li>দ্বিতীয় item
      <ul>
        <li>Nested item</li>
      </ul>
    </li>
  </ul>
</div>

<!-- ভুল Nesting (এভাবে করবে না) -->
<!-- <b><i>text</b></i> — ভুল! -->
<!-- সঠিক: <b><i>text</i></b> -->""",
                quiz = listOf(
                    QuizQuestion("Nesting মানে কী?",
                        listOf("Tag বড় করা","একটি element-এর ভেতরে আরেকটি রাখা","Tag সরানো","Comment লেখা"), 1,
                        "Nesting মানে একটি element-এর ভেতরে আরেকটি element রাখা।"),
                    QuizQuestion("<b><i>text</b></i> — এটি কি সঠিক?",
                        listOf("হ্যাঁ","না, ভুল","ঐচ্ছিক","Browser অনুযায়ী"), 1,
                        "সঠিক হবে <b><i>text</i></b> — ভেতরের tag আগে বন্ধ হবে।"),
                    QuizQuestion("Indentation কী দিয়ে করা হয়?",
                        listOf("Tab বা Space","Enter","Backspace","Delete"), 0,
                        "Indentation Tab বা Space দিয়ে করা হয়।"),
                    QuizQuestion("VS Code-এ automatic indentation-এর জন্য কোন extension ব্যবহার করা যায়?",
                        listOf("Minify","Prettier","Debugger","Live Server"), 1,
                        "Prettier extension automatic code formatting করে।"),
                    QuizQuestion("কোনটি সঠিক nesting?",
                        listOf("<p><div>text</div></p>","<p><span>text</span></p>","<span><div>text</div></span>","<div><p>text</p>"), 1,
                        "<span> একটি inline element, তাই <p>-এর ভেতরে ব্যবহার সঠিক।")
                )
            )
        )
    )

    // ==================== CHAPTER 3 ====================
    private fun chapter3() = Chapter(
        id = "ch3", title = "Text ও Heading Tags", subtitle = "লেখা সাজানোর tags",
        iconKey = "ic_text", color = 0xFFFFB74D,
        lessons = listOf(
            Lesson(
                id = "ch3_l1", chapterId = "ch3",
                title = "Heading Tags (h1-h6)", subtitle = "শিরোনাম তৈরি করা",
                estimatedMinutes = 8, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML-এ ৬ ধরনের heading tag আছে — h1 থেকে h6। h1 সবচেয়ে বড় ও গুরুত্বপূর্ণ, h6 সবচেয়ে ছোট।"),
                    ContentBlock(ContentType.THEORY, "Heading tag শুধু বড় font-এর জন্য নয়। এটি page-এর structure বোঝায় এবং SEO (Search Engine Optimization)-এর জন্য গুরুত্বপূর্ণ।"),
                    ContentBlock(ContentType.WARNING, "একটি পেজে মাত্র একটি <h1> থাকা উচিত। এটি মূল শিরোনাম। একাধিক h1 SEO-তে সমস্যা তৈরি করে।"),
                    ContentBlock(ContentType.NOTE, "h1 > h2 > h3 > h4 > h5 > h6 — এই hierarchy মেনে চলতে হবে। h1 এর পরে সরাসরি h3 দেওয়া উচিত নয়।"),
                    ContentBlock(ContentType.TIP, "Heading-এর size বা color CSS দিয়ে পরিবর্তন করা যায়। তাই সঠিক heading level ব্যবহার করো, visual appearance পরে ঠিক করো।")
                ),
                codeExample = """<h1>প্রধান শিরোনাম (সবচেয়ে বড়)</h1>
<h2>উপশিরোনাম</h2>
<h3>ধারা শিরোনাম</h3>
<h4>উপধারা শিরোনাম</h4>
<h5>ছোট শিরোনাম</h5>
<h6>সবচেয়ে ছোট শিরোনাম</h6>

<!-- বাস্তব উদাহরণ -->
<h1>HTML শেখার গাইড</h1>
  <h2>অধ্যায় ১: পরিচিতি</h2>
    <h3>HTML কী?</h3>
    <h3>কেন শিখবো?</h3>
  <h2>অধ্যায় ২: Tags</h2>
    <h3>Basic Tags</h3>""",
                quiz = listOf(
                    QuizQuestion("কোন heading tag সবচেয়ে বড়?",
                        listOf("h6","h3","h1","h2"), 2, "h1 সবচেয়ে বড় heading tag।"),
                    QuizQuestion("একটি পেজে কতটি h1 থাকা উচিত?",
                        listOf("যতটা দরকার","দুটি","মাত্র একটি","তিনটি"), 2,
                        "SEO-এর জন্য একটি পেজে মাত্র একটি h1 থাকা উচিত।"),
                    QuizQuestion("Heading tag কেন গুরুত্বপূর্ণ?",
                        listOf("শুধু বড় font-এর জন্য","SEO ও page structure-এর জন্য","Color পরিবর্তনের জন্য","শুধু সুন্দর দেখানোর জন্য"), 1,
                        "Heading tag SEO ও page structure উভয়ের জন্যই গুরুত্বপূর্ণ।"),
                    QuizQuestion("কোন heading সবচেয়ে ছোট?",
                        listOf("h4","h5","h6","h3"), 2, "h6 সবচেয়ে ছোট heading।"),
                    QuizQuestion("h1 এর পরে সরাসরি কোনটি দেওয়া উচিত?",
                        listOf("h4","h6","h2","h5"), 2, "h1 এর পরে h2 দেওয়া hierarchy অনুযায়ী সঠিক।")
                )
            ),
            Lesson(
                id = "ch3_l2", chapterId = "ch3",
                title = "Paragraph ও Text Formatting", subtitle = "লেখা সুন্দরভাবে সাজানো",
                estimatedMinutes = 12, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "<p> tag দিয়ে paragraph লেখা হয়। প্রতিটি paragraph নতুন line-এ শুরু হয় এবং উপরে-নিচে margin থাকে।"),
                    ContentBlock(ContentType.DEFINITION, "<strong> : গুরুত্বপূর্ণ text (bold দেখায়)\n<em> : জোর দেওয়া text (italic দেখায়)\n<u> : underline\n<s> : strikethrough (কাটা)\n<mark> : highlight করা\n<small> : ছোট text\n<sup> : উপরে (x²)\n<sub> : নিচে (H₂O)"),
                    ContentBlock(ContentType.NOTE, "<br> tag দিয়ে line break দেওয়া যায়। <br> একটি void element — closing tag নেই।"),
                    ContentBlock(ContentType.WARNING, "শুধু বড় font-এর জন্য <strong> ব্যবহার করা ঠিক নয়। semantic meaning (গুরুত্বপূর্ণ বোঝাতে) ব্যবহার করো।"),
                    ContentBlock(ContentType.TIP, "HTML-এ multiple space বা Enter দিলেও browser একটি space দেখায়। তাই &nbsp; বা CSS ব্যবহার করতে হয়।")
                ),
                codeExample = """<p>এটি একটি সাধারণ paragraph। HTML শেখা অনেক মজার।</p>

<p>এই text-এ <strong>গুরুত্বপূর্ণ অংশ</strong> bold দেখাবে।</p>
<p><em>এই text italic হবে</em> — জোর দেওয়ার জন্য।</p>
<p><u>underline text</u> এবং <s>কাটা text</s></p>
<p><mark>হলুদ highlight</mark> দিয়ে জিনিস চিহ্নিত করা যায়।</p>

<!-- Superscript ও Subscript -->
<p>পানির সূত্র: H<sub>2</sub>O</p>
<p>বর্গক্ষেত্রের ক্ষেত্রফল: a<sup>2</sup></p>

<!-- Line break -->
<p>প্রথম লাইন<br>দ্বিতীয় লাইন</p>

<!-- Special characters -->
<p>মূল্য: ১০০&nbsp;টাকা &amp; ভ্যাট: ১৫%</p>""",
                quiz = listOf(
                    QuizQuestion("Line break দিতে কোন tag ব্যবহার করা হয়?",
                        listOf("<lb>","<line>","<br>","<break>"), 2, "<br> tag দিয়ে line break দেওয়া হয়।"),
                    QuizQuestion("H₂O লিখতে 2 কোন tag-এ থাকবে?",
                        listOf("<sup>","<sub>","<small>","<span>"), 1, "<sub> tag text নিচে নামায়, যেমন H₂O"),
                    QuizQuestion("<strong> tag কী করে?",
                        listOf("Italic করে","Bold দেখায় ও গুরুত্ব বোঝায়","Underline করে","Color পরিবর্তন করে"), 1,
                        "<strong> semantic bold করে, শুধু visual নয়।"),
                    QuizQuestion("HTML-এ &amp; কী দেখাবে?",
                        listOf("&","amp","&&","and"), 0, "&amp; HTML entity যা & চিহ্ন দেখায়।"),
                    QuizQuestion("x² লিখতে 2 কোন tag-এ থাকবে?",
                        listOf("<sub>","<big>","<sup>","<up>"), 2, "<sup> text উপরে নিয়ে যায়।")
                )
            ),
            Lesson(
                id = "ch3_l3", chapterId = "ch3",
                title = "Div, Span ও Semantic Tags", subtitle = "Container elements",
                estimatedMinutes = 12, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "<div> হলো block-level container — পুরো একটি row জুড়ে থাকে। <span> হলো inline container — text-এর মধ্যে থাকে।"),
                    ContentBlock(ContentType.DEFINITION, "Block element: নতুন line-এ শুরু হয়, পুরো width নেয় (<div>, <p>, <h1>)\nInline element: text-এর মধ্যে থাকে, শুধু নিজের জায়গা নেয় (<span>, <a>, <strong>)"),
                    ContentBlock(ContentType.THEORY, "HTML5-এ Semantic tags এসেছে যেগুলো content-এর অর্থ বোঝায়: <header>, <nav>, <main>, <footer>, <article>, <section>, <aside>। এগুলো <div>-এর চেয়ে অনেক বেশি meaningful।"),
                    ContentBlock(ContentType.NOTE, "Semantic HTML ব্যবহারের সুবিধা: SEO ভালো হয়, accessibility বাড়ে, code বোঝা সহজ হয়।"),
                    ContentBlock(ContentType.TIP, "যেখানে semantic tag ব্যবহার করা যায়, সেখানে div/span ব্যবহার না করাই ভালো।")
                ),
                codeExample = """<!-- div: block container -->
<div class="card">
  <h2>Article Title</h2>
  <p>Article content এখানে। <span class="highlight">গুরুত্বপূর্ণ</span> অংশ।</p>
</div>

<!-- Semantic HTML5 layout -->
<header>
  <h1>ওয়েবসাইটের নাম</h1>
  <nav>
    <a href="/">হোম</a>
    <a href="/about">পরিচিতি</a>
  </nav>
</header>

<main>
  <article>
    <h2>Article শিরোনাম</h2>
    <section>
      <p>Section content</p>
    </section>
  </article>
  <aside>
    <p>Sidebar content</p>
  </aside>
</main>

<footer>
  <p>&copy; 2024 আমার ওয়েবসাইট</p>
</footer>""",
                quiz = listOf(
                    QuizQuestion("<div> কোন ধরনের element?",
                        listOf("Inline","Block","Void","Semantic"), 1, "<div> একটি block-level element।"),
                    QuizQuestion("<span> কোন ধরনের element?",
                        listOf("Block","Void","Inline","Semantic"), 2, "<span> একটি inline element।"),
                    QuizQuestion("Navigation menu কোন semantic tag-এ রাখা হয়?",
                        listOf("<header>","<nav>","<aside>","<menu>"), 1, "<nav> tag navigation links ধারণ করে।"),
                    QuizQuestion("Semantic HTML ব্যবহারের কারণ কী?",
                        listOf("শুধু SEO","SEO, Accessibility ও Readability","শুধু সুন্দর দেখানো","Page speed বাড়ানো"), 1,
                        "Semantic HTML SEO, accessibility ও code readability উন্নত করে।"),
                    QuizQuestion("<footer> কোথায় থাকে?",
                        listOf("Page-এর উপরে","Page-এর মাঝে","Page-এর নিচে","Nav-এর ভেতরে"), 2, "<footer> page-এর নিচের অংশ।")
                )
            )
        )
    )

    // ==================== CHAPTER 4 ====================
    private fun chapter4() = Chapter(
        id = "ch4", title = "Links ও Navigation", subtitle = "Page-এ link তৈরি করা",
        iconKey = "ic_link", color = 0xFFBA68C8,
        lessons = listOf(
            Lesson(
                id = "ch4_l1", chapterId = "ch4",
                title = "Anchor Tag ও Links", subtitle = "এক পেজ থেকে আরেক পেজে যাওয়া",
                estimatedMinutes = 10, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "<a> tag (anchor tag) দিয়ে link তৈরি করা হয়। href attribute-এ link address দেওয়া হয়। ক্লিক করলে সেই page-এ যাওয়া যায়।"),
                    ContentBlock(ContentType.DEFINITION, "href (Hypertext REFerence): link-এর destination address\ntarget: কোথায় খুলবে — _blank মানে নতুন tab-এ\nrel: link-এর relationship (nofollow, noopener)"),
                    ContentBlock(ContentType.THEORY, "৩ ধরনের link আছে:\n১. Absolute URL: সম্পূর্ণ address (https://example.com)\n২. Relative URL: নিজের site-এর ভেতরে (about.html)\n৩. Fragment: একই page-এর ভেতরে (#section)"),
                    ContentBlock(ContentType.NOTE, "নতুন tab-এ link খুলতে target='_blank' ব্যবহার করো। তখন security-র জন্য rel='noopener noreferrer' যোগ করো।"),
                    ContentBlock(ContentType.TIP, "Link-এর color ও style CSS দিয়ে পরিবর্তন করা যায়। Default নীল underline হয়।")
                ),
                codeExample = """<!-- External link (অন্য website) -->
<a href="https://google.com">Google-এ যাও</a>

<!-- নতুন tab-এ খুলবে -->
<a href="https://youtube.com" target="_blank" 
   rel="noopener noreferrer">YouTube (নতুন tab)</a>

<!-- Internal link (নিজের site) -->
<a href="about.html">আমার পরিচয়</a>
<a href="../index.html">হোম পেজ</a>

<!-- Same page-এর section-এ যাওয়া -->
<a href="#contact">যোগাযোগ বিভাগে যাও</a>

<!-- Email link -->
<a href="mailto:shohan@email.com">ইমেইল করো</a>

<!-- Phone link -->
<a href="tel:+8801712345678">কল করো</a>

<!-- Download link -->
<a href="document.pdf" download>PDF ডাউনলোড</a>

<!-- Destination section -->
<section id="contact">
  <h2>যোগাযোগ</h2>
</section>""",
                quiz = listOf(
                    QuizQuestion("Link-এর address কোন attribute-এ দেওয়া হয়?",
                        listOf("src","link","href","url"), 2, "href attribute-এ link address দেওয়া হয়।"),
                    QuizQuestion("নতুন tab-এ link খুলতে কোন attribute ব্যবহার করা হয়?",
                        listOf("target='_new'","target='_blank'","target='_tab'","target='_open'"), 1,
                        "target='_blank' নতুন tab-এ link খোলে।"),
                    QuizQuestion("Email link তৈরি করতে href-এ কী লিখতে হয়?",
                        listOf("email:","mail:","mailto:","send:"), 2, "mailto: prefix দিয়ে email link তৈরি হয়।"),
                    QuizQuestion("Same page-এর section-এ link করতে href-এ কী দিতে হয়?",
                        listOf("section:id","#id","//id","@id"), 1, "#id দিয়ে same page-এর section-এ link করা যায়।"),
                    QuizQuestion("target='_blank' এর সাথে কোন security attribute যোগ করা উচিত?",
                        listOf("rel='safe'","rel='noopener noreferrer'","rel='secure'","rel='trust'"), 1,
                        "Security-র জন্য rel='noopener noreferrer' যোগ করা উচিত।")
                )
            ),
            Lesson(
                id = "ch4_l2", chapterId = "ch4",
                title = "Navigation Menu তৈরি", subtitle = "Professional nav bar",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "ওয়েবসাইটের navigation menu সাধারণত <nav> tag-এর ভেতরে <ul> ও <li> দিয়ে তৈরি হয়।"),
                    ContentBlock(ContentType.THEORY, "Hamburger menu, dropdown menu সব কিছু মূলে এই structure-এর উপর তৈরি।"),
                    ContentBlock(ContentType.NOTE, "Active link বোঝাতে class='active' ব্যবহার করা হয় এবং CSS দিয়ে style করা হয়।"),
                    ContentBlock(ContentType.TIP, "Accessibility-র জন্য <nav aria-label='main navigation'> লেখা ভালো অভ্যাস।")
                ),
                codeExample = """<nav aria-label="main navigation">
  <ul>
    <li><a href="/" class="active">হোম</a></li>
    <li><a href="/lessons">পাঠ্যক্রম</a></li>
    <li>
      <a href="/more">আরো</a>
      <!-- Dropdown -->
      <ul class="dropdown">
        <li><a href="/projects">প্রজেক্ট</a></li>
        <li><a href="/quiz">কুইজ</a></li>
      </ul>
    </li>
    <li><a href="/contact">যোগাযোগ</a></li>
  </ul>
</nav>

<!-- Breadcrumb navigation -->
<nav aria-label="breadcrumb">
  <ol>
    <li><a href="/">হোম</a></li>
    <li><a href="/lessons">পাঠ</a></li>
    <li>HTML Links</li>
  </ol>
</nav>""",
                quiz = listOf(
                    QuizQuestion("Navigation menu সাধারণত কোন tag-এ থাকে?",
                        listOf("<menu>","<nav>","<header>","<ul>"), 1, "<nav> semantic tag navigation-এর জন্য।"),
                    QuizQuestion("Active page link বোঝাতে কী ব্যবহার করা হয়?",
                        listOf("id='active'","class='active'","type='active'","link='active'"), 1,
                        "class='active' দিয়ে active link চিহ্নিত করা হয়।"),
                    QuizQuestion("Breadcrumb navigation কী?",
                        listOf("Main nav","আপনি কোথায় আছেন তা দেখানো","Footer nav","Social links"), 1,
                        "Breadcrumb user-কে দেখায় সে website-এ কোন level-এ আছে।"),
                    QuizQuestion("aria-label attribute কেন ব্যবহার করা হয়?",
                        listOf("SEO-র জন্য","Accessibility-র জন্য","Style-এর জন্য","Speed-এর জন্য"), 1,
                        "aria-label screen reader ব্যবহারকারীদের জন্য accessibility বাড়ায়।"),
                    QuizQuestion("Dropdown menu-র সঠিক structure কোনটি?",
                        listOf("<ul> এর ভেতরে আরেকটি <ul>","<div> এর ভেতরে <a>","<nav> এর ভেতরে <p>","<table> দিয়ে"), 0,
                        "Dropdown menu nested <ul> দিয়ে তৈরি হয়।")
                )
            )
        )
    )

    // ==================== CHAPTER 5 ====================
    private fun chapter5() = Chapter(
        id = "ch5", title = "Images ও Media", subtitle = "ছবি ও মিডিয়া যোগ করা",
        iconKey = "ic_image", color = 0xFFFF8A65,
        lessons = listOf(
            Lesson(
                id = "ch5_l1", chapterId = "ch5",
                title = "Images — img Tag", subtitle = "ওয়েবপেজে ছবি যোগ করা",
                estimatedMinutes = 10, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "<img> tag দিয়ে ওয়েবপেজে ছবি দেখানো হয়। এটি একটি void element — closing tag নেই।"),
                    ContentBlock(ContentType.DEFINITION, "src: ছবির address (file path বা URL)\nalt: ছবির text বিবরণ (ছবি load না হলে দেখায়)\nwidth/height: ছবির সাইজ\nloading: lazy লিখলে ছবি scroll করলে load হয়"),
                    ContentBlock(ContentType.WARNING, "alt attribute সবসময় দেওয়া উচিত। এটি accessibility-র জন্য গুরুত্বপূর্ণ এবং SEO-তে সাহায্য করে।"),
                    ContentBlock(ContentType.NOTE, "Web-এ ছবির format: JPG (photos), PNG (transparency), WebP (modern, small size), SVG (vector icons, scalable)"),
                    ContentBlock(ContentType.TIP, "loading='lazy' ব্যবহার করলে page load speed বাড়ে — ছবিগুলো শুধু দেখার সময় load হয়।")
                ),
                codeExample = """<!-- Basic image -->
<img src="photo.jpg" alt="আমার ছবি">

<!-- Online image -->
<img src="https://example.com/image.jpg" 
     alt="উদাহরণ ছবি"
     width="300"
     height="200">

<!-- Lazy loading (performance-friendly) -->
<img src="large-photo.jpg" 
     alt="বড় ছবি" 
     loading="lazy">

<!-- Responsive image -->
<img src="photo.jpg" 
     alt="responsive ছবি"
     style="max-width: 100%; height: auto;">

<!-- Figure ও Caption (সেরা practice) -->
<figure>
  <img src="dhaka.jpg" alt="ঢাকা শহরের দৃশ্য">
  <figcaption>ঢাকা — বাংলাদেশের রাজধানী</figcaption>
</figure>""",
                quiz = listOf(
                    QuizQuestion("<img> tag-এ ছবির address কোন attribute-এ দেওয়া হয়?",
                        listOf("href","link","src","img"), 2, "src attribute-এ ছবির path বা URL দেওয়া হয়।"),
                    QuizQuestion("alt attribute কেন গুরুত্বপূর্ণ?",
                        listOf("ছবি বড় করতে","Accessibility ও SEO-র জন্য","ছবির color পরিবর্তনে","Loading speed বাড়াতে"), 1,
                        "alt text accessibility ও SEO উভয়ের জন্য গুরুত্বপূর্ণ।"),
                    QuizQuestion("loading='lazy' কী করে?",
                        listOf("ছবি ছোট করে","Scroll করলে ছবি load করে","ছবি animate করে","ছবি blur করে"), 1,
                        "lazy loading page performance উন্নত করে।"),
                    QuizQuestion("Vector icon-এর জন্য কোন image format সেরা?",
                        listOf("JPG","PNG","WebP","SVG"), 3, "SVG vector format যা যেকোনো সাইজে sharp থাকে।"),
                    QuizQuestion("Image-এর caption দিতে কোন tag ব্যবহার করা হয়?",
                        listOf("<caption>","<figcaption>","<alt>","<desc>"), 1, "<figcaption> ছবির caption-এর জন্য ব্যবহৃত হয়।")
                )
            ),
            Lesson(
                id = "ch5_l2", chapterId = "ch5",
                title = "Audio ও Video", subtitle = "মিডিয়া যোগ করা",
                estimatedMinutes = 12, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML5-এ <audio> ও <video> tag দিয়ে সরাসরি ওয়েবপেজে audio ও video যোগ করা যায়। Flash player আর দরকার হয় না।"),
                    ContentBlock(ContentType.DEFINITION, "controls: play/pause/volume control দেখায়\nautoplay: page load হলেই শুরু হয়\nloop: শেষ হলে আবার শুরু হয়\nmuted: শব্দ বন্ধ\npreload: কতটুকু আগে load হবে"),
                    ContentBlock(ContentType.WARNING, "autoplay সাথে muted না দিলে অনেক browser block করে। User experience-এর কথা ভেবে autoplay এড়ানো ভালো।"),
                    ContentBlock(ContentType.NOTE, "Multiple <source> দিলে browser compatible format ব্যবহার করে। Video-র জন্য MP4 (H.264) সবচেয়ে বেশি supported।"),
                    ContentBlock(ContentType.TIP, "YouTube বা Vimeo video embed করতে <iframe> ব্যবহার করো — সরাসরি video file upload না করাই ভালো।")
                ),
                codeExample = """<!-- Audio player -->
<audio controls>
  <source src="song.mp3" type="audio/mpeg">
  <source src="song.ogg" type="audio/ogg">
  তোমার browser audio support করে না।
</audio>

<!-- Video player -->
<video controls width="640" height="360" poster="thumbnail.jpg">
  <source src="video.mp4" type="video/mp4">
  <source src="video.webm" type="video/webm">
  তোমার browser video support করে না।
</video>

<!-- Muted autoplay (background video) -->
<video autoplay muted loop>
  <source src="background.mp4" type="video/mp4">
</video>

<!-- YouTube embed -->
<iframe width="560" height="315"
  src="https://www.youtube.com/embed/VIDEO_ID"
  title="YouTube video"
  frameborder="0"
  allowfullscreen>
</iframe>""",
                quiz = listOf(
                    QuizQuestion("controls attribute কী করে?",
                        listOf("Video autoplay করে","Play/Pause control দেখায়","Video মিউট করে","Loop চালু করে"), 1,
                        "controls attribute media player controls দেখায়।"),
                    QuizQuestion("Web-এ সবচেয়ে বেশি supported video format কোনটি?",
                        listOf("AVI","MOV","MP4","WMV"), 2, "MP4 (H.264) সবচেয়ে বেশি browser support করে।"),
                    QuizQuestion("autoplay ব্যবহার করলে কী সমস্যা হতে পারে?",
                        listOf("Video load হবে না","Browser block করতে পারে","Controls কাজ করবে না","Loop বন্ধ হবে"), 1,
                        "অনেক browser muted ছাড়া autoplay block করে।"),
                    QuizQuestion("YouTube video embed করতে কোন tag ব্যবহার করা হয়?",
                        listOf("<video>","<embed>","<iframe>","<object>"), 2,
                        "<iframe> দিয়ে YouTube সহ অন্য site-এর content embed করা যায়।"),
                    QuizQuestion("poster attribute কী করে?",
                        listOf("Video title দেখায়","Video শুরুর আগে thumbnail দেখায়","Controls সাজায়","Caption দেখায়"), 1,
                        "poster attribute video play-এর আগে একটি thumbnail image দেখায়।")
                )
            )
        )
    )

    // ==================== CHAPTER 6 ====================
    private fun chapter6() = Chapter(
        id = "ch6", title = "Lists", subtitle = "তালিকা তৈরি করা",
        iconKey = "ic_list", color = 0xFF4DB6AC,
        lessons = listOf(
            Lesson(
                id = "ch6_l1", chapterId = "ch6",
                title = "Ordered ও Unordered Lists", subtitle = "বিভিন্ন ধরনের তালিকা",
                estimatedMinutes = 10, difficulty = Difficulty.BEGINNER,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML-এ ৩ ধরনের list আছে: Unordered List (bullet points), Ordered List (numbered), Definition List (term ও description)।"),
                    ContentBlock(ContentType.DEFINITION, "<ul> : Unordered List — bullet points\n<ol> : Ordered List — numbered\n<li> : List Item — প্রতিটি item\n<dl> : Definition List\n<dt> : Definition Term\n<dd> : Definition Description"),
                    ContentBlock(ContentType.NOTE, "<ol> এর type attribute দিয়ে numbering পরিবর্তন করা যায়: 1 (default), A, a, I, i"),
                    ContentBlock(ContentType.TIP, "List-এর ভেতরে আরেকটি list রাখা যায় — এটি nested list।")
                ),
                codeExample = """<!-- Unordered List -->
<ul>
  <li>HTML</li>
  <li>CSS</li>
  <li>JavaScript</li>
</ul>

<!-- Ordered List -->
<ol>
  <li>প্রথমে HTML শিখবো</li>
  <li>তারপর CSS শিখবো</li>
  <li>সবশেষে JavaScript শিখবো</li>
</ol>

<!-- Different numbering -->
<ol type="A">
  <li>প্রথম</li>
  <li>দ্বিতীয়</li>
</ol>

<!-- Nested List -->
<ul>
  <li>Frontend
    <ul>
      <li>HTML</li>
      <li>CSS</li>
    </ul>
  </li>
  <li>Backend
    <ul>
      <li>Python</li>
      <li>Node.js</li>
    </ul>
  </li>
</ul>

<!-- Definition List -->
<dl>
  <dt>HTML</dt>
  <dd>HyperText Markup Language — ওয়েব পেজের ভাষা</dd>
  <dt>CSS</dt>
  <dd>Cascading Style Sheets — ওয়েব পেজের সাজসজ্জা</dd>
</dl>""",
                quiz = listOf(
                    QuizQuestion("Bullet point list-এর জন্য কোন tag ব্যবহার করা হয়?",
                        listOf("<ol>","<ul>","<li>","<dl>"), 1, "<ul> (Unordered List) bullet points দেখায়।"),
                    QuizQuestion("<ol type='A'> কী দেখাবে?",
                        listOf("1, 2, 3","I, II, III","A, B, C","a, b, c"), 2,
                        "type='A' uppercase alphabets (A, B, C) দেখায়।"),
                    QuizQuestion("প্রতিটি list item কোন tag-এ লেখা হয়?",
                        listOf("<item>","<list>","<li>","<point>"), 2, "<li> (List Item) প্রতিটি item-এর জন্য।"),
                    QuizQuestion("Definition list-এ term কোন tag-এ লেখা হয়?",
                        listOf("<dl>","<dd>","<dt>","<df>"), 2, "<dt> (Definition Term) term লেখার জন্য।"),
                    QuizQuestion("Nested list মানে কী?",
                        listOf("List-এর item গুলো bold করা","List-এর ভেতরে আরেকটি list","List-কে style করা","Multiple list পাশাপাশি রাখা"), 1,
                        "Nested list মানে একটি list-এর ভেতরে আরেকটি list।")
                )
            )
        )
    )

    // ==================== CHAPTER 7 ====================
    private fun chapter7() = Chapter(
        id = "ch7", title = "Tables", subtitle = "তথ্য টেবিলে সাজানো",
        iconKey = "ic_table", color = 0xFFE57373,
        lessons = listOf(
            Lesson(
                id = "ch7_l1", chapterId = "ch7",
                title = "Basic Table Structure", subtitle = "Table তৈরির মূল ধারণা",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML table দিয়ে data সারি ও কলামে সাজানো যায়। Table-এর কাঠামো বোঝা খুব জরুরি।"),
                    ContentBlock(ContentType.DEFINITION, "<table> : পুরো table\n<thead> : Table header section\n<tbody> : Table body section\n<tfoot> : Table footer section\n<tr> : Table row (সারি)\n<th> : Table header cell (header column)\n<td> : Table data cell (সাধারণ cell)"),
                    ContentBlock(ContentType.NOTE, "<th> default-এ bold ও center হয়। <td> normal text।"),
                    ContentBlock(ContentType.TIP, "colspan দিয়ে multiple column merge করা যায়, rowspan দিয়ে multiple row merge করা যায়।")
                ),
                codeExample = """<table border="1">
  <thead>
    <tr>
      <th>নাম</th>
      <th>বয়স</th>
      <th>শহর</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Shohan</td>
      <td>20</td>
      <td>Muktagacha</td>
    </tr>
    <tr>
      <td>Rafi</td>
      <td>21</td>
      <td>Dhaka</td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <td colspan="3">মোট: 2 জন</td>
    </tr>
  </tfoot>
</table>

<!-- colspan ও rowspan উদাহরণ -->
<table border="1">
  <tr>
    <th colspan="2">বিষয় ও নম্বর</th>
  </tr>
  <tr>
    <td>HTML</td>
    <td rowspan="2">দুটি বিষয়</td>
  </tr>
  <tr>
    <td>CSS</td>
  </tr>
</table>""",
                quiz = listOf(
                    QuizQuestion("Table-এর একটি সারি (row) কোন tag দিয়ে তৈরি হয়?",
                        listOf("<td>","<th>","<tr>","<row>"), 2, "<tr> (Table Row) একটি সারি তৈরি করে।"),
                    QuizQuestion("<th> ও <td> এর মধ্যে পার্থক্য কী?",
                        listOf("<th> ছোট, <td> বড়","<th> header cell (bold), <td> data cell","<th> row, <td> column","কোনো পার্থক্য নেই"), 1,
                        "<th> header cell — default bold ও center। <td> সাধারণ data cell।"),
                    QuizQuestion("Multiple column merge করতে কোন attribute ব্যবহার করা হয়?",
                        listOf("merge","rowspan","colspan","span"), 2,
                        "colspan দিয়ে multiple column merge করা যায়।"),
                    QuizQuestion("Table body section কোন tag-এ থাকে?",
                        listOf("<thead>","<tfoot>","<tbody>","<table>"), 2,
                        "<tbody> table-এর মূল data section।"),
                    QuizQuestion("rowspan কী করে?",
                        listOf("Column merge করে","Row merge করে","Table বড় করে","Border যোগ করে"), 1,
                        "rowspan দিয়ে multiple row merge করা যায়।")
                )
            )
        )
    )

    // ==================== CHAPTER 8 ====================
    private fun chapter8() = Chapter(
        id = "ch8", title = "Forms ও Input", subtitle = "User থেকে তথ্য নেওয়া",
        iconKey = "ic_form", color = 0xFF9575CD,
        lessons = listOf(
            Lesson(
                id = "ch8_l1", chapterId = "ch8",
                title = "Form Basics", subtitle = "Form তৈরির মূল ধারণা",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML Form দিয়ে user-এর কাছ থেকে তথ্য নেওয়া হয়। Login form, registration form, contact form — সব <form> tag দিয়ে তৈরি।"),
                    ContentBlock(ContentType.DEFINITION, "action: form submit হলে কোথায় পাঠাবে\nmethod: GET বা POST\nGET: URL-এ data দেখা যায়\nPOST: URL-এ data দেখা যায় না (secure)"),
                    ContentBlock(ContentType.THEORY, "<label> tag input-এর সাথে connect করে। for attribute-এ input-এর id দিতে হয়। এটি accessibility-র জন্য জরুরি।"),
                    ContentBlock(ContentType.WARNING, "Password বা sensitive data সবসময় method='POST' ব্যবহার করো।"),
                    ContentBlock(ContentType.NOTE, "required attribute দিলে field empty রেখে submit করা যায় না।")
                ),
                codeExample = """<form action="/submit" method="POST">
  
  <!-- Text input -->
  <label for="name">নাম:</label>
  <input type="text" id="name" name="name" 
         placeholder="তোমার নাম লেখো" required>
  
  <!-- Email input -->
  <label for="email">ইমেইল:</label>
  <input type="email" id="email" name="email" 
         placeholder="email@example.com" required>
  
  <!-- Password input -->
  <label for="pass">পাসওয়ার্ড:</label>
  <input type="password" id="pass" name="password" 
         minlength="8" required>
  
  <!-- Number input -->
  <label for="age">বয়স:</label>
  <input type="number" id="age" name="age" 
         min="1" max="100">
  
  <!-- Submit button -->
  <button type="submit">জমা দাও</button>
  <button type="reset">মুছে ফেলো</button>
  
</form>""",
                quiz = listOf(
                    QuizQuestion("Sensitive data পাঠাতে কোন method ব্যবহার করা উচিত?",
                        listOf("GET","POST","PUT","DELETE"), 1,
                        "POST method URL-এ data দেখায় না, তাই secure।"),
                    QuizQuestion("<label for='name'> এর for attribute কী করে?",
                        listOf("Label style করে","Label ও input connect করে","Required করে","Placeholder দেয়"), 1,
                        "for attribute label কে তার input-এর সাথে connect করে।"),
                    QuizQuestion("required attribute কী করে?",
                        listOf("Field disable করে","Field empty রেখে submit করা যায় না","Field readonly করে","Field highlight করে"), 1,
                        "required দিলে সেই field ফাঁকা রেখে form submit হয় না।"),
                    QuizQuestion("Password field-এর জন্য কোন input type ব্যবহার করা হয়?",
                        listOf("type='secret'","type='hide'","type='password'","type='secure'"), 2,
                        "type='password' input text hide করে।"),
                    QuizQuestion("Form-এর action attribute কী করে?",
                        listOf("Form style করে","Form submit হলে কোথায় পাঠাবে নির্ধারণ করে","Required field চিহ্নিত করে","Method নির্ধারণ করে"), 1,
                        "action attribute form data কোথায় submit হবে তা নির্ধারণ করে।")
                )
            ),
            Lesson(
                id = "ch8_l2", chapterId = "ch8",
                title = "সব ধরনের Input Types", subtitle = "বিভিন্ন input ব্যবহার করা",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML5-এ অনেক নতুন input type এসেছে যা mobile-এ keyboard বদলে দেয় এবং built-in validation দেয়।"),
                    ContentBlock(ContentType.DEFINITION, "text, email, password, number, tel, url\ndate, time, datetime-local, month, week\nrange (slider), color (color picker)\ncheckbox, radio, file\nsearch, hidden"),
                    ContentBlock(ContentType.NOTE, "date input mobile-এ date picker দেখায়। color input color chooser দেখায়। এগুলো UX অনেক ভালো করে।"),
                    ContentBlock(ContentType.TIP, "select tag দিয়ে dropdown, textarea দিয়ে multi-line text area তৈরি হয়।")
                ),
                codeExample = """<!-- বিভিন্ন Input Types -->
<form>
  <!-- Date & Time -->
  <input type="date" name="birthday">
  <input type="time" name="meeting_time">
  <input type="datetime-local" name="event">
  
  <!-- Range (Slider) -->
  <label>অগ্রগতি: <span id="val">50</span>%</label>
  <input type="range" min="0" max="100" value="50"
         oninput="document.getElementById('val').textContent=this.value">
  
  <!-- Color Picker -->
  <input type="color" name="favorite_color" value="#58A6FF">
  
  <!-- File Upload -->
  <input type="file" accept=".jpg,.png,.pdf">
  
  <!-- Checkbox -->
  <input type="checkbox" id="agree" name="agree">
  <label for="agree">শর্তে রাজি</label>
  
  <!-- Radio Button -->
  <input type="radio" id="male" name="gender" value="male">
  <label for="male">ছেলে</label>
  <input type="radio" id="female" name="gender" value="female">
  <label for="female">মেয়ে</label>
  
  <!-- Dropdown Select -->
  <select name="district">
    <option value="">জেলা বেছে নাও</option>
    <option value="dhaka">ঢাকা</option>
    <option value="mymensingh">ময়মনসিংহ</option>
    <option value="chittagong">চট্টগ্রাম</option>
  </select>
  
  <!-- Textarea -->
  <textarea name="message" rows="4" cols="50"
            placeholder="তোমার বার্তা লেখো..."></textarea>
</form>""",
                quiz = listOf(
                    QuizQuestion("Date picker দেখানোর জন্য কোন input type ব্যবহার করা হয়?",
                        listOf("type='calendar'","type='date'","type='time'","type='datetime'"), 1,
                        "type='date' browser-এর built-in date picker দেখায়।"),
                    QuizQuestion("Slider তৈরি করতে কোন input type ব্যবহার করা হয়?",
                        listOf("type='slider'","type='scale'","type='range'","type='number'"), 2,
                        "type='range' slider input তৈরি করে।"),
                    QuizQuestion("Multi-line text input-এর জন্য কোনটি ব্যবহার করা হয়?",
                        listOf("<input type='text'>","<multiline>","<textarea>","<input type='multiline'>"), 2,
                        "<textarea> multi-line text input তৈরি করে।"),
                    QuizQuestion("Dropdown menu তৈরি করতে কোন tag ব্যবহার করা হয়?",
                        listOf("<dropdown>","<select>","<options>","<choose>"), 1,
                        "<select> tag dropdown menu তৈরি করে, <option> দিয়ে items যোগ করা হয়।"),
                    QuizQuestion("একটির মধ্যে একটি বেছে নিতে (single select) কোন input type?",
                        listOf("checkbox","select","radio","toggle"), 2,
                        "Radio button দিয়ে একটি group থেকে একটি option বেছে নেওয়া যায়।")
                )
            )
        )
    )

    // ==================== CHAPTER 9 ====================
    private fun chapter9() = Chapter(
        id = "ch9", title = "Semantic HTML5", subtitle = "অর্থবহ HTML লেখা",
        iconKey = "ic_semantic", color = 0xFF4CAF50,
        lessons = listOf(
            Lesson(
                id = "ch9_l1", chapterId = "ch9",
                title = "HTML5 Semantic Elements", subtitle = "অর্থবহ structure তৈরি",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "Semantic HTML মানে এমন tag ব্যবহার করা যা content-এর অর্থ বহন করে। HTML5-এ অনেক semantic tag এসেছে।"),
                    ContentBlock(ContentType.DEFINITION, "<header>: পেজ বা section-এর header\n<nav>: Navigation links\n<main>: Page-এর মূল content (একটিই থাকবে)\n<article>: Self-contained content (blog post, news)\n<section>: Thematic grouping\n<aside>: Sidebar, extra info\n<footer>: পেজ বা section-এর footer\n<figure>: Self-contained media\n<figcaption>: Media-র caption\n<time>: Date/time\n<address>: Contact information\n<details>: Expandable content\n<summary>: <details>-এর visible title"),
                    ContentBlock(ContentType.NOTE, "Semantic tags screen reader-দের (visually impaired users) page navigate করতে সাহায্য করে।"),
                    ContentBlock(ContentType.TIP, "<article> ও <section> এর পার্থক্য: <article> standalone content (blog post), <section> related content-এর group।")
                ),
                codeExample = """<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>Semantic HTML উদাহরণ</title>
</head>
<body>

  <header>
    <h1>HTML শেখার ব্লগ</h1>
    <nav>
      <a href="/">হোম</a>
      <a href="/lessons">পাঠ</a>
      <a href="/about">পরিচিতি</a>
    </nav>
  </header>

  <main>
    <article>
      <header>
        <h2>HTML5 এর নতুন Features</h2>
        <time datetime="2024-01-15">জানুয়ারি ১৫, ২০২৪</time>
      </header>
      
      <section>
        <h3>Semantic Tags</h3>
        <p>HTML5 অনেক নতুন semantic tag এনেছে...</p>
      </section>
      
      <section>
        <h3>Multimedia Support</h3>
        <figure>
          <img src="html5.png" alt="HTML5 Logo">
          <figcaption>HTML5-এর লোগো</figcaption>
        </figure>
      </section>
      
      <footer>
        <address>
          লিখেছেন: <a href="mailto:shohan@email.com">Shohan</a>
        </address>
      </footer>
    </article>
    
    <aside>
      <h3>সম্পর্কিত পাঠ</h3>
      <ul>
        <li><a href="/css">CSS শেখো</a></li>
        <li><a href="/js">JavaScript শেখো</a></li>
      </ul>
    </aside>
  </main>

  <footer>
    <p>&copy; 2024 HTML শেখার ব্লগ</p>
  </footer>

  <!-- Details & Summary -->
  <details>
    <summary>আরো তথ্য দেখো</summary>
    <p>এই লুকানো content click করলে দেখা যাবে।</p>
  </details>

</body>
</html>""",
                quiz = listOf(
                    QuizQuestion("একটি পেজে কতটি <main> tag থাকতে পারে?",
                        listOf("যতটা দরকার","দুটি","মাত্র একটি","তিনটি"), 2,
                        "<main> একটি পেজে একটিই থাকে — মূল content-এর জন্য।"),
                    QuizQuestion("<article> ও <section> এর পার্থক্য কী?",
                        listOf("কোনো পার্থক্য নেই","article standalone, section grouped content","article ছোট, section বড়","article header-এ, section body-তে"), 1,
                        "<article> standalone content, <section> related content-এর group।"),
                    QuizQuestion("Sidebar content কোন tag-এ রাখা হয়?",
                        listOf("<sidebar>","<extra>","<aside>","<secondary>"), 2,
                        "<aside> sidebar বা extra information-এর জন্য।"),
                    QuizQuestion("<details> ও <summary> tag কী করে?",
                        listOf("Table তৈরি করে","Expandable/collapsible content তৈরি করে","List তৈরি করে","Form তৈরি করে"), 1,
                        "<details>+<summary> click করলে content দেখায়/লুকায়।"),
                    QuizQuestion("Date ও time markup করতে কোন semantic tag ব্যবহার করা হয়?",
                        listOf("<date>","<clock>","<time>","<datetime>"), 2,
                        "<time> tag date ও time semantic markup-এর জন্য।")
                )
            )
        )
    )

    // ==================== CHAPTER 10 ====================
    private fun chapter10() = Chapter(
        id = "ch10", title = "HTML5 Advanced", subtitle = "Canvas, SVG ও আরো",
        iconKey = "ic_advanced", color = 0xFFF06292,
        lessons = listOf(
            Lesson(
                id = "ch10_l1", chapterId = "ch10",
                title = "Canvas Element", subtitle = "JavaScript দিয়ে drawing",
                estimatedMinutes = 20, difficulty = Difficulty.ADVANCED,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "<canvas> element JavaScript দিয়ে graphics, animations, games তৈরি করতে ব্যবহার হয়। এটি HTML5-এর সবচেয়ে powerful feature গুলোর একটি।"),
                    ContentBlock(ContentType.THEORY, "Canvas-এ JavaScript দিয়ে 2D context নেওয়া হয় এবং shapes, lines, text, images আঁকা হয়।"),
                    ContentBlock(ContentType.NOTE, "Canvas pixel-based — resize করলে blurry হয়। SVG vector-based — যেকোনো size-এ sharp।"),
                    ContentBlock(ContentType.TIP, "Games, charts, image filters — সব canvas দিয়ে বানানো সম্ভব।")
                ),
                codeExample = """<!-- Canvas element -->
<canvas id="myCanvas" width="400" height="300"
        style="border: 1px solid #333; background: #1a1a1a">
</canvas>

<script>
  const canvas = document.getElementById('myCanvas');
  const ctx = canvas.getContext('2d');
  
  // Rectangle আঁকা
  ctx.fillStyle = '#58A6FF';
  ctx.fillRect(50, 50, 150, 100);
  
  // Circle আঁকা
  ctx.beginPath();
  ctx.arc(300, 150, 60, 0, Math.PI * 2);
  ctx.fillStyle = '#3FB950';
  ctx.fill();
  
  // Line আঁকা
  ctx.beginPath();
  ctx.moveTo(0, 250);
  ctx.lineTo(400, 250);
  ctx.strokeStyle = '#F78166';
  ctx.lineWidth = 3;
  ctx.stroke();
  
  // Text লেখা
  ctx.font = '24px Arial';
  ctx.fillStyle = '#ffffff';
  ctx.fillText('HTML Canvas!', 120, 290);
</script>""",
                quiz = listOf(
                    QuizQuestion("<canvas> element কী দিয়ে control করা হয়?",
                        listOf("CSS","HTML","JavaScript","Python"), 2,
                        "Canvas JavaScript দিয়ে control করা হয়।"),
                    QuizQuestion("Canvas 2D context কীভাবে নেওয়া হয়?",
                        listOf("canvas.get2D()","canvas.getContext('2d')","canvas.draw2D()","canvas.context2d()"), 1,
                        "getContext('2d') দিয়ে 2D rendering context নেওয়া হয়।"),
                    QuizQuestion("Canvas ও SVG-এর মূল পার্থক্য কী?",
                        listOf("Canvas বড়, SVG ছোট","Canvas pixel-based, SVG vector-based","Canvas ভালো, SVG খারাপ","Canvas HTML5, SVG HTML4"), 1,
                        "Canvas pixel-based (scale করলে blurry), SVG vector-based (সবসময় sharp)।"),
                    QuizQuestion("Circle আঁকতে কোন method ব্যবহার করা হয়?",
                        listOf("ctx.circle()","ctx.drawCircle()","ctx.arc()","ctx.round()"), 2,
                        "ctx.arc() দিয়ে circle বা arc আঁকা হয়।"),
                    QuizQuestion("Canvas-এ text লিখতে কোন property set করতে হয়?",
                        listOf("ctx.text","ctx.font","ctx.textStyle","ctx.write"), 1,
                        "ctx.font দিয়ে font size ও family set করে ctx.fillText() দিয়ে text লেখা হয়।")
                )
            ),
            Lesson(
                id = "ch10_l2", chapterId = "ch10",
                title = "SVG — Scalable Vector Graphics", subtitle = "Vector graphics HTML-এ",
                estimatedMinutes = 15, difficulty = Difficulty.ADVANCED,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "SVG হলো XML-based vector image format। এটি সরাসরি HTML-এ লেখা যায় এবং যেকোনো resolution-এ sharp থাকে।"),
                    ContentBlock(ContentType.NOTE, "Icons, logos, illustrations সব SVG দিয়ে তৈরি করা যায়। CSS ও JavaScript দিয়ে animate করাও সম্ভব।"),
                    ContentBlock(ContentType.TIP, "SVG file size অনেক ছোট এবং HTTP request লাগে না (inline SVG হলে)।")
                ),
                codeExample = """<!-- Inline SVG -->
<svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
  
  <!-- Rectangle -->
  <rect x="10" y="10" width="80" height="60" 
        fill="#58A6FF" rx="10" ry="10"/>
  
  <!-- Circle -->
  <circle cx="150" cy="50" r="40" 
          fill="#3FB950" stroke="#fff" stroke-width="3"/>
  
  <!-- Triangle (polygon) -->
  <polygon points="100,150 50,200 150,200" 
           fill="#F78166"/>
  
  <!-- Text -->
  <text x="20" y="190" font-size="16" fill="#E6EDF3">
    SVG Graphics
  </text>
  
  <!-- Path (complex shapes) -->
  <path d="M 10 100 Q 100 50 190 100" 
        stroke="#BC8CFF" stroke-width="3" fill="none"/>
        
</svg>

<!-- External SVG as img -->
<img src="icon.svg" alt="SVG Icon" width="50" height="50">""",
                quiz = listOf(
                    QuizQuestion("SVG-এর পূর্ণ নাম কী?",
                        listOf("Scalable Vector Graphics","Simple Vector Graphic","Standard Visual Graphics","System Vector GUI"), 0,
                        "SVG = Scalable Vector Graphics"),
                    QuizQuestion("SVG কোন format-এ লেখা হয়?",
                        listOf("JSON","HTML","XML","CSS"), 2, "SVG XML-based format।"),
                    QuizQuestion("SVG কেন icons-এর জন্য ভালো?",
                        listOf("File size বড়","যেকোনো resolution-এ sharp ও scalable","শুধু modern browser-এ কাজ করে","Pixel-based তাই sharp"), 1,
                        "SVG vector-based তাই যেকোনো size-এ sharp থাকে।"),
                    QuizQuestion("SVG-এ circle কোন element দিয়ে আঁকা হয়?",
                        listOf("<round>","<oval>","<circle>","<arc>"), 2, "<circle> element দিয়ে SVG-এ circle আঁকা হয়।"),
                    QuizQuestion("SVG-এ জটিল shape আঁকতে কোনটি ব্যবহার করা হয়?",
                        listOf("<shape>","<path>","<complex>","<curve>"), 1, "<path> element দিয়ে যেকোনো complex shape আঁকা যায়।")
                )
            )
        )
    )

    // ==================== CHAPTER 11 ====================
    private fun chapter11() = Chapter(
        id = "ch11", title = "Meta Tags ও SEO", subtitle = "Search Engine-এর জন্য HTML",
        iconKey = "ic_seo", color = 0xFF26C6DA,
        lessons = listOf(
            Lesson(
                id = "ch11_l1", chapterId = "ch11",
                title = "Meta Tags সম্পূর্ণ গাইড", subtitle = "SEO ও Social Sharing",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "Meta tags <head> section-এ থাকে এবং browser, search engine ও social media platform-এর জন্য page সম্পর্কে তথ্য দেয়।"),
                    ContentBlock(ContentType.DEFINITION, "charset: Character encoding\nviewport: Mobile responsiveness\ndescription: Search result-এ দেখানো বিবরণ\nkeywords: (কম গুরুত্বপূর্ণ এখন)\nauthor: লেখকের নাম\nrobots: Search engine কে নির্দেশ"),
                    ContentBlock(ContentType.NOTE, "Open Graph meta tags (og:) Facebook, LinkedIn-এ share করলে সুন্দর preview দেখায়।"),
                    ContentBlock(ContentType.TIP, "Twitter Cards (twitter:) Twitter-এ share করলে সুন্দর preview দেখায়।")
                ),
                codeExample = """<head>
  <!-- Essential -->
  <meta charset="UTF-8">
  <meta name="viewport" 
        content="width=device-width, initial-scale=1.0">
  
  <!-- SEO -->
  <meta name="description" 
        content="HTML শেখার সেরা বাংলা গাইড — beginner থেকে advanced">
  <meta name="keywords" content="HTML, ওয়েব ডিজাইন, বাংলা">
  <meta name="author" content="Shohan">
  <meta name="robots" content="index, follow">
  
  <title>HTML শেখো বাংলায় | HTMLLearn</title>
  
  <!-- Canonical URL -->
  <link rel="canonical" href="https://htmllearn.com/lessons">
  
  <!-- Open Graph (Facebook, LinkedIn) -->
  <meta property="og:title" content="HTML শেখো বাংলায়">
  <meta property="og:description" 
        content="বাংলায় HTML শেখার সেরা app">
  <meta property="og:image" content="https://htmllearn.com/og.jpg">
  <meta property="og:url" content="https://htmllearn.com">
  <meta property="og:type" content="website">
  
  <!-- Twitter Card -->
  <meta name="twitter:card" content="summary_large_image">
  <meta name="twitter:title" content="HTML শেখো বাংলায়">
  <meta name="twitter:description" 
        content="বাংলায় HTML শেখার সেরা app">
  
  <!-- Favicon -->
  <link rel="icon" type="image/png" href="/favicon.png">
</head>""",
                quiz = listOf(
                    QuizQuestion("viewport meta tag কী করে?",
                        listOf("Page speed বাড়ায়","Mobile-এ সঠিক rendering নিশ্চিত করে","Font size বাড়ায়","Background color set করে"), 1,
                        "viewport meta tag mobile responsive design-এর জন্য জরুরি।"),
                    QuizQuestion("Open Graph meta tags কোথায় কাজ করে?",
                        listOf("Search engine-এ","Facebook, LinkedIn-এ share preview-এ","Browser tab-এ","Website speed-এ"), 1,
                        "og: tags social media share preview-এর জন্য।"),
                    QuizQuestion("meta name='robots' content='index, follow' কী করে?",
                        listOf("Page hide করে","Search engine কে page index ও links follow করতে বলে","Page encrypt করে","Cache clear করে"), 1,
                        "robots meta tag search engine crawler-এর behavior নির্ধারণ করে।"),
                    QuizQuestion("Canonical URL কী কাজে লাগে?",
                        listOf("Page redirect করে","Same content-এর primary URL search engine-কে জানায়","SSL enable করে","CDN configure করে"), 1,
                        "Canonical tag duplicate content-এর সমস্যা সমাধান করে।"),
                    QuizQuestion("Favicon কোন tag দিয়ে যোগ করা হয়?",
                        listOf("<meta>","<icon>","<link rel='icon'>","<img>"), 2,
                        "<link rel='icon'> দিয়ে favicon যোগ করা হয়।")
                )
            )
        )
    )

    // ==================== CHAPTER 12 ====================
    private fun chapter12() = Chapter(
        id = "ch12", title = "HTML ও CSS সংযোগ", subtitle = "Style যোগ করা",
        iconKey = "ic_css", color = 0xFF7986CB,
        lessons = listOf(
            Lesson(
                id = "ch12_l1", chapterId = "ch12",
                title = "CSS যোগ করার তিনটি উপায়", subtitle = "HTML-এ style দেওয়া",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "HTML-এ CSS যোগ করার ৩টি উপায় আছে: Inline, Internal, External। প্রতিটির ব্যবহার ক্ষেত্র আলাদা।"),
                    ContentBlock(ContentType.DEFINITION, "Inline: সরাসরি element-এ style attribute\nInternal: <head>-এ <style> tag-এ\nExternal: আলাদা .css file — সবচেয়ে ভালো"),
                    ContentBlock(ContentType.NOTE, "External CSS সবচেয়ে ভালো practice: code reuse হয়, maintenance সহজ, cache হয়।"),
                    ContentBlock(ContentType.WARNING, "Inline CSS শুধু quick fix বা testing-এর জন্য। Production-এ external CSS ব্যবহার করো।")
                ),
                codeExample = """<!-- 1. Inline CSS — শুধু এই element-এ -->
<p style="color: #58A6FF; font-size: 18px; font-weight: bold;">
  নীল রঙের paragraph
</p>

<!-- 2. Internal CSS — পুরো page-এ -->
<head>
  <style>
    body {
      background-color: #0D1117;
      color: #E6EDF3;
      font-family: 'Arial', sans-serif;
    }
    h1 {
      color: #58A6FF;
      text-align: center;
    }
    .card {
      background: #161B22;
      padding: 20px;
      border-radius: 10px;
    }
  </style>
</head>

<!-- 3. External CSS — সেরা উপায় -->
<head>
  <link rel="stylesheet" href="styles.css">
</head>

<!-- styles.css ফাইলে: -->
/*
body {
  margin: 0;
  padding: 0;
  background: #0D1117;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
*/""",
                quiz = listOf(
                    QuizQuestion("CSS যোগ করার কোন উপায়টি সবচেয়ে ভালো practice?",
                        listOf("Inline CSS","Internal CSS","External CSS","সব সমান"), 2,
                        "External CSS reusable, maintainable ও cacheable।"),
                    QuizQuestion("External CSS link করতে কোন tag ব্যবহার করা হয়?",
                        listOf("<css>","<style>","<link>","<import>"), 2,
                        "<link rel='stylesheet' href='styles.css'> দিয়ে external CSS যোগ করা হয়।"),
                    QuizQuestion("Inline CSS কোথায় লেখা হয়?",
                        listOf("<head>-এ","<style> tag-এ","element-এর style attribute-এ","আলাদা .css file-এ"), 2,
                        "Inline CSS element-এর style attribute-এ লেখা হয়।"),
                    QuizQuestion("Internal CSS কোথায় থাকে?",
                        listOf("<body>-এর ভেতরে","<head>-এর ভেতরে <style> tag-এ","আলাদা file-এ","Element-এর attribute-এ"), 1,
                        "Internal CSS <head>-এর ভেতরে <style> tag-এ থাকে।"),
                    QuizQuestion("CSS ফাইলের extension কী?",
                        listOf(".html",".css",".style",".design"), 1, "CSS ফাইলের extension .css")
                )
            )
        )
    )

    // ==================== CHAPTER 13 ====================
    private fun chapter13() = Chapter(
        id = "ch13", title = "HTML ও JavaScript সংযোগ", subtitle = "Dynamic ওয়েবপেজ তৈরি",
        iconKey = "ic_js", color = 0xFFFFD54F,
        lessons = listOf(
            Lesson(
                id = "ch13_l1", chapterId = "ch13",
                title = "JavaScript যোগ করা ও DOM", subtitle = "Interactive page তৈরি",
                estimatedMinutes = 20, difficulty = Difficulty.ADVANCED,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "JavaScript HTML-এর সাথে কাজ করে page-কে interactive করে। HTML structure, CSS style, JS behavior — এই তিনটি মিলে পূর্ণ ওয়েবপেজ।"),
                    ContentBlock(ContentType.DEFINITION, "DOM (Document Object Model): Browser HTML parse করে যে object tree তৈরি করে। JavaScript এই tree modify করতে পারে।"),
                    ContentBlock(ContentType.NOTE, "<script> tag body-র শেষে রাখা ভালো — এতে page আগে load হয়।"),
                    ContentBlock(ContentType.TIP, "defer attribute দিলে <script> tag head-এ রাখা যায় কিন্তু HTML parse-এর পরে execute হয়।")
                ),
                codeExample = """<!DOCTYPE html>
<html lang="bn">
<head>
  <title>Interactive Page</title>
</head>
<body>

  <h1 id="title">HTML + JavaScript</h1>
  <p id="message">এখানে message আসবে</p>
  
  <input type="text" id="nameInput" placeholder="নাম লেখো">
  <button onclick="greet()">স্বাগত জানাও</button>
  
  <div id="counter">0</div>
  <button onclick="increment()">+1</button>
  <button onclick="decrement()">-1</button>

  <script>
    let count = 0;
    
    // Function — button click-এ call হবে
    function greet() {
      const name = document.getElementById('nameInput').value;
      if(name) {
        document.getElementById('message').textContent = 
          'স্বাগত, ' + name + '!';
        document.getElementById('message').style.color = '#58A6FF';
      }
    }
    
    function increment() {
      count++;
      document.getElementById('counter').textContent = count;
    }
    
    function decrement() {
      count--;
      document.getElementById('counter').textContent = count;
    }
    
    // Event listener (onclick-এর বিকল্প)
    document.getElementById('title').addEventListener('click', function() {
      this.style.color = '#3FB950';
    });
  </script>

</body>
</html>""",
                quiz = listOf(
                    QuizQuestion("<script> tag কোথায় রাখা ভালো?",
                        listOf("<head>-এর শুরুতে","<body>-এর শেষে","<html>-এর বাইরে","যেকোনো জায়গায়"), 1,
                        "body-র শেষে রাখলে HTML আগে load হয়।"),
                    QuizQuestion("DOM কী?",
                        listOf("Programming language","Browser-এর HTML-এর object tree","CSS framework","Database"), 1,
                        "DOM হলো browser-এর HTML parse করা object tree।"),
                    QuizQuestion("getElementById() কী করে?",
                        listOf("নতুন element তৈরি করে","id দিয়ে element খুঁজে আনে","element delete করে","element style করে"), 1,
                        "getElementById() দিয়ে নির্দিষ্ট id-এর element পাওয়া যায়।"),
                    QuizQuestion("element.textContent কী করে?",
                        listOf("Element-এর CSS পরিবর্তন করে","Element-এর text content পরিবর্তন করে","Element delete করে","Event যোগ করে"), 1,
                        "textContent element-এর text content পড়া বা বদলাতে ব্যবহার হয়।"),
                    QuizQuestion("defer attribute কী করে?",
                        listOf("Script execute বন্ধ করে","HTML parse-এর পরে script execute হয়","Script cache করে","Script compress করে"), 1,
                        "defer দিলে HTML parse শেষ হলে script execute হয়।")
                )
            )
        )
    )

    // ==================== CHAPTER 14 ====================
    private fun chapter14() = Chapter(
        id = "ch14", title = "Responsive Design", subtitle = "সব device-এ সুন্দর দেখানো",
        iconKey = "ic_responsive", color = 0xFF26A69A,
        lessons = listOf(
            Lesson(
                id = "ch14_l1", chapterId = "ch14",
                title = "Viewport ও Mobile-First", subtitle = "Mobile-friendly design",
                estimatedMinutes = 15, difficulty = Difficulty.INTERMEDIATE,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "Responsive design মানে website সব screen size-এ সুন্দর দেখাবে — mobile, tablet, desktop।"),
                    ContentBlock(ContentType.DEFINITION, "viewport: device-এর দৃশ্যমান area\nwidth=device-width: screen width অনুযায়ী adjust\ninitial-scale=1.0: initial zoom level 100%"),
                    ContentBlock(ContentType.NOTE, "Mobile-first approach: আগে mobile design করো, তারপর বড় screen-এর জন্য extend করো।"),
                    ContentBlock(ContentType.TIP, "CSS Grid ও Flexbox responsive layout-এর জন্য সবচেয়ে ভালো।")
                ),
                codeExample = """<!-- Viewport meta (অবশ্যই থাকতে হবে) -->
<meta name="viewport" 
      content="width=device-width, initial-scale=1.0">

<!-- Responsive image -->
<img src="photo.jpg" alt="ছবি" 
     style="max-width: 100%; height: auto;">

<!-- Responsive CSS দিয়ে layout -->
<style>
  /* Mobile first (ছোট screen) */
  .container {
    padding: 10px;
    width: 100%;
  }
  
  .card-grid {
    display: grid;
    grid-template-columns: 1fr;  /* একটি কলাম */
    gap: 16px;
  }
  
  /* Tablet (768px+) */
  @media (min-width: 768px) {
    .container { padding: 20px; }
    .card-grid {
      grid-template-columns: repeat(2, 1fr);  /* দুটি কলাম */
    }
  }
  
  /* Desktop (1024px+) */
  @media (min-width: 1024px) {
    .container { max-width: 1200px; margin: 0 auto; }
    .card-grid {
      grid-template-columns: repeat(3, 1fr);  /* তিনটি কলাম */
    }
  }
</style>""",
                quiz = listOf(
                    QuizQuestion("Responsive design-এর জন্য কোন meta tag জরুরি?",
                        listOf("meta charset","meta viewport","meta description","meta robots"), 1,
                        "viewport meta tag mobile responsive design-এর জন্য অপরিহার্য।"),
                    QuizQuestion("Mobile-first মানে কী?",
                        listOf("শুধু mobile-এর জন্য design","আগে mobile design, পরে বড় screen","Mobile app তৈরি","Mobile browser ব্যবহার"), 1,
                        "Mobile-first: ছোট screen দিয়ে শুরু করে বড় screen-এর জন্য extend করা।"),
                    QuizQuestion("Media query-তে min-width: 768px মানে কী?",
                        listOf("768px এর কম screen-এ","768px এর সমান বা বেশি screen-এ","শুধু 768px screen-এ","সব screen-এ"), 1,
                        "min-width: 768px মানে 768px বা তার চেয়ে বড় screen-এ।"),
                    QuizQuestion("Responsive image-এর জন্য কোন CSS property ব্যবহার করা হয়?",
                        listOf("width: 100%","max-width: 100%; height: auto","min-width: 100%","responsive: true"), 1,
                        "max-width: 100%; height: auto image কে container-এর বাইরে যেতে দেয় না।"),
                    QuizQuestion("CSS Grid-এ mobile-এ একটি কলাম দিতে কী লিখতে হবে?",
                        listOf("grid-template-columns: 1","grid-template-columns: 100%","grid-template-columns: 1fr","grid-columns: 1"), 2,
                        "grid-template-columns: 1fr একটি কলাম তৈরি করে।")
                )
            )
        )
    )

    // ==================== CHAPTER 15 ====================
    private fun chapter15() = Chapter(
        id = "ch15", title = "HTML Best Practices", subtitle = "পেশাদার HTML লেখা",
        iconKey = "ic_best", color = 0xFFAED581,
        lessons = listOf(
            Lesson(
                id = "ch15_l1", chapterId = "ch15",
                title = "Accessibility ও ARIA", subtitle = "সবার জন্য ওয়েব",
                estimatedMinutes = 15, difficulty = Difficulty.ADVANCED,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "Web accessibility মানে website সব মানুষের জন্য ব্যবহারযোগ্য করা — দৃষ্টি, শ্রবণ বা গতি প্রতিবন্ধী মানুষদের জন্যও।"),
                    ContentBlock(ContentType.DEFINITION, "ARIA (Accessible Rich Internet Applications): screen reader-দের জন্য extra information\nrole: element-এর role বোঝায়\naria-label: element-এর description\naria-hidden: screen reader থেকে লুকায়"),
                    ContentBlock(ContentType.NOTE, "WCAG (Web Content Accessibility Guidelines) follow করা উচিত। Color contrast, keyboard navigation, alt text — সব গুরুত্বপূর্ণ।"),
                    ContentBlock(ContentType.TIP, "Keyboard দিয়ে (Tab key) পুরো site navigate করা যাচ্ছে কিনা test করো।")
                ),
                codeExample = """<!-- Accessible Button -->
<button 
  aria-label="Menu খোলো"
  aria-expanded="false"
  aria-controls="nav-menu">
  <svg aria-hidden="true" focusable="false">
    <!-- icon SVG -->
  </svg>
</button>

<!-- Skip to main content (keyboard users-এর জন্য) -->
<a href="#main" class="skip-link">
  মূল content-এ যাও
</a>

<!-- Accessible Form -->
<form>
  <div role="group" aria-labelledby="address-heading">
    <h3 id="address-heading">ঠিকানা</h3>
    
    <label for="street">রাস্তা:
      <span aria-hidden="true">*</span>
      <span class="sr-only">(আবশ্যক)</span>
    </label>
    <input type="text" id="street" name="street" 
           required
           aria-describedby="street-hint">
    <p id="street-hint" class="hint">
      বাড়ি/ফ্ল্যাট নম্বর সহ লেখো
    </p>
  </div>
</form>

<!-- Live region (dynamic updates) -->
<div aria-live="polite" id="status">
  <!-- JavaScript এখানে status update করবে -->
</div>

<!-- Focus management -->
<dialog aria-labelledby="dialog-title">
  <h2 id="dialog-title">Confirmation</h2>
  <button autofocus>OK</button>
  <button>Cancel</button>
</dialog>""",
                quiz = listOf(
                    QuizQuestion("ARIA-এর পূর্ণ নাম কী?",
                        listOf("Accessible Rich Internet Applications","Advanced Rendering Interface API","Application Rich Internet Architecture","Automated Response Internet App"), 0,
                        "ARIA = Accessible Rich Internet Applications"),
                    QuizQuestion("aria-hidden='true' কী করে?",
                        listOf("Element লুকায়","Screen reader থেকে element লুকায়","Element transparent করে","Display none করে"), 1,
                        "aria-hidden='true' screen reader কে এই element ignore করতে বলে।"),
                    QuizQuestion("Accessibility test করার একটি উপায় কী?",
                        listOf("Mouse দিয়ে click করা","Keyboard দিয়ে Tab করে navigate করা","Page refresh করা","Screen screenshot নেওয়া"), 1,
                        "Keyboard-only navigation test করলে accessibility সমস্যা ধরা পড়ে।"),
                    QuizQuestion("aria-label attribute কী করে?",
                        listOf("Element style করে","Element-এর description screen reader কে বলে","Label তৈরি করে","Title যোগ করে"), 1,
                        "aria-label screen reader-এর জন্য element-এর description দেয়।"),
                    QuizQuestion("WCAG কী?",
                        listOf("Web CSS Animation Guide","Web Content Accessibility Guidelines","Web Code Analysis Guide","Web Component API Guide"), 1,
                        "WCAG = Web Content Accessibility Guidelines — web accessibility standard।")
                )
            ),
            Lesson(
                id = "ch15_l2", chapterId = "ch15",
                title = "HTML Performance Optimization", subtitle = "দ্রুত লোড হওয়া পেজ",
                estimatedMinutes = 15, difficulty = Difficulty.ADVANCED,
                content = listOf(
                    ContentBlock(ContentType.THEORY, "ওয়েবসাইটের speed user experience-এর জন্য অনেক গুরুত্বপূর্ণ। ধীর website-এ user থাকে না। HTML-এই অনেক optimization করা যায়।"),
                    ContentBlock(ContentType.DEFINITION, "Preload: গুরুত্বপূর্ণ resource আগে load\nPrefetch: পরের page-এর resource আগে load\nLazy loading: প্রয়োজনের সময় load\nAsync/defer: JS loading অপ্টিমাইজ\nResource hints: browser-কে hint দেওয়া"),
                    ContentBlock(ContentType.NOTE, "Google Core Web Vitals (LCP, FID, CLS) page ranking-এ প্রভাব ফেলে। HTML optimization এগুলো উন্নত করে।"),
                    ContentBlock(ContentType.TIP, "Google PageSpeed Insights দিয়ে page performance test করো।")
                ),
                codeExample = """<head>
  <!-- DNS prefetch (external domains) -->
  <link rel="dns-prefetch" href="//fonts.googleapis.com">
  
  <!-- Preconnect (important 3rd party) -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  
  <!-- Preload critical assets -->
  <link rel="preload" href="hero.jpg" as="image">
  <link rel="preload" href="critical.css" as="style">
  <link rel="preload" href="main.js" as="script">
  
  <!-- Prefetch next page -->
  <link rel="prefetch" href="/next-page.html">
  
  <!-- CSS (blocking) -->
  <link rel="stylesheet" href="styles.css">
  
  <!-- Critical CSS inline হলে আরো ভালো -->
  <style>/* above-fold CSS here */</style>
</head>

<body>
  <!-- Hero image — preload করা উচিত -->
  <img src="hero.jpg" alt="Hero" fetchpriority="high">
  
  <!-- Below fold images — lazy load -->
  <img src="photo1.jpg" alt="Photo 1" loading="lazy">
  <img src="photo2.jpg" alt="Photo 2" loading="lazy">
  
  <!-- Scripts (defer বা async) -->
  <!-- defer: parse শেষে, order বজায় -->
  <script src="app.js" defer></script>
  
  <!-- async: যখন পারে execute, order নেই -->
  <script src="analytics.js" async></script>
  
  <!-- নিচে রাখলেও চলে (blocking নয়) -->
  <script src="non-critical.js"></script>
</body>""",
                quiz = listOf(
                    QuizQuestion("loading='lazy' image কখন load করে?",
                        listOf("Page load-এর সাথে","যখন user সেটা দেখার কাছে পৌঁছায়","Page-এর শেষে","কখনো না"), 1,
                        "Lazy loading শুধু তখনই image load করে যখন user সেটা দেখতে পারবে।"),
                    QuizQuestion("defer ও async-এর মধ্যে পার্থক্য কী?",
                        listOf("কোনো পার্থক্য নেই","defer order বজায় রাখে, async রাখে না","defer async-এর চেয়ে দ্রুত","async defer-এর চেয়ে ভালো"), 1,
                        "defer HTML parse শেষে order মেনে execute করে। async যখন ready তখন execute করে।"),
                    QuizQuestion("Preload link কী করে?",
                        listOf("Next page load করে","গুরুত্বপূর্ণ resource আগে থেকে load করে","DNS resolve করে","Connection তৈরি করে"), 1,
                        "preload critical resource আগে download শুরু করে।"),
                    QuizQuestion("Google Core Web Vitals কী?",
                        listOf("Google-এর coding guidelines","Page performance metrics যা ranking affect করে","Google-এর HTML standards","Google Analytics features"), 1,
                        "Core Web Vitals (LCP, FID, CLS) Google-এর page experience metrics।"),
                    QuizQuestion("fetchpriority='high' কী করে?",
                        listOf("Image compression করে","Browser কে বলে এই resource উচ্চ priority-তে load করতে","Image lazy load করে","Image quality বাড়ায়"), 1,
                        "fetchpriority='high' browser কে নির্দিষ্ট resource আগে load করতে নির্দেশ করে।")
                )
            )
        )
    )
}
