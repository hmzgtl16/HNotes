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
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.configureJacoco
import com.example.hnotes.configureKotlinAndroid
import com.example.hnotes.configureLintLibrary
import com.example.hnotes.configureSpotlessAndroid
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.testing.jacoco.plugins.JacocoPlugin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com-android-library").get().get().pluginId)
            apply(plugin = libs.findPlugin("com-diffplug-spotless").get().get().pluginId)
            apply<JacocoPlugin>()

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureLintLibrary()
                configureJacoco(this)
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
                resourcePrefix =
                    path.split("""\W""".toRegex())
                        .drop(1)
                        .distinct()
                        .joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configureJacoco(this)
            }
            extensions.configure<SpotlessExtension> {
                configureSpotlessAndroid(this)
            }
            dependencies {
                add(
                    "androidTestImplementation",
                    libs.findLibrary("org-jetbrains-kotlin-test").get(),
                )
                add("testImplementation", libs.findLibrary("org-jetbrains-kotlin-test").get())
                add("implementation", libs.findLibrary("androidx-tracing-ktx").get())
            }
        }
    }
}
