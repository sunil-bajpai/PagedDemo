package com.example.notesapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {

    @Insert
    fun addCity(city: City)

}