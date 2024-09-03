package com.example.hnotes.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@Composable
fun HNotesBottomAppBar(
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = BottomAppBarDefaults.containerColor,
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding
) {

    BottomAppBar(
        actions = actions,
        modifier = modifier,
        containerColor = containerColor,
        contentPadding = contentPadding
    )

}

@ThemePreviews
@Composable
private fun HNotesBottomAppBarPreview() {
    HNotesTheme {
        HNotesBottomAppBar(
            actions = {

                HNotesIconButton(
                    onClick = {  },
                    icon = {
                        Icon(
                            imageVector = HNotesIcons.Palette,
                            contentDescription = "Localized description"
                        )
                    }
                )

                HNotesIconButton(
                    onClick = { /* doSomething() */ },
                    icon = {
                        Icon(
                            imageVector = HNotesIcons.Image,
                            contentDescription = "Localized description"
                        )
                    }
                )
            }
        )
    }
}