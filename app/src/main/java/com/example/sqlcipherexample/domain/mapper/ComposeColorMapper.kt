package com.example.sqlcipherexample.domain.mapper

import androidx.compose.ui.graphics.Color
import com.example.sqlcipherexample.data.model.ColorState

fun ColorState.toComposeColor() = when (this) {
    ColorState.BLUE -> Color.Blue
    ColorState.RED -> Color.Red
    ColorState.GREEN -> Color.Green
    ColorState.WHITE -> Color.White
    ColorState.MAGENTA -> Color.Magenta
    ColorState.YELLOW -> Color.Yellow
    ColorState.GRAY -> Color.Gray
}
