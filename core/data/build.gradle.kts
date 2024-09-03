plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.hilt)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
}

android {
    namespace = "com.example.hnotes.core.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.datastore)

    implementation(projects.core.alarm)

    androidTestImplementation(projects.core.testing)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}
