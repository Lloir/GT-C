package com.example

import android.app.Application
import com.example.data.local.AppDatabase
import com.example.data.repository.GalacticRepository
import com.example.service.NotificationHelper

class GalacticTycoonsApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: GalacticRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
        repository = GalacticRepository(database)
        NotificationHelper.createNotificationChannels(this)
    }
}
