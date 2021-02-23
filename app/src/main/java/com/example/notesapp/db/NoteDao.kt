package com.example.notesapp.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note:Note)

    @Query("SELECT Note.id,Note.title,Note.city,Note.note,City.zip FROM NOTE left Outer join City on Note.city=City.city ")
    suspend fun getAllNotes():List<NoteAndCity?>?


    @Query("SELECT Note.id,Note.title,Note.city,Note.note,City.zip FROM NOTE left Outer join City on Note.city=City.city ")
    suspend fun getAllNotesPaged():List<NoteAndCity?>?

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note:Note)
}