plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.library.compose)
    alias(libs.plugins.hnotes.kotlinx.serialization)
}

android {
    namespace = "com.example.hnotes.core.ui"

    defaultConfig {
        testInstrumentationRunner = "com.example.hnotes.core.testing.HNotesTestRunner"
    }
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.domain)

    implementation(libs.io.coil.kt)
    implementation(libs.io.coil.kt.compose)

    androidTestImplementation(projects.core.testing)
}