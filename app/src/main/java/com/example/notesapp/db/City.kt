package com.example.notesapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
        @PrimaryKey(autoGenerate = true)
        var id:Int,
      var city:String,
      var zip:Int

)


