package com.example.hnotes.core.rules

import android.Manifest
import android.os.Build
import androidx.test.rule.GrantPermissionRule.grant
import org.junit.rules.TestRule

class GrantPostNotificationsPermissionRule :
    TestRule by if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        grant(Manifest.permission.POST_NOTIFICATIONS) else grant()