plugins {
    alias(libs.plugins.hnotes.android.feature)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.tasks"
}

dependencies {

    implementation(projects.core.common)

    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}