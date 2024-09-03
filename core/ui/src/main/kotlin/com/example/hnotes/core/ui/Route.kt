package com.example.hnotes.core.ui

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data class ListRoute(val id : Long) : Route

    @Serializable
    data object ListsRoute : Route

    @Serializable
    data class NoteRoute(val id : Long) : Route

    @Serializable
    data object NotesRoute : Route

    @Serializable
    data object SearchRoute : Route

    @Serializable
    data object SettingsRoute : Route

    @Serializable
    data class TaskRoute(val id : Long) : Route

    @Serializable
    data object TasksRoute : Route
}
