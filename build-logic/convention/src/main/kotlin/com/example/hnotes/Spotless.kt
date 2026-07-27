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

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotlessAndroid(spotlessExtension: SpotlessExtension) {
    spotlessExtension.apply {
        configureSpotlessCommon(spotlessExtension = this)

        format("xml") {
            target("src/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.xml"), "(<[^!?])")
            endWithNewline()
        }
    }
    extensions.configure<SpotlessExtension> {
    }
}

internal fun Project.configureSpotlessJvm(spotlessExtension: SpotlessExtension) {
    configureSpotlessCommon(spotlessExtension = spotlessExtension)
}

internal fun Project.configureSpotlessRootProject(spotlessExtension: SpotlessExtension) {
    spotlessExtension.apply {
        kotlin {
            target("build-logic/convention/src/**/*.kt")
            ktlint(libs.findVersion("ktlint").get().requiredVersion).editorConfigOverride(
                mapOf("android" to "true"),
            )
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
            endWithNewline()
        }
        kotlinGradle {
            target("*.kts")
            target("build-logic/*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
            endWithNewline()
        }
    }
}

private fun Project.configureSpotlessCommon(spotlessExtension: SpotlessExtension) {
    spotlessExtension.apply {
        kotlin {
            target("src/**/*.kt")
            ktlint(libs.findVersion("ktlint").get().requiredVersion).editorConfigOverride(
                mapOf("android" to "true"),
            )
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
            endWithNewline()
        }
        kotlinGradle {
            target("*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
            endWithNewline()
        }
    }
}
