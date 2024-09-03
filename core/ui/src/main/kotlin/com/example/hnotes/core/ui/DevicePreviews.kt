package com.example.hnotes.core.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@PhonePreviews
@FoldablePreviews
@TabletPreviews
@DesktopPreviews
annotation class DevicePreviews

@Preview(name = "Phone Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PHONE)
@Preview(name = "Phone Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PHONE)
annotation class PhonePreviews

@Preview(name = "Foldable Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.FOLDABLE)
@Preview(name = "Foldable Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.FOLDABLE)
annotation class FoldablePreviews

@Preview(name = "Tablet Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.TABLET)
@Preview(name = "Tablet Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.TABLET)
annotation class TabletPreviews

@Preview(name = "Desktop Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.DESKTOP)
@Preview(name = "Desktop Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.DESKTOP)
annotation class DesktopPreviews