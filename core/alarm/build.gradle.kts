plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.hilt)
}

android {
    namespace = "com.example.hnotes.core.alarm"
}

dependencies {

    implementation(projects.core.model)
    implementation(projects.core.notification)
}