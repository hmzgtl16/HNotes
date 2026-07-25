package com.example.hnotes.feature.label.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class LabelNavKey(val noteId: Long): NavKey