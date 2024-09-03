package com.example.hnotes.feature.settings

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.designsystem.component.HNotesLoadingWheel
import com.example.hnotes.core.designsystem.component.HNotesTextButton
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.designsystem.theme.LocalBackgroundTheme
import com.example.hnotes.core.designsystem.theme.supportsDynamicTheming
import com.example.hnotes.core.model.data.UiTheme
import com.example.hnotes.core.ui.DevicePreviews
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsDialog(
        uiState = uiState,
        onChangeUiTheme = viewModel::updateUiTheme,
        onChangeDynamicUiTheme = viewModel::updateDynamicUiTheme,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsDialog(
    uiState: SettingsUiState,
    onChangeUiTheme: (UiTheme) -> Unit,
    onChangeDynamicUiTheme: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    supportDynamicUiTheme: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
) {

    val configuration = LocalConfiguration.current

    Box(
        modifier = modifier
            .background(
                color = LocalBackgroundTheme.current.color,
                shape = RoundedCornerShape(size = 30.dp)
            )
            .sizeIn(
                maxWidth = configuration.screenWidthDp.dp - 80.dp,
                maxHeight = configuration.screenHeightDp.dp - 80.dp
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
                        SettingsUiState.Loading -> {
                            SettingsDialogLoading()
                        }

                        is SettingsUiState.Success -> {
                            SettingsDialogContent(
                                uiTheme = uiState.uiTheme,
                                onChangeUiTheme = onChangeUiTheme,
                                useDynamicUiTheme = uiState.useDynamicUiTheme,
                                onChangeDynamicUiTheme = onChangeDynamicUiTheme,
                                supportDynamicUiTheme = supportDynamicUiTheme
                            )
                        }
                    }

                    HorizontalDivider()

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.CenterHorizontally,
                        ),
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                        content = {

                            val context = LocalContext.current
                            val uriHandler = LocalUriHandler.current

                            HNotesTextButton(
                                onClick = { uriHandler.openUri(uri = PRIVACY_POLICY_URL) },
                                text = {
                                    Text(text = stringResource(id = R.string.feature_settings_privacy_policy))
                                }
                            )

                            HNotesTextButton(
                                onClick = {
                                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                                },
                                text = {
                                    Text(text = stringResource(id = R.string.feature_settings_licenses))
                                }
                            )

                            HNotesTextButton(
                                onClick = { uriHandler.openUri(uri = FEEDBACK_URL) },
                                text = {
                                    Text(text = stringResource(id = R.string.feature_settings_feedback))
                                }
                            )
                        }
                    )

                    HNotesTextButton(
                        onClick = onDismiss,
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
    HNotesLoadingWheel(contentDescription = stringResource(id = R.string.feature_settings_loading))
}

@Composable
private fun SettingsDialogContent(
    uiTheme: UiTheme,
    onChangeUiTheme: (UiTheme) -> Unit,
    useDynamicUiTheme: Boolean,
    onChangeDynamicUiTheme: (Boolean) -> Unit,
    supportDynamicUiTheme: Boolean,
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
                uiTheme = uiTheme,
                onChangeUiTheme = onChangeUiTheme
            )

            AnimatedVisibility(visible = supportDynamicUiTheme) {
                SettingsDialogThemeSection(
                    useDynamicUiTheme = useDynamicUiTheme,
                    onChangeDynamicUiTheme = onChangeDynamicUiTheme
                )
            }
        }
    )
}

@Composable
private fun SettingsDialogThemeSection(
    uiTheme: UiTheme,
    onChangeUiTheme: (uiTheme: UiTheme) -> Unit,
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
                                selected = uiTheme == UiTheme.FOLLOW_SYSTEM,
                                role = Role.RadioButton,
                                onClick = { onChangeUiTheme(UiTheme.FOLLOW_SYSTEM) },
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            RadioButton(
                                selected = uiTheme == UiTheme.FOLLOW_SYSTEM,
                                onClick = null
                            )
                            Text(text = stringResource(id = R.string.feature_settings_theme_follow_system))
                        }
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = uiTheme == UiTheme.DARK,
                                role = Role.RadioButton,
                                onClick = { onChangeUiTheme(UiTheme.DARK) },
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            RadioButton(
                                selected = uiTheme == UiTheme.DARK,
                                onClick = null
                            )
                            Text(text = stringResource(id = R.string.feature_settings_theme_dark))
                        }
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = uiTheme == UiTheme.LIGHT,
                                role = Role.RadioButton,
                                onClick = { onChangeUiTheme(UiTheme.LIGHT) }
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            RadioButton(
                                selected = uiTheme == UiTheme.LIGHT,
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
    HNotesTheme {
        SettingsDialog(
            uiState = SettingsUiState.Loading,
            onChangeUiTheme = {},
            onChangeDynamicUiTheme = {},
            onDismiss = {}
        )
    }
}

@DevicePreviews
@Composable
private fun SettingsDialogSuccessPreview() {
    HNotesTheme {
        SettingsDialog(
            uiState = SettingsUiState.Success(
                uiTheme = UiTheme.FOLLOW_SYSTEM,
                useDynamicUiTheme = true
            ),
            onChangeUiTheme = {},
            onChangeDynamicUiTheme = {},
            supportDynamicUiTheme = true,
            onDismiss = {}
        )
    }
}

private const val PRIVACY_POLICY_URL = ""
private const val FEEDBACK_URL = "https://forms.gle/MVgsJMhhbRBQLfyX7"
