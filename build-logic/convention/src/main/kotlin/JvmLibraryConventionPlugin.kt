import com.diffplug.gradle.spotless.SpotlessExtension
import com.example.hnotes.configureKotlinJvm
import com.example.hnotes.configureSpotlessJvm
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("org-jetbrains-kotlin-jvm").get().get().pluginId)
            apply(plugin = libs.findPlugin("com-diffplug-spotless").get().get().pluginId)

            configureKotlinJvm()
            extensions.configure<SpotlessExtension> {
                configureSpotlessJvm(this)
            }
            dependencies {
                add("testImplementation", libs.findLibrary("org-jetbrains-kotlin-test").get())
            }
        }
    }
}
