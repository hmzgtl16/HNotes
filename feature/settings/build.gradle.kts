plugins {
    alias(libs.plugins.hnotes.android.feature)
    alias(libs.plugins.hnotes.android.library.compose)
}

android {
    namespace = "com.example.hnotes.feature.settings"
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.gms.play.services.oss.licenses)

    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}
