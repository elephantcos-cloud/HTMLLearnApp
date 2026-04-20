package com.htmllearn.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.htmllearn.app.content.BadgeContent
import com.htmllearn.app.ui.components.*
import com.htmllearn.app.ui.theme.*
import com.htmllearn.app.viewmodel.AppViewModel

@Composable
fun DashboardScreen(
    onNavigateToLessons: () -> Unit,
    onNavigateToEditor: () -> Unit,
    onNavigateToReference: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: AppViewModel
) {
    val state by viewModel.dashboardState.collectAsState()
    val badgeUnlocked by viewModel.badgeUnlocked.collectAsState()
    val levelUp by viewModel.levelUp.collectAsState()

    // Badge unlocked dialog
    badgeUnlocked?.let { badge ->
        AlertDialog(
            onDismissRequest = { viewModel.clearBadgeUnlocked() },
            containerColor = BgSecondary,
            title = {
                Text("নতুন Badge অর্জন!", style = MaterialTheme.typography.titleLarge.copy(color = AccentYellow))
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BadgeIcon(color = Color(badge.colorHex), size = 64.dp)
                    Spacer(Modifier.height(12.dp))
                    Text(badge.title, style = MaterialTheme.typography.titleMedium)
                    Text(badge.description, style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.clearBadgeUnlocked() }) { Text("দারুণ!") }
            }
        )
    }

    // Level up dialog
    levelUp?.let { level ->
        AlertDialog(
            onDismissRequest = { viewModel.clearLevelUp() },
            containerColor = BgSecondary,
            title = { Text("Level Up!", style = MaterialTheme.typography.titleLarge.copy(color = AccentYellow)) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Level $level", style = MaterialTheme.typography.headlineMedium.copy(color = AccentYellow, fontWeight = FontWeight.Bold))
                    Text(viewModel.repository.getLevelName(level), style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("অভিনন্দন! তুমি নতুন স্তরে পৌঁছেছো!", style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.clearLevelUp() }) { Text("চালিয়ে যাও!") }
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(BgPrimary),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "স্বাগতম, ${state.user?.name ?: "শিক্ষার্থী"}!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text("আজও HTML শিখতে থাকো", style = MaterialTheme.typography.bodyMedium)
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(AccentBlue.copy(alpha = 0.2f))
                        .clickable { onNavigateToProfile() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "L${state.user?.level ?: 1}",
                        style = MaterialTheme.typography.labelLarge.copy(color = AccentBlue, fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // Level & XP Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BgSecondary),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Level ${state.user?.level ?: 1} — ${state.levelName}",
                                style = MaterialTheme.typography.titleMedium.copy(color = XpColor)
                            )
                            Text(
                                "${state.user?.xp ?: 0} XP অর্জিত",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        CircularProgress(
                            progress = state.levelProgress,
                            size = 64.dp,
                            color = XpColor
                        ) {
                            Text(
                                "${(state.levelProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall.copy(color = XpColor)
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    XpProgressBar(
                        current = state.user?.xp ?: 0,
                        max = state.nextLevelXp,
                        progress = state.levelProgress
                    )
                }
            }
        }

        // Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Streak",
                    value = "${state.user?.streak ?: 0}",
                    icon = { StreakFlameIcon(color = StreakColor) },
                    color = StreakColor,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "পাঠ",
                    value = "${state.completedCount}/${state.totalLessons}",
                    icon = { BookIcon(color = AccentBlue) },
                    color = AccentBlue,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "আজকের সময়",
                    value = "${state.todayMinutes}m",
                    icon = { ClockIcon(color = AccentGreen) },
                    color = AccentGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Progress overview
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BgSecondary),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("সামগ্রিক অগ্রগতি", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    val overallProgress = if (state.totalLessons > 0) state.completedCount.toFloat() / state.totalLessons else 0f
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(BgTertiary)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(overallProgress)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Brush.horizontalGradient(listOf(AccentBlue, AccentPurple)))
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "${(overallProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.labelLarge.copy(color = AccentBlue)
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${state.completedCount} টি সম্পন্ন, ${state.totalLessons - state.completedCount} টি বাকি",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        // Quick Actions
        item {
            Text("দ্রুত শুরু করো", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard("পাঠ্যক্রম", AccentBlue, { BookIcon(color = AccentBlue, size = 28.dp) }, Modifier.weight(1f)) { onNavigateToLessons() }
                QuickActionCard("কোড এডিটর", AccentGreen, { CodeIcon(color = AccentGreen, size = 28.dp) }, Modifier.weight(1f)) { onNavigateToEditor() }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard("Tag রেফারেন্স", AccentPurple, { TagIcon(color = AccentPurple, size = 28.dp) }, Modifier.weight(1f)) { onNavigateToReference() }
                QuickActionCard("প্রজেক্ট", AccentOrange, { ProjectIcon(color = AccentOrange, size = 28.dp) }, Modifier.weight(1f)) { onNavigateToProjects() }
            }
        }

        // Recent badges
        if (state.recentBadges.isNotEmpty()) {
            item {
                Text("সাম্প্রতিক Badge", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.recentBadges.take(4).forEach { badge ->
                        val info = BadgeContent.getBadgeById(badge.badgeId)
                        if (info != null) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Color(info.colorHex).copy(alpha = 0.2f))
                                    .border(1.dp, Color(info.colorHex).copy(alpha = 0.5f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                BadgeIcon(color = Color(info.colorHex), size = 28.dp)
                            }
                        }
                    }
                }
            }
        }

        // Activity calendar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BgSecondary),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val activityMap = state.recentActivity.associate { it.date to it.studyMinutes }
                    ActivityCalendar(activities = activityMap)
                }
            }
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun QuickActionCard(
    title: String, color: Color,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgSecondary),
        border = BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) { icon() }
            Spacer(Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelMedium.copy(color = color))
        }
    }
}

// Additional icon composables
@Composable
fun StreakFlameIcon(color: Color = StreakColor, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawOval(color = color.copy(0.4f),
            topLeft = androidx.compose.ui.geometry.Offset(w*0.2f, h*0.5f),
            size = androidx.compose.ui.geometry.Size(w*0.6f, h*0.45f))
        drawPath(androidx.compose.ui.graphics.Path().apply {
            moveTo(w*0.5f, h*0.05f); cubicTo(w*0.8f, h*0.2f, w*0.85f, h*0.5f, w*0.7f, h*0.75f)
            cubicTo(w*0.6f, h*0.55f, w*0.35f, h*0.55f, w*0.3f, h*0.75f)
            cubicTo(w*0.15f, h*0.5f, w*0.2f, h*0.2f, w*0.5f, h*0.05f); close()
        }, color = color)
    }
}

@Composable
fun ClockIcon(color: Color = AccentGreen, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawCircle(color = color, radius = w*0.42f, style = Stroke(w*0.1f))
        drawLine(color, androidx.compose.ui.geometry.Offset(w*0.5f, h*0.5f), androidx.compose.ui.geometry.Offset(w*0.5f, h*0.2f), w*0.08f, cap = StrokeCap.Round)
        drawLine(color, androidx.compose.ui.geometry.Offset(w*0.5f, h*0.5f), androidx.compose.ui.geometry.Offset(w*0.72f, h*0.58f), w*0.08f, cap = StrokeCap.Round)
    }
}

@Composable
fun CodeIcon(color: Color = AccentGreen, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawPath(androidx.compose.ui.graphics.Path().apply {
            moveTo(w*0.35f, h*0.25f); lineTo(w*0.15f, h*0.5f); lineTo(w*0.35f, h*0.75f)
        }, color, style = Stroke(w*0.1f, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawPath(androidx.compose.ui.graphics.Path().apply {
            moveTo(w*0.65f, h*0.25f); lineTo(w*0.85f, h*0.5f); lineTo(w*0.65f, h*0.75f)
        }, color, style = Stroke(w*0.1f, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawLine(color, androidx.compose.ui.geometry.Offset(w*0.55f, h*0.2f), androidx.compose.ui.geometry.Offset(w*0.45f, h*0.8f), w*0.08f, cap = StrokeCap.Round)
    }
}

@Composable
fun TagIcon(color: Color = AccentPurple, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawRoundRect(color, topLeft = androidx.compose.ui.geometry.Offset(w*0.05f, h*0.25f),
            size = androidx.compose.ui.geometry.Size(w*0.9f, h*0.5f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w*0.1f),
            style = Stroke(w*0.08f))
        drawText(color, w, h)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawText(color: Color, w: Float, h: Float) {
    drawLine(color, androidx.compose.ui.geometry.Offset(w*0.25f, h*0.4f), androidx.compose.ui.geometry.Offset(w*0.25f, h*0.6f), w*0.07f, cap = StrokeCap.Round)
    drawLine(color, androidx.compose.ui.geometry.Offset(w*0.35f, h*0.4f), androidx.compose.ui.geometry.Offset(w*0.75f, h*0.4f), w*0.07f, cap = StrokeCap.Round)
}

@Composable
fun ProjectIcon(color: Color = AccentOrange, size: Dp = 20.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width; val h = this.size.height
        drawRoundRect(color.copy(0.3f), topLeft = androidx.compose.ui.geometry.Offset(w*0.1f, h*0.3f),
            size = androidx.compose.ui.geometry.Size(w*0.8f, h*0.6f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w*0.1f))
        drawPath(androidx.compose.ui.graphics.Path().apply {
            moveTo(w*0.1f, h*0.35f); lineTo(w*0.1f, h*0.2f)
            lineTo(w*0.45f, h*0.2f); lineTo(w*0.55f, h*0.35f)
        }, color, style = Stroke(w*0.08f, join = StrokeJoin.Round))
        drawRoundRect(color, topLeft = androidx.compose.ui.geometry.Offset(w*0.1f, h*0.3f),
            size = androidx.compose.ui.geometry.Size(w*0.8f, h*0.6f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w*0.1f),
            style = Stroke(w*0.08f))
    }
}
