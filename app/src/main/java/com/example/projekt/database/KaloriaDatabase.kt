package com.example.projekt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [KaloriaSession::class], version = 1, exportSchema = false)
abstract class KaloriaDatabase: RoomDatabase() {
    abstract val kaloriaDatabaseDao: KaloriaDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: KaloriaDatabase? = null

        fun getInstance(context: Context): KaloriaDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KaloriaDatabase::class.java,
                        "sleep_history_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}