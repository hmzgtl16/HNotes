package com.example.hnotes.core.designsystem.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State
import coil.compose.rememberAsyncImagePainter
import com.example.hnotes.core.designsystem.theme.LocalTintTheme

@Composable
fun HNotesAsyncImage(
    image: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val iconTint = LocalTintTheme.current.iconTint
    val isLocalInspection = LocalInspectionMode.current

    var isLoading by remember { mutableStateOf(value = true) }
    var isError by remember { mutableStateOf(value = false) }

    val imageLoader = rememberAsyncImagePainter(
        model = Uri.parse("file:///data/user/0/com.example.hnotes.demo.debug/files/1721604429735.jpeg"),
        onState = {
            isLoading = it is State.Loading
            isError = it is State.Error
        }
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            if (isLoading && !isLocalInspection) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            if (!isError && !isLocalInspection) {
                Image(
                    contentScale = ContentScale.FillBounds,
                    painter = imageLoader,
                    contentDescription = contentDescription,
                    colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null
                )
            }
        },
    )
}