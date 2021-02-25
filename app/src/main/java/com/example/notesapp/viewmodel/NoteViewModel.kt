package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.db.City
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteAndCity
import com.example.notesapp.db.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(val database:NoteDao,application: Application) : AndroidViewModel(application) {

            // Coroutine that will be canceled when the ViewModel is cleared.
    var result:List<NoteAndCity?>?=null

            fun addNote(note: Note) {
                viewModelScope.launch {
                    database.addNote(note)
                }
            }

            fun getAllNotes() {
                viewModelScope.launch {
                    result=database.getAllNotes()
                }
            }

            fun updateNote(note: Note) {
                viewModelScope.launch {
                    database.updateNote(note)
                }
            }

            fun deleteNote(note: Note) {
                viewModelScope.launch {
                    database.deleteNote(note)
                }

    }
}