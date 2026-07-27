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
package com.example.hnotes

import com.android.build.api.dsl.CommonExtension
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke

internal fun configureGradleManagedDevices(
    commonExtension: CommonExtension,
) {
    val pixel9 =
        DeviceConfig(
            device = "Pixel 9",
            apiLevel = 36,
            systemImageSource = "google",
        )
    val pixelTablet =
        DeviceConfig(
            device = "Pixel Tablet",
            apiLevel = 36,
            systemImageSource = "google",
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
    val taskName =
        buildString {
            append(device.lowercase().replace(" ", ""))
            append("api")
            append(apiLevel.toString())
            append(systemImageSource.replace("-", ""))
        }
}
