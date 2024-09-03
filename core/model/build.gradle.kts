plugins {
    alias(libs.plugins.hnotes.android.library)
}

android {
    namespace = "com.example.hnotes.core.model"
}

dependencies {
    api(libs.org.jetbrains.kotlinx.datetime)
}