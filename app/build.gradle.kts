import com.example.hnotes.BuildType

plugins {
    alias(libs.plugins.hnotes.android.application)
    alias(libs.plugins.hnotes.android.application.compose)
    alias(libs.plugins.hnotes.hilt)
    alias(libs.plugins.hnotes.module.graph)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.hnotes"

    defaultConfig {
        applicationId = "com.example.hnotes"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            // baselineProfile.automaticGenerationDuringBuild = true
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.feature.notes)
    implementation(projects.feature.notes.api)
    implementation(projects.feature.notes.impl)
    implementation(projects.feature.note)
    implementation(projects.feature.search)
    implementation(projects.feature.search.api)
    implementation(projects.feature.settings)
    implementation(projects.feature.settings.api)

    implementation(projects.core.data)
    implementation(projects.core.design)
    implementation(projects.core.navigation)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.org.jetbrains.kotlinx.serialization.json)

    baselineProfile(projects.benchmark)
}

baselineProfile {
    automaticGenerationDuringBuild = false
    dexLayoutOptimization = true
}