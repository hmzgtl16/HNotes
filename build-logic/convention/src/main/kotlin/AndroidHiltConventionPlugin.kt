import com.example.hnotes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("com.google.dagger.hilt.android").get()
                )
                add(
                    configurationName = "ksp",
                    dependencyNotation = libs.findLibrary("com.google.dagger.hilt.compiler").get()
                )
            }
        }
    }

}