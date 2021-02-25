package com.example.notesapp.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun addNote(note:Note)

    @Query("SELECT Note.id,Note.title,Note.city,Note.note,City.zip FROM NOTE left Outer join City on Note.city=City.city ")
    fun getAllNotes():List<NoteAndCity?>?

    @Insert
    fun addMultipleNotes(vararg note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note:Note)

}