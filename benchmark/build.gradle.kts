import com.android.build.api.dsl.ManagedVirtualDevice
import com.example.hnotes.configureFlavors

plugins {
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.hnotes.android.test)
}

android {
    namespace = "com.example.hnotes.benchmark"
    compileSdk = 34



    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "APP_BUILD_TYPE_SUFFIX", "\"\"")
    }

    buildFeatures {
        buildConfig = true
    }

    configureFlavors(this) { flavor ->
        buildConfigField(
            "String",
            "APP_FLAVOR_SUFFIX",
            "\"${flavor.applicationIdSuffix ?: ""}\""
        )
    }

    testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel8Api34") {
            device = "Pixel 8"
            apiLevel = 34
            systemImageSource = "aosp"
        }

        targetProjectPath = ":app"
        experimentalProperties["android.experimental.self-instrumenting"] = true
    }
}

baselineProfile {
    managedDevices += "pixel8Api34"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.test.ext.junit.ktx)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
}

/*
androidComponents {
    onVariants { v ->
        val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { artifactsLoader.load(it)?.applicationId }
        )
    }
}
*/
