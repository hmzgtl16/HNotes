plugins {
    alias(libs.plugins.hnotes.android.feature.impl)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.label.impl"
}

dependencies {
    implementation(projects.feature.label.api)
    implementation(projects.feature.notes.api)

    implementation(libs.androidx.compose.material3.adaptive.navigation3)
    implementation(libs.androidx.navigation3.ui)
}