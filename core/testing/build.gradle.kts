plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.library.compose)
    alias(libs.plugins.hnotes.android.hilt)
}

android {
    namespace = "com.example.hnotes.core.testing"
}

dependencies {

    api(kotlin("test"))
    api(libs.androidx.compose.ui.test.junit4)
    api(projects.core.data)
    api(projects.core.model)

    debugApi(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.test.rules)
    implementation(libs.com.google.dagger.hilt.android.testing)
    implementation(libs.org.jetbrains.kotlinx.coroutines.android)
    implementation(libs.org.jetbrains.kotlinx.coroutines.test)
    implementation(libs.org.jetbrains.kotlinx.datetime)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

