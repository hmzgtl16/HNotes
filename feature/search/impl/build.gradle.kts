plugins {
    alias(libs.plugins.hnotes.android.feature.impl)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.search.impl"
}

dependencies {
    implementation(projects.feature.note.api)
}