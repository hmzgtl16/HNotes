package com.example.hnotes

import com.android.build.api.dsl.CommonExtension
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke

internal fun configureGradleManagedDevices(
    commonExtension: CommonExtension,
) {
    val pixel9 = DeviceConfig(
        device = "Pixel 9",
        apiLevel = 36,
        systemImageSource = "google"
    )
    val pixelTablet = DeviceConfig(
        device = "Pixel Tablet",
        apiLevel = 36,
        systemImageSource = "google"
    )

    commonExtension.testOptions.apply {
        @Suppress("UnstableApiUsage")
        managedDevices {
            localDevices {
                maybeCreate(pixel9.taskName).apply {
                    device = pixel9.device
                    apiLevel = pixel9.apiLevel
                    systemImageSource = pixel9.systemImageSource
                }
                maybeCreate(pixelTablet.taskName).apply {
                    device = pixelTablet.device
                    apiLevel = pixelTablet.apiLevel
                    systemImageSource = pixelTablet.systemImageSource
                }
            }
            groups {
                maybeCreate("phoneAndTablet").apply {
                    targetDevices.add(localDevices[pixel9.taskName])
                    targetDevices.add(localDevices[pixelTablet.taskName])
                }
            }
        }
    }
}

private data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
) {
    val taskName = buildString {
        append(device.lowercase().replace(" ", ""))
        append("api")
        append(apiLevel.toString())
        append(systemImageSource.replace("-", ""))
    }
}