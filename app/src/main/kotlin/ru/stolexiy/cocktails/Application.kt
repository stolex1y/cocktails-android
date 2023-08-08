package ru.stolexiy.cocktails

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

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

    companion object {
        private const val GLOBAL_TAG: String = "SURF"
    }
}
