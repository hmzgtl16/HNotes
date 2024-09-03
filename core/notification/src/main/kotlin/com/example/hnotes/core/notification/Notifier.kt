package com.example.hnotes.core.notification

import android.Manifest.permission
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface Notifier {

    fun postTaskNotification(id: Long)
}

@Singleton
class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : Notifier {

    override fun postTaskNotification(id: Long) =
        with(
            receiver = context,
            block = {

                if (ActivityCompat.checkSelfPermission(
                        this,
                        permission.POST_NOTIFICATIONS
                    ) != PERMISSION_GRANTED
                ) return

                val taskNotification = createNotification(deepLink = deepLink(id = id))
                val notificationManager = NotificationManagerCompat.from(this)

                notificationManager.notify(id.hashCode(), taskNotification)
            }
        )
}

private fun Context.createNotification(deepLink: Uri): Notification {

    ensureNotificationChannelExists()
    return NotificationCompat.Builder(this, TASK_NOTIFICATION_CHANNEL_ID)
        .apply {
            setSmallIcon(R.drawable.ic_notifications)
            setContentTitle(getString(R.string.core_notifications_task_notification_title))
            setContentText(getString(R.string.core_notifications_task_notification_description))
            setContentIntent(taskPendingIntent(deepLink = deepLink))
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setGroup(TASK_NOTIFICATION_GROUP)
            setAutoCancel(true)
        }
        .build()
}

private fun Context.ensureNotificationChannelExists() {
    if (VERSION.SDK_INT < VERSION_CODES.O) return

    val channel = NotificationChannel(
        TASK_NOTIFICATION_CHANNEL_ID,
        getString(R.string.core_notifications_tasks_notification_channel_name),
        NotificationManager.IMPORTANCE_DEFAULT,
    ).apply {
        description = getString(R.string.core_notifications_tasks_notification_channel_description)
    }

    NotificationManagerCompat
        .from(this)
        .createNotificationChannel(channel)
}

private fun Context.taskPendingIntent(deepLink: Uri): PendingIntent? =
    PendingIntent.getActivity(
        this,
        TASK_NOTIFICATION_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = deepLink
            component = ComponentName(packageName, TARGET_ACTIVITY_NAME)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

private fun deepLink(id: Long): Uri = "$DEEP_LINK_SCHEME_AND_HOST/$TASK_PATH/$id".toUri()

private const val TARGET_ACTIVITY_NAME = "com.example.hnotes.MainActivity"
private const val TASK_NOTIFICATION_REQUEST_CODE = 0
private const val TASK_NOTIFICATION_CHANNEL_ID = ""
private const val TASK_NOTIFICATION_GROUP = "TASK_NOTIFICATIONS"

private const val DEEP_LINK_SCHEME_AND_HOST = "https://www.nowinandroid.apps.samples.google.com"
private const val TASK_PATH = "task"