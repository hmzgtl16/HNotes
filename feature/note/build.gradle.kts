plugins {
    alias(libs.plugins.hnotes.android.feature)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.note"
}

dependencies {

    implementation(projects.core.common)
    implementation(libs.com.google.accompanist.permissions)

    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}