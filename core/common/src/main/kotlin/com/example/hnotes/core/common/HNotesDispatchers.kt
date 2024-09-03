package com.example.hnotes.core.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

enum class HNotesDispatchers {
    Default,
    IO
}

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val hNotesDispatcher: HNotesDispatchers)