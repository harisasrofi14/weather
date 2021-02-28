package com.example.myweather.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myweather.data.local.entity.Weather


@Database(entities = [Weather::class], version = 1, exportSchema = false)
abstract class WeatherRoomDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): WeatherRoomDatabase {
            if (INSTANCE == null) {
                synchronized(WeatherRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WeatherRoomDatabase::class.java,
                            "weather_database"
                        ).build()
                    }
                }
            }
            return INSTANCE as WeatherRoomDatabase
        }
    }
}