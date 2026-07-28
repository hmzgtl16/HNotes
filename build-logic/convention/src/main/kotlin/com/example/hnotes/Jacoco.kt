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

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import com.android.build.api.variant.SourceDirectories
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.util.Locale

private val coverageExclusions =
    listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*_Hilt*.class",
        "**/Hilt_*.class",
    )

private fun String.capitalize() =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

internal fun Project.configureJacoco(
    commonExtension: CommonExtension,
) {
    commonExtension.buildTypes.named("debug") {
        enableAndroidTestCoverage = true
        enableUnitTestCoverage = true
    }

    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("org-jacoco").get().toString()
    }
}

internal fun Project.configureJacoco(
    androidComponentsExtension: AndroidComponentsExtension<*, *, *>,
) {
    androidComponentsExtension.onVariants { variant ->
        val myObjFactory = project.objects
        val buildDir = layout.buildDirectory.get().asFile
        val allJars: ListProperty<RegularFile> = myObjFactory.listProperty(RegularFile::class.java)
        val allDirectories: ListProperty<Directory> =
            myObjFactory.listProperty(Directory::class.java)
        val reportTask =
            tasks.register(
                "create${variant.name.capitalize()}CombinedCoverageReport",
                JacocoReport::class,
            ) {
                classDirectories.setFrom(
                    allJars,
                    allDirectories.map { dirs ->
                        dirs.map { dir ->
                            myObjFactory.fileTree().setDir(dir).exclude(coverageExclusions)
                        }
                    },
                )
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }

                fun SourceDirectories.Flat?.toFilePaths(): Provider<List<String>> =
                    this
                        ?.all
                        ?.map { directories -> directories.map { it.asFile.path } }
                        ?: provider { emptyList() }
                sourceDirectories.setFrom(
                    files(
                        variant.sources.java.toFilePaths(),
                        variant.sources.kotlin.toFilePaths(),
                    ),
                )

                executionData.setFrom(
                    project.fileTree("$buildDir/outputs/unit_test_code_coverage/${variant.name}UnitTest")
                        .matching { include("**/*.exec") },
                    project.fileTree("$buildDir/outputs/code_coverage/${variant.name}AndroidTest")
                        .matching { include("**/*.ec") },
                )
            }

        variant.artifacts.forScope(ScopedArtifacts.Scope.PROJECT)
            .use(reportTask)
            .toGet(
                ScopedArtifact.CLASSES,
                { _ -> allJars },
                { _ -> allDirectories },
            )
    }
}
