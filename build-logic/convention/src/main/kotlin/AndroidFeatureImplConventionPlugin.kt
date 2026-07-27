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
import com.android.build.api.dsl.LibraryExtension
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("hnotes-android-library").get().get().pluginId)
            apply(plugin = libs.findPlugin("hnotes-hilt").get().get().pluginId)
            apply(plugin = libs.findPlugin("hnotes-module-graph").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {
                add(
                    "implementation",
                    project(":core:data"),
                )
                add(
                    "implementation",
                    project(":core:ui"),
                )

                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-runtime-compose").get(),
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-viewmodel-compose").get(),
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-hilt-lifecycle-viewmodel-compose").get(),
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test").get(),
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test-manifest").get(),
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-lifecycle-runtime-testing").get(),
                )
            }
        }
    }
}
