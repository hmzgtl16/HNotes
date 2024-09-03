package com.example.hnotes.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun HNotesAnimatedStrikethroughText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    visible: Boolean = true,
    animationSpec: AnimationSpec<Int> = tween(
        durationMillis = 700,
        easing = FastOutLinearInEasing
    )
) {

    var annotatedText by remember {
        mutableStateOf(value = AnnotatedString(text = ""))
    }

    val length = remember {
        Animatable(initialValue = 0, typeConverter = Int.VectorConverter)
    }

    LaunchedEffect(key1 = length.value) {
        annotatedText = text.buildStrikethrough(length = length.value)
    }

    LaunchedEffect(key1 = visible) {
        when {
            visible -> length.animateTo(targetValue = text.length, animationSpec = animationSpec)
            !visible -> length.animateTo(targetValue = 0, animationSpec = animationSpec)
            else -> length.snapTo(targetValue = 0)
        }
    }

    LaunchedEffect(text) {
        when {
            visible && text.length == length.value ->
                annotatedText = text.buildStrikethrough(length = length.value)
            visible && text.length != length.value ->
                length.snapTo(targetValue = text.length)
            else ->
                annotatedText = AnnotatedString(text = text)
        }
    }

    Text(
        text = annotatedText,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

private fun String.buildStrikethrough(length: Int) = buildAnnotatedString {
    append(this@buildStrikethrough)
    addStyle(
        style = SpanStyle(textDecoration = TextDecoration.LineThrough),
        start = 0,
        end = length
    )
}
