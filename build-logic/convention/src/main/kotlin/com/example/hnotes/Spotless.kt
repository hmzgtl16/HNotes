package com.example.hnotes

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project

internal fun Project.configureSpotlessAndroid(spotlessExtension: SpotlessExtension) {
    configureSpotlessCommon(spotlessExtension)
    spotlessExtension.apply {
        format("xml") {
            target("src/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.xml"), "(<[^!?])")
            endWithNewline()
        }
    }
}

internal fun Project.configureSpotlessJvm(spotlessExtension: SpotlessExtension) {
    configureSpotlessCommon(spotlessExtension)
}

internal fun Project.configureSpotlessRootProject(spotlessExtension: SpotlessExtension) {
    spotlessExtension.apply {
        kotlin {
            target("build-logic/convention/src/**/*.kt")
            ktlint(libs.findVersion("ktlint").get().requiredVersion)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
            endWithNewline()
        }
        format("kts") {
            target("*.kts")
            target("build-logic/*.kts")
            target("build-logic/convention/*.kts")
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
            ktlint(libs.findVersion("ktlint").get().requiredVersion)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
            endWithNewline()
        }
        format("kts") {
            target("*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
            endWithNewline()
        }
    }
}
