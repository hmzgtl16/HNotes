plugins {
    alias(libs.plugins.hnotes.android.feature)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.notes"
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.data)

    testImplementation(libs.com.google.dagger.hilt.android.testing)
    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}
