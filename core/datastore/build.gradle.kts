import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Locale

plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.hilt)
    alias(libs.plugins.com.google.protobuf)
}

android {
    namespace = "com.example.hnotes.core.datastore"

    defaultConfig {
        //testInstrumentationRunner = "com.example.hnotes.core.testing.HNotesTestRunner"
        consumerProguardFiles("consumer-proguard-rules.pro")
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

protobuf {

    protoc {
        artifact = libs.com.google.protobuf.protoc.get().toString()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents.beforeVariants {
    android.sourceSets.register(it.name) {
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}

androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val capName = variant.name.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else
                    it.toString()
            }
            tasks.getByName<KotlinCompile>("ksp${capName}Kotlin") {
                setSource(tasks.getByName("generate${capName}Proto").outputs)
            }
        }
    }
}

dependencies {

    api(projects.core.model)

    api(libs.androidx.dataStore)

    implementation(libs.androidx.test.ext.junit.ktx)
    implementation(projects.core.common)
    //implementation(projects.core.testing)
    implementation(libs.com.google.dagger.hilt.android.testing)
    implementation(libs.com.google.protobuf.kotlin.lite)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}

