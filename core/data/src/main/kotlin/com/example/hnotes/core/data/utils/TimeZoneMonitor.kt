package com.example.hnotes.core.data.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.hnotes.core.common.ApplicationScope
import com.example.hnotes.core.common.Dispatcher
import com.example.hnotes.core.common.HNotesDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinTimeZone
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

interface TimeZoneMonitor {
    val currentTimeZone: Flow<TimeZone>
}

@Singleton
internal class TimeZoneBroadcastMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope scope: CoroutineScope,
    @Dispatcher(HNotesDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : TimeZoneMonitor {

    override val currentTimeZone: SharedFlow<TimeZone> =
        callbackFlow {
            trySend(TimeZone.currentSystemDefault())

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    if (intent.action != Intent.ACTION_TIMEZONE_CHANGED) return

                    val zoneIdFromIntent =
                        if (VERSION.SDK_INT < VERSION_CODES.R) {
                            null
                        } else {
                            intent.getStringExtra(Intent.EXTRA_TIMEZONE)?.let { timeZoneId ->
                                val zoneId = ZoneId.of(timeZoneId, ZoneId.SHORT_IDS)
                                zoneId.toKotlinTimeZone()
                            }
                        }

                    trySend(zoneIdFromIntent ?: TimeZone.currentSystemDefault())
                }
            }

            trySend(TimeZone.currentSystemDefault())

            awaitClose {
                context.unregisterReceiver(receiver)
            }
        }
            .distinctUntilChanged()
            .conflate()
            .flowOn(context = dispatcher)
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                replay = 1
            )
}
