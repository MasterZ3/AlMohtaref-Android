package com.almohtaref.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Navy = Color(0xFF1E3A8A)
private val Amber = Color(0xFFD97706)
private val LightScheme = lightColorScheme(
    primary = Navy,
    secondary = Amber,
    background = Color(0xFFF8FAFC),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF172033),
    onSurface = Color(0xFF172033)
)

@Composable
fun AlMohtarefTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightScheme, content = content)
}
