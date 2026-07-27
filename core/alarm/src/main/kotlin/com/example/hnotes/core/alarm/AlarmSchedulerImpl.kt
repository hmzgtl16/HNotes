/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.hnotes.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.hnotes.core.model.RepeatMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit
import kotlin.time.Instant

@Singleton
class AlarmSchedulerImpl
@Inject
constructor(
    @ApplicationContext private val context: Context,
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun schedule(id: Long, scheduleTime: Instant, repeatMode: RepeatMode) {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(AlarmScheduler.ALARM_EXTRA_ID, id)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
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
                    pendingIntent,
                )
            }

            RepeatMode.DAILY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    1.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent,
                )
            }

            RepeatMode.WEEKLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    7.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent,
                )
            }

            RepeatMode.MONTHLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    30.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent,
                )
            }

            RepeatMode.YEARLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    365.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent,
                )
            }
        }
    }

    override fun cancel(id: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
            )
        alarmManager.cancel(pendingIntent)
    }
}
