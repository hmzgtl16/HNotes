package com.example.hnotes.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BackgroundColorsPreviewParameterProvider : PreviewParameterProvider<List<Color>> {

    override val values: Sequence<List<Color>>
        get() = sequenceOf(
            listOf(
                Color.Unspecified,
                Color(color = 0xFF77172E),
                Color(color = 0xFF692B17),
                Color(color = 0xFF7C4A03),
                Color(color = 0xFF264D3B),
                Color(color = 0xFF0C625D),
                Color(color = 0xFF256377),
                Color(color = 0xFF284255),
                Color(color = 0xFF472E5B),
                Color(color = 0xFF6C394F),
                Color(color = 0xFF4B443A)
            )
        )
}