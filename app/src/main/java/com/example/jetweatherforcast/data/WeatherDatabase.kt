package com.example.jetweatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetweatherforcast.model.Favorite
import com.example.jetweatherforcast.model.UnitDegree


@Database(entities = [Favorite::class, UnitDegree::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}