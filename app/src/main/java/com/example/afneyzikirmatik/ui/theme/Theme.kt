package com.example.afneyzikirmatik.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldPrimaryVariant,
    onPrimaryContainer = Color.White,
    secondary = EmeraldSecondary,
    onSecondary = Color.White,
    secondaryContainer = GoldAccentVariant,
    onSecondaryContainer = TextPrimaryDark,
    tertiary = GoldAccent,
    onTertiary = TextPrimaryDark,
    error = ErrorColor,
    onError = Color.White,
    errorContainer = ErrorColor.copy(alpha = 0.1f),
    onErrorContainer = ErrorColor,
    background = SoftBackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SoftSurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SoftSurfaceDark.copy(alpha = 0.8f),
    onSurfaceVariant = TextSecondaryDark,
    outline = TextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldPrimaryVariant,
    onPrimaryContainer = Color.White,
    secondary = EmeraldSecondary,
    onSecondary = Color.White,
    secondaryContainer = GoldAccentVariant,
    onSecondaryContainer = TextPrimaryLight,
    tertiary = GoldAccent,
    onTertiary = TextPrimaryLight,
    error = ErrorColor,
    onError = Color.White,
    errorContainer = ErrorColor.copy(alpha = 0.1f),
    onErrorContainer = ErrorColor,
    background = SoftBackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SoftSurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SoftSurfaceLight.copy(alpha = 0.9f),
    onSurfaceVariant = TextSecondaryLight,
    outline = TextSecondaryLight
)

@Composable
fun AfneyZikirmatikTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}