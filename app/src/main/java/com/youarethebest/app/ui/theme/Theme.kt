package com.youarethebest.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val WarmGold = Color(0xFFFFB347)
private val DeepPurple = Color(0xFF7C4DFF)
private val SoftPink = Color(0xFFFF6B9D)
private val BrightCoral = Color(0xFFFF6F61)
private val SunshineYellow = Color(0xFFFFD93D)
private val SkyBlue = Color(0xFF6EC6FF)

private val LightColors = lightColorScheme(
    primary = DeepPurple,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE7F6),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = SoftPink,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFD8E4),
    onSecondaryContainer = Color(0xFF31111D),
    tertiary = WarmGold,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFF3E2723),
    background = Color(0xFFFFF8F0),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFF8F0),
    onSurface = Color(0xFF1C1B1F),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFFFB1C8),
    onSecondary = Color(0xFF4E1131),
    secondaryContainer = Color(0xFF6B2C47),
    onSecondaryContainer = Color(0xFFFFD8E4),
    tertiary = Color(0xFFFFCC80),
    onTertiary = Color(0xFF4E3415),
    tertiaryContainer = Color(0xFF6B4B29),
    onTertiaryContainer = Color(0xFFFFE0B2),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
)

@Composable
fun YouAreTheBestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
