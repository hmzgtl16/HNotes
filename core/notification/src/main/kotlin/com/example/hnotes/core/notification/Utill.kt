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
package com.example.hnotes.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri

private const val NOTIFICATION_CHANNEL_ID = "hnote_notification_channel"
private const val NOTIFICATION_REQUEST_CODE = 0
private const val TARGET_ACTIVITY_NAME = "com.example.hnotes.MainActivity"

private val DEEP_LINK_SCHEME_AND_HOST = "http://www.example.com/hnotes"

fun deepLink(id: Long): Uri = "$DEEP_LINK_SCHEME_AND_HOST/$id".toUri()

fun Context.createNotification(deepLink: Uri): Notification {
    ensureNotificationChannelExists()
    return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .apply {
            setSmallIcon(R.drawable.core_notification_ic_notification)
            setContentTitle(getString(R.string.core_notification_notification_title))
            setContentText(getString(R.string.core_notification_notification_description))
            setContentIntent(taskPendingIntent(deepLink = deepLink))
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setAutoCancel(true)
        }
        .build()
}

private fun Context.ensureNotificationChannelExists() {
    val channel =
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            getString(R.string.core_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = getString(R.string.core_notification_channel_description)
        }

    NotificationManagerCompat
        .from(this)
        .createNotificationChannel(channel)
}

private fun Context.taskPendingIntent(deepLink: Uri): PendingIntent? =
    PendingIntent.getActivity(
        this,
        NOTIFICATION_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = deepLink
            component = ComponentName(packageName, TARGET_ACTIVITY_NAME)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
