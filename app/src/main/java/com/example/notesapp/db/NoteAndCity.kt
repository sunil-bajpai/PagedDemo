package com.example.notesapp.db

import java.io.Serializable

data class NoteAndCity(
        var id:Int,
        var title:String,
        var note:String,
        var city:String,
        var zip:Int
):Serializable
