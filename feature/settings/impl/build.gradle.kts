plugins {
    alias(libs.plugins.hnotes.android.feature.impl)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.settings.impl"
}

dependencies {
    implementation(projects.feature.settings.api)
}