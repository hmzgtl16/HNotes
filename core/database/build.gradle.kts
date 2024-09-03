plugins {
    alias(libs.plugins.hnotes.android.library)
    alias(libs.plugins.hnotes.android.hilt)
    alias(libs.plugins.hnotes.android.room)
}

android {
    namespace = "com.example.hnotes.core.database"

    defaultConfig {
        testInstrumentationRunner = "com.example.hnotes.core.testing.HNotesTestRunner"
    }
}

dependencies {
    api(projects.core.model)

    androidTestImplementation(projects.core.testing)
}

