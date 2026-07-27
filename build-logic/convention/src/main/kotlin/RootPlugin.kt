import com.diffplug.gradle.spotless.SpotlessExtension
import com.example.hnotes.configureSpotlessRootProject
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class RootPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com-diffplug-spotless").get().get().pluginId)

            require(path == ":")
            extensions.configure<SpotlessExtension> {
                configureSpotlessRootProject(this)
            }
        }
    }
}
