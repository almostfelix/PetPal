package com.smartdevices.petpal.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.smartdevices.petpal.tools.CustomColorScheme

private val DarkColorScheme = CustomColorScheme(
    primary = prim_dark,
    secondary = prim_sec_dark,
    background = bg_dark,
    blackIcon = black_icon_dark,
    shadow = shadow_dark,
    disabled = disabled_dark,
    g_red = g_red_dark,
    error_red = error_red_dark,
    l_blue = l_blue_dark,
    g_blue = g_blue_dark,
    secondary2 = accent2_dark,
)

private val LightColorScheme = CustomColorScheme(
    primary = prim_dark,
    secondary = prim_sec_dark,
    background = bg_dark,
    blackIcon = black_icon_dark,
    shadow = shadow_dark,
    disabled = disabled_dark,
    g_red = g_red_dark,
    error_red = error_red_dark,
    l_blue = l_blue_dark,
    g_blue = g_blue_dark,
    secondary2 = accent2_dark,
)
private val LightColorScheme2 = lightColorScheme(
    primary = prim_dark,
    secondary = prim_sec_dark,
    background = bg_dark,
    surface = bg_dark,
    onPrimary = black_icon_dark,
    onSecondary = shadow_dark,
    onBackground = disabled_dark,
    onSurface = g_red_dark,
    error = error_red_dark,
    onError = l_blue_dark
)

@Composable
fun JetpackComposeTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
        colorScheme = LightColorScheme2,
        typography = Typography,
        content = content
    )
}