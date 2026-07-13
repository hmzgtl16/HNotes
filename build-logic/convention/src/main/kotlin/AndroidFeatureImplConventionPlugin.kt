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
                    project(":core:data")
                )
                add(
                    "implementation",
                    project(":core:ui")
                )

                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-runtime-compose").get()
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-viewmodel-compose").get()
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-hilt-lifecycle-viewmodel-compose").get()
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test").get()
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test-manifest").get()
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-lifecycle-runtime-testing").get()
                )
            }
        }
    }
}