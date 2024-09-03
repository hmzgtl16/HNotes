import com.example.hnotes.HNotesBuildType
import org.gradle.internal.declarativedsl.parsing.main

plugins {
    alias(libs.plugins.hnotes.android.application)
    alias(libs.plugins.hnotes.android.application.compose)
    alias(libs.plugins.hnotes.android.application.flavors)
    alias(libs.plugins.hnotes.android.hilt)

    id("com.google.android.gms.oss-licenses-plugin")
}

android {

    defaultConfig {
        applicationId = "com.example.hnotes"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.hnotes.core.testing.HNotesTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = HNotesBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = HNotesBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    namespace = "com.example.hnotes"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(projects.feature.list)
    implementation(projects.feature.lists)
    implementation(projects.feature.note)
    implementation(projects.feature.notes)
    implementation(projects.feature.search)
    implementation(projects.feature.settings)
    implementation(projects.feature.task)
    implementation(projects.feature.tasks)

    implementation(projects.core.alarm)
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)

    ksp(libs.com.google.dagger.hilt.compiler)
}

dependencyGuard {
    configuration("prodReleaseRuntimeClasspath")
}
