import com.android.build.api.dsl.LibraryExtension
import com.example.hnotes.configureAndroidCompose
import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com-android-library").get().get().pluginId)
            apply(plugin = libs.findPlugin("org-jetbrains-kotlin-compose").get().get().pluginId)

            extensions.configure<LibraryExtension>() {
                configureAndroidCompose(this)
            }
        }
    }
}