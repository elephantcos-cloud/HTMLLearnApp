package com.htmllearn.app.content

data class TagReference(
    val tag: String,
    val category: TagCategory,
    val description: String,
    val attributes: List<String>,
    val example: String,
    val isVoid: Boolean = false,
    val htmlVersion: String = "HTML5"
)

enum class TagCategory(val label: String) {
    STRUCTURE("কাঠামো"),
    TEXT("টেক্সট"),
    LINK("লিংক"),
    LIST("তালিকা"),
    TABLE("টেবিল"),
    FORM("ফর্ম"),
    MEDIA("মিডিয়া"),
    SEMANTIC("সেম্যান্টিক"),
    SCRIPTING("স্ক্রিপ্টিং"),
    META("মেটা")
}

object TagReferenceContent {

    fun getAllTags(): List<TagReference> = listOf(
        // STRUCTURE
        TagReference("html", TagCategory.STRUCTURE, "পুরো HTML document-এর root element। সব কিছু এর ভেতরে থাকে।",
            listOf("lang — পেজের ভাষা নির্ধারণ"),
            "<html lang=\"bn\">\n  <!-- সব content এখানে -->\n</html>"),
        TagReference("head", TagCategory.STRUCTURE, "Document-এর metadata ধারণ করে। Title, CSS links, meta tags এখানে থাকে।",
            listOf(), "<head>\n  <title>পেজের শিরোনাম</title>\n</head>"),
        TagReference("body", TagCategory.STRUCTURE, "পেজের দৃশ্যমান content এখানে থাকে।",
            listOf("onload — page load হলে function call"),
            "<body>\n  <h1>দৃশ্যমান content</h1>\n</body>"),
        TagReference("div", TagCategory.STRUCTURE, "Block-level container। Section বা group তৈরিতে ব্যবহার হয়।",
            listOf("class, id, style"),
            "<div class=\"card\">\n  <p>Content</p>\n</div>"),
        TagReference("span", TagCategory.STRUCTURE, "Inline container। Text-এর মধ্যে কোনো অংশ style করতে ব্যবহার হয়।",
            listOf("class, id, style"),
            "<p>এই <span class=\"red\">অংশটি</span> লাল।</p>"),

        // TEXT
        TagReference("h1", TagCategory.TEXT, "সবচেয়ে বড় heading। পেজের মূল শিরোনাম। একটি পেজে মাত্র একটি h1 থাকা উচিত।",
            listOf("class, id, style"),
            "<h1>প্রধান শিরোনাম</h1>"),
        TagReference("h2", TagCategory.TEXT, "দ্বিতীয় স্তরের heading। অধ্যায় বা section-এর শিরোনামে ব্যবহার হয়।",
            listOf(), "<h2>অধ্যায় শিরোনাম</h2>"),
        TagReference("h3", TagCategory.TEXT, "তৃতীয় স্তরের heading।", listOf(), "<h3>ধারা শিরোনাম</h3>"),
        TagReference("h4", TagCategory.TEXT, "চতুর্থ স্তরের heading।", listOf(), "<h4>উপধারা</h4>"),
        TagReference("h5", TagCategory.TEXT, "পঞ্চম স্তরের heading।", listOf(), "<h5>ছোট শিরোনাম</h5>"),
        TagReference("h6", TagCategory.TEXT, "সবচেয়ে ছোট heading।", listOf(), "<h6>ক্ষুদ্র শিরোনাম</h6>"),
        TagReference("p", TagCategory.TEXT, "Paragraph। প্রতিটি paragraph নতুন line-এ শুরু হয় এবং margin থাকে।",
            listOf("class, id, style"),
            "<p>এটি একটি paragraph।</p>"),
        TagReference("strong", TagCategory.TEXT, "Semantically গুরুত্বপূর্ণ text। Default-এ bold দেখায়।",
            listOf(), "<p>এটি <strong>গুরুত্বপূর্ণ</strong>।</p>"),
        TagReference("em", TagCategory.TEXT, "জোর দেওয়া text। Default-এ italic দেখায়।",
            listOf(), "<p>এটি <em>জোর দিয়ে</em> বলা।</p>"),
        TagReference("u", TagCategory.TEXT, "Underline text।", listOf(), "<p><u>underlined text</u></p>"),
        TagReference("s", TagCategory.TEXT, "Strikethrough — কাটা text। পুরনো বা বাতিল তথ্য বোঝায়।",
            listOf(), "<p>পুরনো দাম: <s>১০০ টাকা</s> নতুন: ৮০ টাকা</p>"),
        TagReference("mark", TagCategory.TEXT, "Highlighted text। Default-এ হলুদ background।",
            listOf(), "<p>এটি <mark>গুরুত্বপূর্ণ</mark> অংশ।</p>"),
        TagReference("small", TagCategory.TEXT, "ছোট text। Copyright, disclaimer-এ ব্যবহার।",
            listOf(), "<p><small>© 2024 সব অধিকার সংরক্ষিত</small></p>"),
        TagReference("sup", TagCategory.TEXT, "Superscript — উপরে উঠানো text। গণিতে x² লিখতে।",
            listOf(), "<p>x<sup>2</sup> + y<sup>2</sup></p>"),
        TagReference("sub", TagCategory.TEXT, "Subscript — নিচে নামানো text। রসায়নে H₂O লিখতে।",
            listOf(), "<p>H<sub>2</sub>O</p>"),
        TagReference("br", TagCategory.TEXT, "Line break। নতুন লাইনে নামাতে। Void element।",
            listOf(), "<p>প্রথম লাইন<br>দ্বিতীয় লাইন</p>", true),
        TagReference("hr", TagCategory.TEXT, "Horizontal rule — একটি horizontal line। Section আলাদা করতে।",
            listOf(), "<hr>", true),
        TagReference("blockquote", TagCategory.TEXT, "Block-level quotation। অন্য source থেকে উদ্ধৃতি।",
            listOf("cite — source URL"),
            "<blockquote cite=\"https://source.com\">\n  উদ্ধৃতি এখানে\n</blockquote>"),
        TagReference("code", TagCategory.TEXT, "Inline code। Programming code বা technical term-এ।",
            listOf(), "<p><code>console.log('Hello')</code> চালাও।</p>"),
        TagReference("pre", TagCategory.TEXT, "Preformatted text। Space ও newline বজায় রাখে। Code block-এ ব্যবহার।",
            listOf(), "<pre>\nfunction hello() {\n  return 'Hi';\n}\n</pre>"),
        TagReference("abbr", TagCategory.TEXT, "Abbreviation। Short form-এর full form tooltip-এ দেখায়।",
            listOf("title — পূর্ণ রূপ"),
            "<abbr title=\"HyperText Markup Language\">HTML</abbr>"),

        // LINK
        TagReference("a", TagCategory.LINK, "Anchor tag। Link তৈরি করে। অন্য page, section বা email/phone link করে।",
            listOf("href — destination", "target — _blank নতুন tab", "rel — relationship", "download — download করতে"),
            "<a href=\"https://google.com\" target=\"_blank\">Google</a>"),

        // LIST
        TagReference("ul", TagCategory.LIST, "Unordered list — bullet points।",
            listOf("type — disc, circle, square"),
            "<ul>\n  <li>Item 1</li>\n  <li>Item 2</li>\n</ul>"),
        TagReference("ol", TagCategory.LIST, "Ordered list — numbered list।",
            listOf("type — 1, A, a, I, i", "start — শুরুর নম্বর", "reversed — উল্টো ক্রমে"),
            "<ol>\n  <li>প্রথম</li>\n  <li>দ্বিতীয়</li>\n</ol>"),
        TagReference("li", TagCategory.LIST, "List item। ul বা ol-এর ভেতরে থাকে।",
            listOf("value — specific number (ol-এ)"),
            "<li>একটি item</li>"),
        TagReference("dl", TagCategory.LIST, "Definition list। Term ও তার বিবরণ।",
            listOf(), "<dl>\n  <dt>HTML</dt>\n  <dd>ওয়েবের ভাষা</dd>\n</dl>"),

        // TABLE
        TagReference("table", TagCategory.TABLE, "Table তৈরির মূল element।",
            listOf("border — border দেখাতে"),
            "<table>\n  <tr><th>নাম</th><th>বয়স</th></tr>\n  <tr><td>Shohan</td><td>20</td></tr>\n</table>"),
        TagReference("tr", TagCategory.TABLE, "Table row — একটি সারি।", listOf(), "<tr>...</tr>"),
        TagReference("th", TagCategory.TABLE, "Table header cell — bold ও centered।",
            listOf("colspan", "rowspan", "scope"),
            "<th colspan=\"2\">নাম ও বয়স</th>"),
        TagReference("td", TagCategory.TABLE, "Table data cell — সাধারণ cell।",
            listOf("colspan", "rowspan"),
            "<td>Shohan</td>"),
        TagReference("thead", TagCategory.TABLE, "Table header section।", listOf(), "<thead><tr><th>...</th></tr></thead>"),
        TagReference("tbody", TagCategory.TABLE, "Table body section।", listOf(), "<tbody><tr><td>...</td></tr></tbody>"),
        TagReference("tfoot", TagCategory.TABLE, "Table footer section।", listOf(), "<tfoot><tr><td>...</td></tr></tfoot>"),

        // FORM
        TagReference("form", TagCategory.FORM, "Form তৈরির মূল element। User থেকে data নেয়।",
            listOf("action — submit destination", "method — GET/POST", "enctype — file upload-এ multipart/form-data"),
            "<form action=\"/submit\" method=\"POST\">\n  ...\n</form>"),
        TagReference("input", TagCategory.FORM, "Input field। বিভিন্ন type-এর data input নেয়। Void element।",
            listOf("type — text/email/password/number/checkbox/radio/file/date/range/color",
                "name", "value", "placeholder", "required", "disabled", "readonly", "min/max", "pattern"),
            "<input type=\"text\" placeholder=\"নাম লেখো\" required>", true),
        TagReference("label", TagCategory.FORM, "Input-এর label। for attribute দিয়ে input-এর সাথে connect করে।",
            listOf("for — input-এর id"),
            "<label for=\"email\">ইমেইল:</label>\n<input type=\"email\" id=\"email\">"),
        TagReference("button", TagCategory.FORM, "Clickable button। Submit, reset বা custom action।",
            listOf("type — submit/reset/button", "disabled"),
            "<button type=\"submit\">জমা দাও</button>"),
        TagReference("select", TagCategory.FORM, "Dropdown select box।",
            listOf("name", "multiple", "size"),
            "<select name=\"city\">\n  <option value=\"dhaka\">ঢাকা</option>\n  <option value=\"mymensingh\">ময়মনসিংহ</option>\n</select>"),
        TagReference("option", TagCategory.FORM, "Select box-এর একটি option।",
            listOf("value", "selected — default selected", "disabled"),
            "<option value=\"dhaka\">ঢাকা</option>"),
        TagReference("textarea", TagCategory.FORM, "Multi-line text input।",
            listOf("rows", "cols", "placeholder", "maxlength", "resize"),
            "<textarea rows=\"4\" placeholder=\"বার্তা লেখো...\"></textarea>"),
        TagReference("fieldset", TagCategory.FORM, "Form-এর related fields group করে।",
            listOf("disabled"),
            "<fieldset>\n  <legend>ব্যক্তিগত তথ্য</legend>\n  ...\n</fieldset>"),
        TagReference("legend", TagCategory.FORM, "Fieldset-এর caption।", listOf(), "<legend>যোগাযোগ তথ্য</legend>"),
        TagReference("datalist", TagCategory.FORM, "Input-এর জন্য suggestion list।",
            listOf(),
            "<input list=\"cities\">\n<datalist id=\"cities\">\n  <option value=\"ঢাকা\">\n  <option value=\"ময়মনসিংহ\">\n</datalist>"),

        // MEDIA
        TagReference("img", TagCategory.MEDIA, "Image দেখায়। Void element।",
            listOf("src — image path", "alt — বিকল্প text (আবশ্যক)", "width/height", "loading — lazy", "fetchpriority"),
            "<img src=\"photo.jpg\" alt=\"ছবির বিবরণ\" loading=\"lazy\">", true),
        TagReference("audio", TagCategory.MEDIA, "Audio player।",
            listOf("controls", "autoplay", "loop", "muted", "preload"),
            "<audio controls>\n  <source src=\"song.mp3\" type=\"audio/mpeg\">\n</audio>"),
        TagReference("video", TagCategory.MEDIA, "Video player।",
            listOf("controls", "autoplay", "loop", "muted", "width/height", "poster", "preload"),
            "<video controls width=\"640\" poster=\"thumb.jpg\">\n  <source src=\"video.mp4\" type=\"video/mp4\">\n</video>"),
        TagReference("source", TagCategory.MEDIA, "Media-র alternative source।",
            listOf("src", "type"), "<source src=\"video.webm\" type=\"video/webm\">", true),
        TagReference("iframe", TagCategory.MEDIA, "অন্য page বা content embed করে।",
            listOf("src", "width/height", "frameborder", "allowfullscreen", "loading"),
            "<iframe src=\"https://youtube.com/embed/ID\" allowfullscreen></iframe>"),
        TagReference("figure", TagCategory.MEDIA, "Self-contained media content।",
            listOf(),
            "<figure>\n  <img src=\"photo.jpg\" alt=\"ছবি\">\n  <figcaption>ছবির বিবরণ</figcaption>\n</figure>"),
        TagReference("figcaption", TagCategory.MEDIA, "Figure-এর caption।", listOf(), "<figcaption>ছবির বিবরণ</figcaption>"),
        TagReference("canvas", TagCategory.MEDIA, "JavaScript দিয়ে graphics আঁকার জন্য।",
            listOf("width", "height", "id"),
            "<canvas id=\"myCanvas\" width=\"400\" height=\"300\"></canvas>"),
        TagReference("svg", TagCategory.MEDIA, "Scalable Vector Graphics — HTML-এ vector graphics।",
            listOf("width", "height", "viewBox"),
            "<svg width=\"100\" height=\"100\">\n  <circle cx=\"50\" cy=\"50\" r=\"40\" fill=\"blue\"/>\n</svg>"),

        // SEMANTIC
        TagReference("header", TagCategory.SEMANTIC, "পেজ বা section-এর header।",
            listOf(), "<header>\n  <h1>সাইটের নাম</h1>\n  <nav>...</nav>\n</header>"),
        TagReference("nav", TagCategory.SEMANTIC, "Navigation links।",
            listOf("aria-label"),
            "<nav>\n  <a href=\"/\">হোম</a>\n  <a href=\"/about\">পরিচিতি</a>\n</nav>"),
        TagReference("main", TagCategory.SEMANTIC, "পেজের মূল content। প্রতি page-এ একটি।",
            listOf(), "<main>\n  <article>...</article>\n</main>"),
        TagReference("article", TagCategory.SEMANTIC, "Self-contained content (blog post, news, comment)।",
            listOf(), "<article>\n  <h2>Article Title</h2>\n  <p>Content...</p>\n</article>"),
        TagReference("section", TagCategory.SEMANTIC, "Thematically related content-এর group।",
            listOf(), "<section>\n  <h2>Section Title</h2>\n  <p>Content...</p>\n</section>"),
        TagReference("aside", TagCategory.SEMANTIC, "Sidebar বা extra information।",
            listOf(), "<aside>\n  <h3>Related Links</h3>\n  ...\n</aside>"),
        TagReference("footer", TagCategory.SEMANTIC, "পেজ বা section-এর footer।",
            listOf(), "<footer>\n  <p>© 2024 সব অধিকার সংরক্ষিত</p>\n</footer>"),
        TagReference("details", TagCategory.SEMANTIC, "Expandable/collapsible content।",
            listOf("open — default open"),
            "<details>\n  <summary>আরো দেখো</summary>\n  <p>লুকানো content</p>\n</details>"),
        TagReference("summary", TagCategory.SEMANTIC, "Details-এর visible title।",
            listOf(), "<summary>Click করে দেখো</summary>"),
        TagReference("time", TagCategory.SEMANTIC, "Date ও time semantic markup।",
            listOf("datetime — machine-readable format"),
            "<time datetime=\"2024-01-15\">জানুয়ারি ১৫, ২০২৪</time>"),
        TagReference("address", TagCategory.SEMANTIC, "Contact information।",
            listOf(), "<address>\n  ইমেইল: <a href=\"mailto:info@example.com\">info@example.com</a>\n</address>"),

        // SCRIPTING
        TagReference("script", TagCategory.SCRIPTING, "JavaScript code বা file যোগ করে।",
            listOf("src — external JS file", "defer — parse শেষে run", "async — async run", "type"),
            "<script src=\"app.js\" defer></script>"),
        TagReference("noscript", TagCategory.SCRIPTING, "JavaScript disabled থাকলে দেখানো content।",
            listOf(), "<noscript>\n  <p>JavaScript enable করুন।</p>\n</noscript>"),
        TagReference("template", TagCategory.SCRIPTING, "HTML template যা render হয় না, JavaScript দিয়ে clone করা হয়।",
            listOf(), "<template id=\"card-template\">\n  <div class=\"card\">...</div>\n</template>"),

        // META
        TagReference("meta", TagCategory.META, "Document metadata। Browser, search engine-এর জন্য তথ্য। Void element।",
            listOf("charset", "name", "content", "property", "http-equiv"),
            "<meta charset=\"UTF-8\">\n<meta name=\"description\" content=\"বিবরণ\">", true),
        TagReference("title", TagCategory.META, "Browser tab ও search result-এ দেখানো পেজের নাম।",
            listOf(), "<title>পেজের শিরোনাম | ওয়েবসাইট</title>"),
        TagReference("link", TagCategory.META, "External resource (CSS, favicon, etc.) যোগ করে। Void element।",
            listOf("rel — relationship", "href — file path", "type"),
            "<link rel=\"stylesheet\" href=\"styles.css\">\n<link rel=\"icon\" href=\"favicon.ico\">", true),
        TagReference("style", TagCategory.META, "Internal CSS লেখার জন্য।",
            listOf(), "<style>\n  body { background: #0D1117; }\n</style>"),
        TagReference("base", TagCategory.META, "Page-এর সব relative URL-এর base URL set করে। Void element।",
            listOf("href — base URL", "target"),
            "<base href=\"https://example.com/\">", true)
    )

    fun searchTags(query: String): List<TagReference> {
        if (query.isEmpty()) return getAllTags()
        val q = query.lowercase().trim()
        return getAllTags().filter { tag ->
            tag.tag.contains(q) ||
            tag.description.contains(q) ||
            tag.category.label.contains(q)
        }
    }

    fun getTagsByCategory(category: TagCategory): List<TagReference> =
        getAllTags().filter { it.category == category }
}
