package it.zagoli.cluehelp

import android.app.Application
import timber.log.Timber

class ClueHelpApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}