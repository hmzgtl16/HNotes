import com.android.build.gradle.LibraryExtension
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("hnotes.android.library")
                apply("hnotes.android.hilt")
                apply("hnotes.kotlinx.serialization")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.example.hnotes.core.testing.HNotesTestRunner"
                }
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(commonExtension = this)
            }

            dependencies {
                add(
                    configurationName = "implementation",
                    dependencyNotation = project(":core:ui")
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = project(":core:designsystem")
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("androidx.hilt.navigation.compose").get()
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("androidx.lifecycle.runtime.compose").get()
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("androidx.lifecycle.viewmodel.compose").get()
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("androidx.navigation.compose").get()
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("androidx.tracing.ktx").get()
                )
                add(
                    configurationName = "androidTestImplementation",
                    dependencyNotation = libs.findLibrary("androidx.lifecycle.runtime-testing").get()
                )
            }
        }
    }
}
