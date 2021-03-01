package com.example.notesapp.db

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun addNote(note:Note)

    @Query("SELECT Note.id,Note.title,Note.city,Note.note,City.zip FROM NOTE left Outer join City on Note.city=City.city ")
    fun getAllNotes():List<NoteAndCity?>?

    @Query("SELECT Note.id,Note.title,Note.city,Note.note,City.zip FROM NOTE left Outer join City on Note.city=City.city ")
    fun getAllNotesPaged(): DataSource.Factory<Int, NoteAndCity>

    @Insert
    fun addMultipleNotes(vararg note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note:Note)

}
