package ru.stolexiy.cocktails.ui.util.notification

object NotificationUtils {
    private var nextId: Int = 0

    @Synchronized
    fun getUniqueNotificationId(): Int = nextId++
}
