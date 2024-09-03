plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.core.designsystem"


    defaultConfig {
        testInstrumentationRunner = "com.example.hnotes.core.testing.HNotesTestRunner"
    }
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.icons.extended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.adaptive.navigation.suite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.text.google.fonts)

    implementation(libs.io.coil.kt.compose)

    testImplementation(projects.core.testing)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.com.google.dagger.hilt.android.testing)

    androidTestImplementation(projects.core.testing)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}