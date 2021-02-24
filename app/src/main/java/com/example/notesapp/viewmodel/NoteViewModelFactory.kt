package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.db.NoteDao
import kotlinx.coroutines.ExperimentalCoroutinesApi


class NoteViewModelFactory(private val database : NoteDao,private val application: Application) : ViewModelProvider.Factory  {

    @ExperimentalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(
                database, application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}