import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.example.hnotes.configureBadgingTasks
import com.example.hnotes.configureGradleManagedDevices
import com.example.hnotes.configureKotlinAndroid
import com.example.hnotes.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.dropbox.dependency-guard")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(commonExtension = this)
                defaultConfig.targetSdk = 34
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(commonExtension = this)
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(extension = this)
                configureBadgingTasks(
                    baseExtension = extensions.getByType<BaseExtension>(),
                    componentsExtension = this
                )
            }
        }
    }
}