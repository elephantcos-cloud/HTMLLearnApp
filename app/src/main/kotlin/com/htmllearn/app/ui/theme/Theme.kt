package com.htmllearn.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ---- Color Palette ----
val BgPrimary    = Color(0xFF0D1117)
val BgSecondary  = Color(0xFF161B22)
val BgTertiary   = Color(0xFF21262D)
val BorderColor  = Color(0xFF30363D)
val TextPrimary  = Color(0xFFE6EDF3)
val TextSecondary= Color(0xFF8B949E)
val TextMuted    = Color(0xFF484F58)
val AccentBlue   = Color(0xFF58A6FF)
val AccentGreen  = Color(0xFF3FB950)
val AccentOrange = Color(0xFFF78166)
val AccentPurple = Color(0xFFBC8CFF)
val AccentYellow = Color(0xFFE3B341)
val AccentPink   = Color(0xFFF778BA)
val AccentTeal   = Color(0xFF39D353)
val XpColor      = Color(0xFFFFB74D)
val StreakColor  = Color(0xFFFF7043)
val BadgeColor   = Color(0xFFFFD700)

private val DarkColorScheme = darkColorScheme(
    primary          = AccentBlue,
    onPrimary        = Color(0xFF0D1117),
    primaryContainer = Color(0xFF1D3557),
    secondary        = AccentGreen,
    onSecondary      = Color(0xFF0D1117),
    background       = BgPrimary,
    onBackground     = TextPrimary,
    surface          = BgSecondary,
    onSurface        = TextPrimary,
    surfaceVariant   = BgTertiary,
    onSurfaceVariant = TextSecondary,
    outline          = BorderColor,
    error            = AccentOrange,
    onError          = Color(0xFF0D1117),
    tertiary         = AccentPurple,
)

@Composable
fun HTMLLearnTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content
    )
}

val AppTypography = Typography(
    headlineLarge  = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold,   color = TextPrimary),
    headlineMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold,   color = TextPrimary),
    headlineSmall  = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    titleLarge     = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    titleMedium    = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    titleSmall     = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,   color = TextPrimary),
    bodyLarge      = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal,   color = TextPrimary),
    bodyMedium     = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal,   color = TextSecondary),
    bodySmall      = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal,   color = TextMuted),
    labelLarge     = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,   color = TextPrimary),
    labelMedium    = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium,   color = TextSecondary),
    labelSmall     = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,   color = TextMuted),
)
