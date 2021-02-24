package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notesapp.db.City
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteAndCity
import com.example.notesapp.db.NoteDao

class NoteViewModel(val NoteDao:NoteDao,application: Application) : AndroidViewModel(application) {

    fun addNote(note: Note){
        NoteDao.addNote(note)
    }

    fun getAllNotes():List<NoteAndCity?>?{
        return  NoteDao.getAllNotes()
    }

    fun updateNote(note: Note){
        NoteDao.updateNote(note)
    }

    fun deleteNote(note: Note){
        NoteDao.deleteNote(note)
    }
}