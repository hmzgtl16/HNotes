/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.hnotes.feature.label.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.design.component.AppLoadingWheel
import com.example.hnotes.core.design.component.AppTextButton
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.design.theme.LocalBackgroundTheme
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.ui.LabelCard

@Composable
fun LabelDialog(modifier: Modifier = Modifier, viewModel: LabelViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LabelDialog(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDialog(modifier: Modifier = Modifier, uiState: LabelsUiState, onEvent: (LabelsDialogEvent) -> Unit) {
    Box(
        modifier =
            modifier
                .background(
                    color = LocalBackgroundTheme.current.color,
                    shape = RoundedCornerShape(size = 30.dp),
                ),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.Top,
                    ),
                content = {
                    Text(
                        text = stringResource(R.string.feature_label_impl_labels_title),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        color = LocalContentColor.current,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    HorizontalDivider()

                    when (uiState) {
                        LabelsUiState.Loading -> {
                            LabelDialogLoading(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),
                            )
                        }

                        is LabelsUiState.Success -> {
                            if (uiState.allLabels.isEmpty()) {
                                LabelDialogEmpty(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),
                                )
                            } else {
                                LabelsDialogContent(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),
                                    allLabels = uiState.allLabels,
                                    selectedLabels = uiState.selectedLabels,
                                    onToggle = {
                                        onEvent(
                                            LabelsDialogEvent.ToggleLabelSelection(it),
                                        )
                                    },
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                        content = {
                            AppTextButton(
                                onClick = { onEvent(LabelsDialogEvent.Dismiss) },
                                text = {
                                    Text(
                                        text =
                                            stringResource(
                                                id = R.string.feature_label_impl_done,
                                            ),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                },
                            )
                        },
                    )
                },
            )
        },
    )
}

@Composable
fun LabelDialogLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            AppLoadingWheel(
                contentDescription = stringResource(R.string.feature_label_impl_loading_labels),
            )
        },
    )
}

@Composable
fun LabelDialogEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = stringResource(R.string.feature_label_impl_labels_empty),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
    )
}

@Composable
fun LabelsDialogContent(modifier: Modifier = Modifier, allLabels: List<Label>, selectedLabels: List<Label>, onToggle: (Label) -> Unit) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically,
            ),
        content = {
            items(
                items = allLabels,
                key = { it.id },
                itemContent = { label ->
                    LabelCard(
                        label = label,
                        isSelected = selectedLabels.any { it.id == label.id },
                        onToggle = { onToggle(label) },
                    )
                },
            )
        },
    )
}

@PreviewScreenSizes
@Composable
private fun LabelDialogLoadingPreview() {
    AppTheme {
        LabelDialog(
            uiState = LabelsUiState.Loading,
            onEvent = {},
        )
    }
}

@PreviewScreenSizes
@Composable
private fun LabelsDialogEmptyPreview() {
    AppTheme {
        LabelDialog(
            uiState = LabelsUiState.Success(allLabels = emptyList()),
            onEvent = {},
        )
    }
}

@PreviewScreenSizes
@Composable
private fun LabelsDialogContentPreview() {
    AppTheme {
        LabelDialog(
            uiState =
                LabelsUiState.Success(
                    allLabels =
                        listOf(
                            Label(id = 1, name = "Label 1"),
                            Label(id = 2, name = "Label 2"),
                            Label(id = 3, name = "Label 3"),
                        ),
                    selectedLabels =
                        listOf(
                            Label(id = 1, name = "Label 1"),
                        ),
                ),
            onEvent = {},
        )
    }
}
