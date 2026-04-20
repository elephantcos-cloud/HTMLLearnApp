package com.htmllearn.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.htmllearn.app.content.*
import com.htmllearn.app.ui.components.*
import com.htmllearn.app.ui.theme.*
import com.htmllearn.app.viewmodel.AppViewModel

// ==================== ONBOARDING ====================
@Composable
fun OnboardingScreen(onFinish: () -> Unit, viewModel: AppViewModel) {
    var name by remember { mutableStateOf("") }
    var step by remember { mutableIntStateOf(0) }
    var goalMinutes by remember { mutableIntStateOf(30) }

    Box(modifier = Modifier.fillMaxSize().background(BgPrimary), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            when (step) {
                0 -> {
                    BookIcon(color = AccentBlue, size = 80.dp)
                    Text("HTML শেখো বাংলায়", style = MaterialTheme.typography.headlineMedium)
                    Text("Beginner থেকে Advanced পর্যন্ত সম্পূর্ণ বাংলায়",
                        style = MaterialTheme.typography.bodyMedium, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Button(onClick = { step = 1 }, modifier = Modifier.fillMaxWidth()) { Text("শুরু করো") }
                }
                1 -> {
                    Text("তোমার নাম কী?", style = MaterialTheme.typography.headlineSmall)
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        label = { Text("নাম লেখো") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentBlue,
                            unfocusedBorderColor = BorderColor,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                    Button(
                        onClick = { if (name.isNotBlank()) step = 2 },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = name.isNotBlank()
                    ) { Text("পরবর্তী") }
                }
                2 -> {
                    Text("প্রতিদিন কতক্ষণ পড়তে চাও?", style = MaterialTheme.typography.headlineSmall)
                    Text("$goalMinutes মিনিট", style = MaterialTheme.typography.headlineMedium.copy(color = AccentBlue))
                    Slider(
                        value = goalMinutes.toFloat(), onValueChange = { goalMinutes = it.toInt() },
                        valueRange = 5f..120f, steps = 22,
                        colors = SliderDefaults.colors(thumbColor = AccentBlue, activeTrackColor = AccentBlue)
                    )
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("৫ মিনিট", style = MaterialTheme.typography.labelSmall)
                        Text("১২০ মিনিট", style = MaterialTheme.typography.labelSmall)
                    }
                    Button(
                        onClick = {
                            viewModel.initUser(name)
                            viewModel.setStudyGoal(goalMinutes)
                            onFinish()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("চালিয়ে যাও") }
                }
            }
        }
    }
}

// ==================== LESSONS SCREEN ====================
@Composable
fun LessonsScreen(
    onLessonClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: AppViewModel
) {
    val lessonState by viewModel.lessonState.collectAsState()
    val chapters = LessonContent.getAllChapters()

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = "পাঠ্যক্রম", onBack = onBackClick)
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            chapters.forEach { chapter ->
                item {
                    ChapterSection(
                        chapter = chapter,
                        progressMap = lessonState.allProgress,
                        onLessonClick = onLessonClick
                    )
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ChapterSection(
    chapter: Chapter,
    progressMap: Map<String, com.htmllearn.app.data.db.entity.LessonProgressEntity>,
    onLessonClick: (String) -> Unit
) {
    val color = Color(chapter.color)
    var expanded by remember { mutableStateOf(true) }
    val completed = chapter.lessons.count { progressMap[it.id]?.isCompleted == true }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(44.dp).clip(RoundedCornerShape(10.dp)).background(color.copy(0.2f)),
                    contentAlignment = Alignment.Center
                ) { BookIcon(color = color, size = 24.dp) }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(chapter.title, style = MaterialTheme.typography.titleMedium.copy(color = color))
                    Text(chapter.subtitle, style = MaterialTheme.typography.bodySmall)
                    Text("$completed/${chapter.lessons.size} সম্পন্ন", style = MaterialTheme.typography.labelSmall.copy(color = AccentGreen))
                }
                Box(
                    modifier = Modifier.size(24.dp).clip(CircleShape)
                        .background(if (expanded) color.copy(0.2f) else BgTertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(Modifier.size(12.dp)) {
                        val w = size.width; val h = size.height
                        drawPath(androidx.compose.ui.graphics.Path().apply {
                            if (expanded) { moveTo(w*0.2f,h*0.35f); lineTo(w*0.5f,h*0.65f); lineTo(w*0.8f,h*0.35f) }
                            else { moveTo(w*0.2f,h*0.65f); lineTo(w*0.5f,h*0.35f); lineTo(w*0.8f,h*0.65f) }
                        }, color, style = Stroke(w*0.15f, cap = StrokeCap.Round, join = StrokeJoin.Round))
                    }
                }
            }
            if (expanded) {
                HorizontalDivider(color = BorderColor, thickness = 1.dp)
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chapter.lessons.forEachIndexed { index, lesson ->
                        val isCompleted = progressMap[lesson.id]?.isCompleted == true
                        val isLocked = index > 0 && progressMap[chapter.lessons[index - 1].id]?.isCompleted != true
                        val difficultyLabel = when (lesson.difficulty) {
                            Difficulty.BEGINNER -> "সহজ"; Difficulty.INTERMEDIATE -> "মধ্যম"; Difficulty.ADVANCED -> "কঠিন"
                        }
                        val difficultyColor = when (lesson.difficulty) {
                            Difficulty.BEGINNER -> AccentGreen; Difficulty.INTERMEDIATE -> AccentYellow; Difficulty.ADVANCED -> AccentOrange
                        }
                        LessonCard(
                            title = lesson.title, subtitle = lesson.subtitle,
                            isCompleted = isCompleted, isLocked = isLocked,
                            estimatedMinutes = lesson.estimatedMinutes,
                            difficulty = difficultyLabel, difficultyColor = difficultyColor,
                            chapterColor = color,
                            onClick = { onLessonClick(lesson.id) }
                        )
                    }
                }
            }
        }
    }
}

// ==================== LESSON DETAIL ====================
@Composable
fun LessonDetailScreen(
    lessonId: String,
    onBack: () -> Unit,
    onStartQuiz: () -> Unit,
    onOpenEditor: () -> Unit,
    viewModel: AppViewModel
) {
    val lesson = LessonContent.getLessonById(lessonId) ?: return
    val lessonState by viewModel.lessonState.collectAsState()
    val progress = lessonState.allProgress[lessonId]
    val isCompleted = progress?.isCompleted == true

    LaunchedEffect(lessonId) { viewModel.startLesson(lessonId) }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = lesson.title, onBack = onBack)
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Chip("${lesson.estimatedMinutes} মিনিট", AccentBlue)
                    val diffLabel = when(lesson.difficulty) {
                        Difficulty.BEGINNER -> "সহজ"; Difficulty.INTERMEDIATE -> "মধ্যম"; Difficulty.ADVANCED -> "কঠিন"
                    }
                    val diffColor = when(lesson.difficulty) {
                        Difficulty.BEGINNER -> AccentGreen; Difficulty.INTERMEDIATE -> AccentYellow; Difficulty.ADVANCED -> AccentOrange
                    }
                    Chip(diffLabel, diffColor)
                    Chip("+${lesson.xpReward} XP", XpColor)
                }
            }
            item { Text(lesson.subtitle, style = MaterialTheme.typography.bodyLarge) }
            items(lesson.content) { block ->
                ContentBlockView(type = block.type, text = block.text)
            }
            item {
                Text("কোড উদাহরণ", style = MaterialTheme.typography.titleMedium.copy(color = AccentBlue))
                Spacer(Modifier.height(6.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1F2D)),
                    border = BorderStroke(1.dp, AccentBlue.copy(0.4f))
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(Color(0xFF1A2F42)).padding(8.dp, 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("HTML", style = MaterialTheme.typography.labelSmall.copy(color = AccentBlue))
                            TextButton(onClick = { onOpenEditor() }) {
                                Text("Editor-এ খোলো", style = MaterialTheme.typography.labelSmall.copy(color = AccentGreen))
                            }
                        }
                        Text(
                            lesson.codeExample,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                            )
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.height(8.dp))
                if (!isCompleted) {
                    Button(
                        onClick = {
                            viewModel.completeLesson(lessonId)
                            onStartQuiz()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) { Text("পাঠ সম্পন্ন ও Quiz শুরু") }
                } else {
                    OutlinedButton(
                        onClick = onStartQuiz,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) { Text("Quiz আবার দাও") }
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

// ==================== QUIZ SCREEN ====================
@Composable
fun QuizScreen(
    lessonId: String,
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: AppViewModel
) {
    val lesson = LessonContent.getLessonById(lessonId) ?: return
    val questions = lesson.quiz
    var currentQ by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableIntStateOf(-1) }
    var answered by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }
    val quizResult by viewModel.quizResult.collectAsState()

    LaunchedEffect(quizResult) {
        if (quizResult != null) viewModel.clearQuizResult()
    }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        if (!finished) {
            val question = questions[currentQ]
            TopBar(title = "Quiz — ${lesson.title}", onBack = onBack)
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                LinearProgressIndicator(
                    progress = { (currentQ + 1).toFloat() / questions.size },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = AccentBlue, trackColor = BgTertiary
                )
                Spacer(Modifier.height(8.dp))
                Text("প্রশ্ন ${currentQ + 1}/${questions.size}", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BgSecondary),
                    border = BorderStroke(1.dp, BorderColor)
                ) {
                    Text(question.question, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.height(16.dp))
                question.options.forEachIndexed { index, option ->
                    val bgColor = when {
                        !answered -> if (selectedAnswer == index) AccentBlue.copy(0.2f) else BgSecondary
                        index == question.correctAnswer -> AccentGreen.copy(0.2f)
                        index == selectedAnswer && selectedAnswer != question.correctAnswer -> AccentOrange.copy(0.2f)
                        else -> BgSecondary
                    }
                    val borderColor = when {
                        !answered -> if (selectedAnswer == index) AccentBlue else BorderColor
                        index == question.correctAnswer -> AccentGreen
                        index == selectedAnswer && selectedAnswer != question.correctAnswer -> AccentOrange
                        else -> BorderColor
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(enabled = !answered) {
                            selectedAnswer = index; answered = true
                            if (index == question.correctAnswer) score++
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = bgColor),
                        border = BorderStroke(1.dp, borderColor)
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(28.dp).clip(CircleShape)
                                    .background(borderColor.copy(0.2f)).border(1.dp, borderColor, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${('A' + index)}", style = MaterialTheme.typography.labelMedium.copy(color = borderColor))
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(option, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                if (answered) {
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedAnswer == question.correctAnswer) AccentGreen.copy(0.1f) else AccentOrange.copy(0.1f)
                        ),
                        border = BorderStroke(1.dp, if (selectedAnswer == question.correctAnswer) AccentGreen.copy(0.4f) else AccentOrange.copy(0.4f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                if (selectedAnswer == question.correctAnswer) "সঠিক!" else "ভুল!",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = if (selectedAnswer == question.correctAnswer) AccentGreen else AccentOrange
                                )
                            )
                            Text(question.explanation, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (currentQ < questions.size - 1) {
                                currentQ++; selectedAnswer = -1; answered = false
                            } else {
                                finished = true
                                viewModel.submitQuiz(lessonId, score, questions.size)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (currentQ < questions.size - 1) "পরবর্তী প্রশ্ন" else "ফলাফল দেখো")
                    }
                }
                Spacer(Modifier.height(80.dp))
            }
        } else {
            // Result screen
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val percent = (score.toFloat() / questions.size * 100).toInt()
                val resultColor = when {
                    percent >= 80 -> AccentGreen
                    percent >= 60 -> AccentYellow
                    else -> AccentOrange
                }
                CircularProgress(progress = percent / 100f, size = 140.dp, strokeWidth = 12.dp, color = resultColor) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$percent%", style = MaterialTheme.typography.headlineMedium.copy(color = resultColor, fontWeight = FontWeight.Bold))
                        Text("$score/${questions.size}", style = MaterialTheme.typography.labelMedium)
                    }
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    when { percent == 100 -> "নিখুঁত!"; percent >= 80 -> "দারুণ!"; percent >= 60 -> "ভালো!"; else -> "আরো চেষ্টা করো!" },
                    style = MaterialTheme.typography.headlineSmall.copy(color = resultColor)
                )
                Spacer(Modifier.height(8.dp))
                val xpEarned = when { percent == 100 -> 100; percent >= 80 -> 75; percent >= 60 -> 50; else -> 25 }
                Text("+$xpEarned XP অর্জন", style = MaterialTheme.typography.titleMedium.copy(color = XpColor))
                Spacer(Modifier.height(32.dp))
                Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) { Text("পাঠ্যক্রমে ফিরো") }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        currentQ = 0; selectedAnswer = -1; answered = false; score = 0; finished = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("আবার চেষ্টা করো") }
            }
        }
    }
}

// ==================== CODE EDITOR ====================
@Composable
fun CodeEditorScreen(onBack: () -> Unit) {
    var htmlCode by remember { mutableStateOf("""<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <title>আমার পেজ</title>
  <style>
    body { background: #1a1a2e; color: #e2e2e2;
           font-family: Arial; padding: 20px; }
    h1 { color: #58A6FF; }
    p { line-height: 1.6; }
  </style>
</head>
<body>
  <h1>নমস্কার!</h1>
  <p>এখানে তোমার HTML লেখো এবং পাশে output দেখো।</p>
</body>
</html>""") }
    var showPreview by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(BgSecondary)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    BackArrowIcon(color = TextPrimary)
                }
                Text("Live Code Editor", style = MaterialTheme.typography.titleMedium)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = !showPreview, onClick = { showPreview = false }, label = { Text("Code") })
                FilterChip(selected = showPreview, onClick = { showPreview = true }, label = { Text("Preview") })
            }
        }
        HorizontalDivider(color = BorderColor)

        if (!showPreview) {
            BasicTextField(
                value = htmlCode,
                onValueChange = { htmlCode = it },
                modifier = Modifier.fillMaxSize().padding(16.dp),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = TextPrimary,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    lineHeight = 20.sp
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (htmlCode.isEmpty()) Text("HTML লিখতে শুরু করো...", color = TextMuted)
                        innerTextField()
                    }
                }
            )
        } else {
            android.webkit.WebView(androidx.compose.ui.platform.LocalContext.current).also { webView ->
                webView.settings.javaScriptEnabled = true
                webView.loadDataWithBaseURL(null, htmlCode, "text/html", "UTF-8", null)
            }
            androidx.compose.ui.viewinterop.AndroidView(
                factory = { context ->
                    android.webkit.WebView(context).apply {
                        settings.javaScriptEnabled = true
                        loadDataWithBaseURL(null, htmlCode, "text/html", "UTF-8", null)
                    }
                },
                update = { it.loadDataWithBaseURL(null, htmlCode, "text/html", "UTF-8", null) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// ==================== TAG REFERENCE ====================
@Composable
fun TagReferenceScreen(onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<TagCategory?>(null) }
    val allTags = TagReferenceContent.getAllTags()
    val filtered = allTags.filter { tag ->
        (searchQuery.isEmpty() || tag.tag.contains(searchQuery.lowercase()) || tag.description.contains(searchQuery)) &&
        (selectedCategory == null || tag.category == selectedCategory)
    }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = "HTML Tag রেফারেন্স", onBack = onBack)
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = searchQuery, onValueChange = { searchQuery = it },
                placeholder = { Text("Tag খোঁজো...", color = TextMuted) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentBlue, unfocusedBorderColor = BorderColor,
                    focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary
                )
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(vertical = 4.dp)) {
                item {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { selectedCategory = null },
                        label = { Text("সব") },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AccentBlue.copy(0.3f))
                    )
                }
                items(TagCategory.values()) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                        label = { Text(cat.label) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AccentBlue.copy(0.3f))
                    )
                }
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filtered) { tag -> TagCard(tag) }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun TagCard(tag: TagReference) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "<${tag.tag}>",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = AccentBlue,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    ),
                    modifier = Modifier.weight(1f)
                )
                if (tag.isVoid) Chip("Void", AccentOrange)
                Spacer(Modifier.width(6.dp))
                Chip(tag.category.label, AccentPurple)
            }
            Spacer(Modifier.height(4.dp))
            Text(tag.description, style = MaterialTheme.typography.bodySmall)
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                if (tag.attributes.isNotEmpty()) {
                    Text("Attributes:", style = MaterialTheme.typography.labelMedium.copy(color = AccentGreen))
                    tag.attributes.forEach { attr ->
                        Text("• $attr", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary))
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text("উদাহরণ:", style = MaterialTheme.typography.labelMedium.copy(color = AccentYellow))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1F2D))
                ) {
                    Text(
                        tag.example,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    )
                }
            }
        }
    }
}

// ==================== PROJECTS ====================
@Composable
fun ProjectsScreen(onProjectClick: (String) -> Unit, onBack: () -> Unit) {
    val projects = ProjectContent.getAllProjects()
    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = "প্রজেক্ট", onBack = onBack)
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(projects) { project ->
                ProjectCard(project = project, onClick = { onProjectClick(project.id) })
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ProjectCard(project: Project, onClick: () -> Unit) {
    val (diffColor, diffLabel) = when (project.difficulty) {
        ProjectDifficulty.EASY -> AccentGreen to "সহজ"
        ProjectDifficulty.MEDIUM -> AccentYellow to "মধ্যম"
        ProjectDifficulty.HARD -> AccentOrange to "কঠিন"
    }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Chip(diffLabel, diffColor)
                Chip("${project.estimatedMinutes} মিনিট", AccentBlue)
            }
            Spacer(Modifier.height(8.dp))
            Text(project.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(project.description, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                project.tags.take(3).forEach { tag -> Chip(tag, TextMuted) }
            }
        }
    }
}

@Composable
fun ProjectDetailScreen(projectId: String, onBack: () -> Unit) {
    val project = ProjectContent.getAllProjects().find { it.id == projectId } ?: return
    var showCode by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = project.title, onBack = onBack)
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text(project.description, style = MaterialTheme.typography.bodyLarge) }
            item {
                Text("Hints:", style = MaterialTheme.typography.titleSmall.copy(color = AccentGreen))
                project.hints.forEach { hint ->
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text("• ", color = AccentGreen)
                        Text(hint, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                Button(onClick = { showCode = !showCode }, modifier = Modifier.fillMaxWidth()) {
                    Text(if (showCode) "Starter Code লুকাও" else "Starter Code দেখো")
                }
                if (showCode) {
                    Spacer(Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1F2D)),
                        border = BorderStroke(1.dp, AccentBlue.copy(0.4f))
                    ) {
                        Text(
                            project.starterCode, modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                            )
                        )
                    }
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

// ==================== PROFILE ====================
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onSettings: () -> Unit,
    onAchievements: () -> Unit,
    viewModel: AppViewModel
) {
    val state by viewModel.profileState.collectAsState()
    val allBadges = BadgeContent.getAllBadges()
    val earnedIds = state.badges.map { it.badgeId }.toSet()

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(BgSecondary)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { BackArrowIcon(color = TextPrimary) }
                Text("প্রোফাইল", style = MaterialTheme.typography.titleLarge)
            }
            IconButton(onClick = onSettings) { SettingsGearIcon(color = TextSecondary) }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BgSecondary),
                    border = BorderStroke(1.dp, BorderColor)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(80.dp).clip(CircleShape)
                                .background(AccentBlue.copy(0.2f))
                                .border(2.dp, AccentBlue.copy(0.5f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "L${state.user?.level ?: 1}",
                                style = MaterialTheme.typography.headlineSmall.copy(color = AccentBlue, fontWeight = FontWeight.Bold)
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(state.user?.name ?: "শিক্ষার্থী", style = MaterialTheme.typography.titleLarge)
                        Text(state.levelName, style = MaterialTheme.typography.bodyMedium.copy(color = XpColor))
                        Spacer(Modifier.height(12.dp))
                        XpProgressBar(current = state.user?.xp ?: 0, max = 45000, progress = state.levelProgress)
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ProfileStat("Streak", "${state.user?.streak ?: 0}", StreakColor)
                            ProfileStat("Max Streak", "${state.user?.maxStreak ?: 0}", AccentYellow)
                            ProfileStat("XP", "${state.user?.xp ?: 0}", XpColor)
                            ProfileStat("Badge", "${state.badges.size}", BadgeColor)
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard("পাঠ সম্পন্ন", "${state.user?.totalLessonsCompleted ?: 0}", { BookIcon(color = AccentBlue, size = 20.dp) }, AccentBlue, Modifier.weight(1f))
                    StatCard("Quiz পাস", "${state.user?.totalQuizzesPassed ?: 0}", { StarIcon(color = AccentYellow), }, AccentYellow, Modifier.weight(1f))
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BgSecondary),
                    border = BorderStroke(1.dp, BorderColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val activityMap = state.activity.associate { it.date to it.studyMinutes }
                        ActivityCalendar(activities = activityMap)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Badges (${state.badges.size}/${allBadges.size})", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = onAchievements) { Text("সব দেখো", color = AccentBlue) }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.height(180.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(allBadges.take(8)) { badge ->
                        BadgeCard(badgeInfo = badge, earned = badge.id in earnedIds)
                    }
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ProfileStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleMedium.copy(color = color, fontWeight = FontWeight.Bold))
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}

// ==================== ACHIEVEMENTS ====================
@Composable
fun AchievementsScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    val state by viewModel.profileState.collectAsState()
    val allBadges = BadgeContent.getAllBadges()
    val earnedIds = state.badges.map { it.badgeId }.toSet()

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = "সব অর্জন", onBack = onBack)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(allBadges) { badge ->
                BadgeCard(badgeInfo = badge, earned = badge.id in earnedIds)
            }
        }
    }
}

// ==================== SETTINGS ====================
@Composable
fun SettingsScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    val state by viewModel.profileState.collectAsState()
    var notifEnabled by remember(state.notificationEnabled) { mutableStateOf(state.notificationEnabled) }
    var goalMinutes by remember(state.studyGoalMinutes) { mutableFloatStateOf(state.studyGoalMinutes.toFloat()) }

    Column(modifier = Modifier.fillMaxSize().background(BgPrimary)) {
        TopBar(title = "সেটিংস", onBack = onBack)
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SettingsSection("বিজ্ঞপ্তি") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("বিজ্ঞপ্তি চালু রাখো", style = MaterialTheme.typography.bodyMedium)
                        Switch(
                            checked = notifEnabled,
                            onCheckedChange = {
                                notifEnabled = it
                                viewModel.setNotificationEnabled(it)
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = AccentBlue, checkedTrackColor = AccentBlue.copy(0.4f))
                        )
                    }
                }
            }
            item {
                SettingsSection("প্রতিদিনের লক্ষ্য") {
                    Text("${goalMinutes.toInt()} মিনিট", style = MaterialTheme.typography.bodyMedium.copy(color = AccentBlue))
                    Slider(
                        value = goalMinutes, onValueChange = { goalMinutes = it },
                        valueRange = 5f..120f, steps = 22,
                        onValueChangeFinished = { viewModel.setStudyGoal(goalMinutes.toInt()) },
                        colors = SliderDefaults.colors(thumbColor = AccentBlue, activeTrackColor = AccentBlue)
                    )
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("৫ মিনিট", style = MaterialTheme.typography.labelSmall)
                        Text("১২০ মিনিট", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            item {
                SettingsSection("বিজ্ঞপ্তির সময়") {
                    Text("সকালের বিজ্ঞপ্তি: ${state.morningHour}:${state.morningMinute.toString().padStart(2,'0')}", style = MaterialTheme.typography.bodySmall)
                    Text("সন্ধ্যার বিজ্ঞপ্তি: ${state.eveningHour}:${state.eveningMinute.toString().padStart(2,'0')}", style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(4.dp))
                    Text("বিজ্ঞপ্তির সময় পরিবর্তন করতে ফোনের সেটিংস ব্যবহার করো।", style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall.copy(color = AccentBlue))
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

// ==================== SHARED UI COMPONENTS ====================
@Composable
fun TopBar(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().background(BgSecondary)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) { BackArrowIcon(color = TextPrimary) }
        Text(title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
    }
    HorizontalDivider(color = BorderColor)
}

@Composable
fun BackArrowIcon(color: Color = TextPrimary, size: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawPath(androidx.compose.ui.graphics.Path().apply {
            moveTo(w*0.6f, h*0.2f); lineTo(w*0.25f, h*0.5f); lineTo(w*0.6f, h*0.8f)
        }, color, style = Stroke(w*0.1f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun SettingsGearIcon(color: Color = TextSecondary, size: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawCircle(color = color, radius = w*0.2f, center = androidx.compose.ui.geometry.Offset(w*0.5f, h*0.5f))
        drawCircle(color = color, radius = w*0.38f, center = androidx.compose.ui.geometry.Offset(w*0.5f, h*0.5f), style = Stroke(w*0.12f))
    }
}
