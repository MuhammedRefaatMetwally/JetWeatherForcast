package com.example.jetweatherforcast.repository


import com.example.jetweatherforcast.data.WeatherDao
import com.example.jetweatherforcast.model.Favorite
import com.example.jetweatherforcast.model.UnitDegree
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)

    fun getUnits(): Flow<List<UnitDegree>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: UnitDegree) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit:  UnitDegree) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit:  UnitDegree) = weatherDao.deleteUnit(unit)



}