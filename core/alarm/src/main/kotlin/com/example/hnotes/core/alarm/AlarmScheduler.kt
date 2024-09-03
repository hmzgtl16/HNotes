package com.example.hnotes.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.hnotes.core.model.data.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.format
import javax.inject.Inject
import javax.inject.Singleton

interface AlarmScheduler {

    fun schedule(task: Task)

    fun cancel(task: Task)
}

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(task: Task) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_EXTRA_ID, task.id)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            task.reminder.toEpochMilliseconds(),
            PendingIntent.getBroadcast(
                context,
                task.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(task: Task) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                task.id.toInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}

const val ALARM_EXTRA_ID = "EXTRA_ID"
