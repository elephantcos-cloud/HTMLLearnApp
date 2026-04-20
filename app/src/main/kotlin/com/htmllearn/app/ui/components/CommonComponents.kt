package com.htmllearn.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.htmllearn.app.content.BadgeInfo
import com.htmllearn.app.ui.theme.*

// ---- XP Progress Bar ----
@Composable
fun XpProgressBar(
    current: Int,
    max: Int,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$current XP", style = MaterialTheme.typography.labelSmall, color = XpColor)
            Text("$max XP", style = MaterialTheme.typography.labelSmall, color = TextMuted)
        }
        Spacer(Modifier.height(4.dp))
        val animProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(1000, easing = EaseOutCubic),
            label = "xp_progress"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(BgTertiary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animProgress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.horizontalGradient(listOf(XpColor, AccentOrange))
                    )
            )
        }
    }
}

// ---- Stat Card ----
@Composable
fun StatCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    color: Color = AccentBlue,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) { icon() }
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.titleLarge.copy(color = color, fontWeight = FontWeight.Bold))
            Text(title, style = MaterialTheme.typography.labelSmall)
        }
    }
}

// ---- Lesson Card ----
@Composable
fun LessonCard(
    title: String,
    subtitle: String,
    isCompleted: Boolean,
    isLocked: Boolean,
    estimatedMinutes: Int,
    difficulty: String,
    difficultyColor: Color,
    chapterColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !isLocked) { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(
            1.dp,
            if (isCompleted) AccentGreen.copy(alpha = 0.5f)
            else if (isLocked) BorderColor.copy(alpha = 0.4f)
            else BorderColor
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCompleted) AccentGreen.copy(alpha = 0.2f)
                        else if (isLocked) TextMuted.copy(alpha = 0.1f)
                        else chapterColor.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    CheckIcon(color = AccentGreen)
                } else if (isLocked) {
                    LockIcon(color = TextMuted)
                } else {
                    BookIcon(color = chapterColor)
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = if (isLocked) TextMuted else TextPrimary
                    ),
                    maxLines = 1
                )
                Text(subtitle, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Chip(text = "$estimatedMinutes মিনিট", color = AccentBlue)
                    Chip(text = difficulty, color = difficultyColor)
                }
            }
            if (isCompleted) {
                Text("+50 XP", style = MaterialTheme.typography.labelSmall.copy(color = AccentGreen))
            }
        }
    }
}

// ---- Chip ----
@Composable
fun Chip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall.copy(color = color))
    }
}

// ---- Badge Card ----
@Composable
fun BadgeCard(badgeInfo: BadgeInfo, earned: Boolean, modifier: Modifier = Modifier) {
    val color = Color(badgeInfo.colorHex)
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (earned) BgSecondary else BgTertiary
        ),
        border = BorderStroke(1.dp, if (earned) color.copy(alpha = 0.5f) else BorderColor)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .alpha(if (earned) 1f else 0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                BadgeIcon(color = color, size = 28.dp)
            }
            Spacer(Modifier.height(6.dp))
            Text(
                badgeInfo.title,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (earned) color else TextMuted
                ),
                maxLines = 2
            )
        }
    }
}

// ---- Activity Calendar ----
@Composable
fun ActivityCalendar(
    activities: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    val maxMinutes = activities.values.maxOrNull()?.coerceAtLeast(1) ?: 1
    Column(modifier = modifier) {
        Text(
            "গত ৩০ দিনের কার্যক্রম",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(Modifier.height(8.dp))
        val sortedDates = activities.keys.sorted().takeLast(30)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            sortedDates.forEach { date ->
                val minutes = activities[date] ?: 0
                val intensity = (minutes.toFloat() / maxMinutes).coerceIn(0f, 1f)
                val color = when {
                    intensity == 0f -> BgTertiary
                    intensity < 0.25f -> AccentGreen.copy(alpha = 0.3f)
                    intensity < 0.5f -> AccentGreen.copy(alpha = 0.5f)
                    intensity < 0.75f -> AccentGreen.copy(alpha = 0.75f)
                    else -> AccentGreen
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("কম", style = MaterialTheme.typography.labelSmall)
            Text("বেশি", style = MaterialTheme.typography.labelSmall)
        }
    }
}

// ---- Circular Progress ----
@Composable
fun CircularProgress(
    progress: Float,
    size: Dp = 80.dp,
    strokeWidth: Dp = 8.dp,
    color: Color = AccentBlue,
    backgroundColor: Color = BgTertiary,
    content: @Composable BoxScope.() -> Unit
) {
    val animProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = EaseOutCubic),
        label = "circle_progress"
    )
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = 360f * animProgress
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        content()
    }
}

// ---- Content Block for Lesson ----
@Composable
fun ContentBlockView(type: com.htmllearn.app.content.ContentType, text: String) {
    val (bgColor, borderColor, prefixText) = when (type) {
        com.htmllearn.app.content.ContentType.THEORY -> Triple(BgTertiary, BorderColor, "")
        com.htmllearn.app.content.ContentType.CODE_EXAMPLE -> Triple(Color(0xFF0D2137), AccentBlue.copy(0.5f), "")
        com.htmllearn.app.content.ContentType.NOTE -> Triple(AccentBlue.copy(0.08f), AccentBlue.copy(0.4f), "নোট: ")
        com.htmllearn.app.content.ContentType.WARNING -> Triple(AccentOrange.copy(0.08f), AccentOrange.copy(0.4f), "সতর্কতা: ")
        com.htmllearn.app.content.ContentType.TIP -> Triple(AccentGreen.copy(0.08f), AccentGreen.copy(0.4f), "টিপস: ")
        com.htmllearn.app.content.ContentType.DEFINITION -> Triple(AccentPurple.copy(0.08f), AccentPurple.copy(0.4f), "সংজ্ঞা: ")
    }
    val textColor = when (type) {
        com.htmllearn.app.content.ContentType.NOTE -> AccentBlue
        com.htmllearn.app.content.ContentType.WARNING -> AccentOrange
        com.htmllearn.app.content.ContentType.TIP -> AccentGreen
        com.htmllearn.app.content.ContentType.DEFINITION -> AccentPurple
        else -> TextPrimary
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                if (prefixText.isNotEmpty()) {
                    withStyle(SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
                        append(prefixText)
                    }
                }
                withStyle(SpanStyle(color = if (prefixText.isEmpty()) TextPrimary else TextSecondary)) {
                    append(text)
                }
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// ---- SVG Icons (Vector Drawables as Composables) ----
@Composable
fun CheckIcon(color: Color = AccentGreen, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        drawPath(
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.2f, h * 0.5f)
                lineTo(w * 0.45f, h * 0.75f)
                lineTo(w * 0.8f, h * 0.25f)
            },
            color = color,
            style = Stroke(width = w * 0.12f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun LockIcon(color: Color = TextMuted, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        drawRoundRect(
            color = color,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.25f, h * 0.45f),
            size = androidx.compose.ui.geometry.Size(w * 0.5f, h * 0.45f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f),
            style = Stroke(width = w * 0.1f)
        )
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.3f, h * 0.12f),
            size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.4f),
            style = Stroke(width = w * 0.1f)
        )
    }
}

@Composable
fun BookIcon(color: Color = AccentBlue, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        drawRoundRect(
            color = color.copy(alpha = 0.3f),
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.1f),
            size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.8f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f)
        )
        for (i in 0..2) {
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(w * 0.3f, h * (0.3f + i * 0.15f)),
                end = androidx.compose.ui.geometry.Offset(w * 0.7f, h * (0.3f + i * 0.15f)),
                strokeWidth = w * 0.07f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun BadgeIcon(color: Color = BadgeColor, size: Dp = 28.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        drawCircle(color = color.copy(alpha = 0.3f), radius = w * 0.4f)
        drawCircle(color = color, radius = w * 0.4f, style = Stroke(w * 0.08f))
        drawPath(
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.5f, h * 0.2f)
                lineTo(w * 0.62f, h * 0.42f)
                lineTo(w * 0.85f, h * 0.45f)
                lineTo(w * 0.68f, h * 0.62f)
                lineTo(w * 0.73f, h * 0.85f)
                lineTo(w * 0.5f, h * 0.73f)
                lineTo(w * 0.27f, h * 0.85f)
                lineTo(w * 0.32f, h * 0.62f)
                lineTo(w * 0.15f, h * 0.45f)
                lineTo(w * 0.38f, h * 0.42f)
                close()
            },
            color = color
        )
    }
}

@Composable
fun StarIcon(color: Color = AccentYellow, size: Dp = 16.dp, filled: Boolean = true) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.5f, h * 0.1f)
            lineTo(w * 0.62f, h * 0.38f)
            lineTo(w * 0.93f, h * 0.4f)
            lineTo(w * 0.72f, h * 0.6f)
            lineTo(w * 0.79f, h * 0.88f)
            lineTo(w * 0.5f, h * 0.74f)
            lineTo(w * 0.21f, h * 0.88f)
            lineTo(w * 0.28f, h * 0.6f)
            lineTo(w * 0.07f, h * 0.4f)
            lineTo(w * 0.38f, h * 0.38f)
            close()
        }
        if (filled) drawPath(path, color)
        else drawPath(path, color, style = Stroke(w * 0.08f))
    }
}
