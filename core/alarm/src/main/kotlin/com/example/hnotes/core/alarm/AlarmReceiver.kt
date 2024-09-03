package com.example.hnotes.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.hnotes.core.notification.Notifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject lateinit var notifier: Notifier

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(ALARM_EXTRA_ID, 0L) ?: return

        notifier.postTaskNotification(id = id)
    }
}

