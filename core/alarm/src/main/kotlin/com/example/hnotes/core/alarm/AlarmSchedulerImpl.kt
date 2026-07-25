package com.example.hnotes.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.hnotes.core.model.RepeatMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

@Singleton
class AlarmSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun schedule(id: Long, scheduleTime: Instant, repeatMode: RepeatMode) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmScheduler.ALARM_EXTRA_ID, id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        )

        when (repeatMode) {
            RepeatMode.NONE -> {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    pendingIntent
                )
            }

            RepeatMode.DAILY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    1.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }

            RepeatMode.WEEKLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    7.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }

            RepeatMode.MONTHLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    30.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }

            RepeatMode.YEARLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    365.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }
        }
    }

    override fun cancel(id: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        )
        alarmManager.cancel(pendingIntent)
    }
}