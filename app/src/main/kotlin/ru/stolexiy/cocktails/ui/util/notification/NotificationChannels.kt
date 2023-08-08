package ru.stolexiy.cocktails.ui.util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import ru.stolexiy.cocktails.R

object NotificationChannels {
    const val BACKGROUND_WORK = "BACKGROUND"

    fun NotificationManager.initChannels(appContext: Context) {
        val channels: MutableList<NotificationChannel> = mutableListOf()
        channels += NotificationChannel(
            BACKGROUND_WORK,
            appContext.getString(R.string.background_work),
            NotificationManager.IMPORTANCE_MIN
        )
        createNotificationChannels(channels)
    }
}
