package com.example.hnotes.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data class Note(val noteId: Long?) : Route

}

const val DEEP_LINK_SCHEME_AND_HOST = "http://www.example.com/hnotes"