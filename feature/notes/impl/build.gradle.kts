plugins {
    alias(libs.plugins.hnotes.android.feature.impl)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.notes.impl"
}

dependencies {
    implementation(projects.feature.notes.api)
    implementation(projects.feature.note.api)

    implementation(libs.androidx.compose.material3.adaptive.navigation3)
}

