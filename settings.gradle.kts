pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "HNotes"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":benchmark")
include(":core:alarm")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:design")
include(":core:model")
include(":core:navigation")
include(":core:notification")
include(":core:ui")
include(":feature:note:api")
include(":feature:note:impl")
include(":feature:notes:api")
include(":feature:notes:impl")
include(":feature:search:api")
include(":feature:search:impl")
include(":feature:settings:api")
include(":feature:settings:impl")
