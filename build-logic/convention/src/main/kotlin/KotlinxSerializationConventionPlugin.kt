import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinxSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("org.jetbrains.kotlinx.serialization.json").get()
                )
            }
        }
    }
}