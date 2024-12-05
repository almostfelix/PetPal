package com.smartdevices.petpal.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var appDatabase: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return appDatabase ?: synchronized(this) {
            appDatabase ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "pet_database"
            ).build().also { appDatabase = it }
        }
    }
}
