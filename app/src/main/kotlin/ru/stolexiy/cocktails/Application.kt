package ru.stolexiy.cocktails

import android.app.Application
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ru.stolexiy.cocktails.ui.util.notification.NotificationChannels.initChannels
import timber.log.Timber
import java.util.Optional
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

@HiltAndroidApp
class Application : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var notificationManager: Optional<NotificationManager>

    override fun onCreate() {
        super.onCreate()

        notificationManager.getOrNull()?.initChannels(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(
                        priority, "[$GLOBAL_TAG] $tag", message, t
                    )
                }
            })
        }

        Thread.currentThread().setUncaughtExceptionHandler { _, error ->
            Timber.e(error, "Uncaught exception:")
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    companion object {
        private const val GLOBAL_TAG: String = "SURF"
    }
}
