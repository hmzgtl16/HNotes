package com.example.hnotes.feature.settings.impl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.design.component.AppLoadingWheel
import com.example.hnotes.core.design.component.AppTextButton
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.design.theme.LocalBackgroundTheme
import com.example.hnotes.core.design.theme.supportsDynamicTheming
import com.example.hnotes.core.model.Theme
import com.example.hnotes.core.ui.DevicePreviews

@Composable
fun SettingsDialog(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsDialog(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsDialog(
    modifier: Modifier = Modifier,
    uiState: SettingsDialogState,
    onEvent: (SettingsDialogEvent) -> Unit,
    supportDynamicTheme: Boolean = supportsDynamicTheming()
) {
    val windowInfo = LocalWindowInfo.current

    Box(
        modifier = modifier
            .background(
                color = LocalBackgroundTheme.current.color,
                shape = RoundedCornerShape(size = 30.dp)
            ),
        contentAlignment = Alignment.Center,
        content = {

            Column(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.TopCenter)
                    .padding(all = 32.dp)
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.Top
                ),
                content = {

                    Text(
                        text = stringResource(R.string.feature_settings_title),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        color = LocalContentColor.current,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider()

                    when (uiState) {
                        SettingsDialogState.Loading -> {
                            SettingsDialogLoading()
                        }

                        is SettingsDialogState.Success -> {
                            SettingsDialogContent(
                                theme = uiState.theme,
                                onChangeTheme = { onEvent(SettingsDialogEvent.ThemeChanged(it)) },
                                useDynamicTheme = uiState.useDynamicColor,
                                onChangeDynamicTheme = {
                                    onEvent(
                                        SettingsDialogEvent.DynamicColorEnabled(
                                            it
                                        )
                                    )
                                },
                                supportDynamicTheme = supportDynamicTheme
                            )
                        }
                    }

                    HorizontalDivider()

                    AppTextButton(
                        onClick = { onEvent(SettingsDialogEvent.Dismiss) },
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_settings_dismiss_dialog_button_text),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.align(alignment = Alignment.End)
                    )
                }
            )
        }
    )
}

@Composable
fun SettingsDialogLoading() {
    AppLoadingWheel(contentDescription = stringResource(id = R.string.feature_settings_loading))
}

@Composable
private fun SettingsDialogContent(
    theme: Theme,
    onChangeTheme: (Theme) -> Unit,
    useDynamicTheme: Boolean,
    onChangeDynamicTheme: (Boolean) -> Unit,
    supportDynamicTheme: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        content = {

            SettingsDialogThemeSection(
                theme = theme,
                onChangeTheme = onChangeTheme
            )

            AnimatedVisibility(visible = supportDynamicTheme) {
                SettingsDialogThemeSection(
                    useDynamicUiTheme = useDynamicTheme,
                    onChangeDynamicUiTheme = onChangeDynamicTheme
                )
            }
        }
    )
}

@Composable
private fun SettingsDialogThemeSection(
    theme: Theme,
    onChangeTheme: (Theme) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        content = {

            Text(
                text = stringResource(id = R.string.feature_settings_theme),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .selectableGroup(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                ),
                content = {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = theme == Theme.FOLLOW_SYSTEM,
                                role = Role.RadioButton,
                                onClick = { onChangeTheme(Theme.FOLLOW_SYSTEM) },
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            RadioButton(
                                selected = theme == Theme.FOLLOW_SYSTEM,
                                onClick = null
                            )
                            Text(text = stringResource(id = R.string.feature_settings_theme_follow_system))
                        }
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = theme == Theme.DARK,
                                role = Role.RadioButton,
                                onClick = { onChangeTheme(Theme.DARK) },
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            RadioButton(
                                selected = theme == Theme.DARK,
                                onClick = null
                            )
                            Text(text = stringResource(id = R.string.feature_settings_theme_dark))
                        }
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = theme == Theme.LIGHT,
                                role = Role.RadioButton,
                                onClick = { onChangeTheme(Theme.LIGHT) }
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            RadioButton(
                                selected = theme == Theme.LIGHT,
                                onClick = null
                            )
                            Text(text = stringResource(id = R.string.feature_settings_theme_light))
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun SettingsDialogThemeSection(
    useDynamicUiTheme: Boolean,
    onChangeDynamicUiTheme: (Boolean) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = useDynamicUiTheme,
                role = Role.Switch,
                onClick = {
                    onChangeDynamicUiTheme(!useDynamicUiTheme)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Text(
                text = stringResource(id = R.string.feature_settings_dynamic_theme),
                style = MaterialTheme.typography.titleMedium,
                color = LocalContentColor.current
            )

            Switch(
                checked = useDynamicUiTheme,
                onCheckedChange = null
            )
        }
    )
}

@DevicePreviews
@Composable
private fun SettingsDialogLoadingPreview() {
    AppTheme {
        SettingsDialog(
            uiState = SettingsDialogState.Loading,
            onEvent = {}
        )
    }
}

@DevicePreviews
@Composable
private fun SettingsDialogSuccessPreview() {
    AppTheme {
        SettingsDialog(
            uiState = SettingsDialogState.Success(
                theme = Theme.FOLLOW_SYSTEM,
                useDynamicColor = true
            ),
            onEvent = {}
        )
    }
}
