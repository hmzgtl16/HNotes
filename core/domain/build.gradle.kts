plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.example.hnotes.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)

    testImplementation(projects.core.testing)
}
