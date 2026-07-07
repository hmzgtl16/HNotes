import com.android.build.api.dsl.LibraryExtension
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.configureKotlinAndroid
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com-android-library").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
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
            dependencies {
                add(
                    "androidTestImplementation",
                    libs.findLibrary("org-jetbrains-kotlin-test").get()
                )
                add("testImplementation", libs.findLibrary("org-jetbrains-kotlin-test").get())
                add("implementation", libs.findLibrary("androidx-tracing-ktx").get())
            }
        }
    }
}