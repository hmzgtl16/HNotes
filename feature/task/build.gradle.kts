plugins {
    alias(libs.plugins.hnotes.android.feature)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.task"
}

dependencies {

    implementation(projects.core.common)
    implementation(libs.com.github.commandiron.wheel.picker.compose)

    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}