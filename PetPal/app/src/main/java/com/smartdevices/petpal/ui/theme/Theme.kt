package com.smartdevices.petpal.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.smartdevices.petpal.tools.CustomColorScheme

private val DarkColorScheme = CustomColorScheme(
    primary = prim_dark,
    secondary = accent_dark,
    secondary2 = accent2_dark,
    background = bg_dark,
    blackIcon = black_icon_dark,
    shadow = shadow_dark,
    disabled = disabled_dark,
    g_red = g_red_dark,
    error_red = error_red_dark,
    l_blue = l_blue_dark,
    g_blue = g_blue_dark
)

private val LightColorScheme = CustomColorScheme(
    primary = prim_light,
    secondary = accent_light,
    secondary2 = accent2_light,
    background = bg_light,
    blackIcon = black_icon_light,
    shadow = shadow_light,
    disabled = disabled_light,
    g_red = g_red_light,
    error_red = error_red_light,
    l_blue = l_blue_light,
    g_blue = g_blue_light
)
val LocalCustomColors = compositionLocalOf { LightColorScheme }

@Composable
fun JetpackComposeTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}