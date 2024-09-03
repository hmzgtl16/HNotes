package com.example.hnotes

enum class HNotesBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(applicationIdSuffix = ".debug"),
    RELEASE,
}