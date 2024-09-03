import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.example.hnotes.configureFlavors
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.configureKotlinAndroid
import com.example.hnotes.configurePrintApksTask
import com.example.hnotes.disableUnnecessaryAndroidTests
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(commonExtension = this)
                defaultConfig.targetSdk = 34
                testOptions.animationsDisabled = true
                configureFlavors(commonExtension = this)
                configureGradleManagedDevices(commonExtension = this)
                resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_").lowercase() + "_"
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(extension = this)
                disableUnnecessaryAndroidTests(project = target)
            }

            dependencies {
                add(configurationName = "testImplementation", dependencyNotation = kotlin("test"))

                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("androidx.tracing.ktx").get()
                )
            }
        }
    }
}