buildscript {
    repositories {
        google()
        mavenCentral()

        // Android Build Server
        maven { url = uri("../hnotes-prebuilts/m2repository") }
    }
    dependencies {
        classpath(libs.com.google.android.gms.play.services.oss.licenses.plugin) {
            exclude(group = "com.google.protobuf")
        }
    }
}

plugins {
   // alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.compose.screenshot) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.com.dropbox.dependency.guard) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.compose) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
