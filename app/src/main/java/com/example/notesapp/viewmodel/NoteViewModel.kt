package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteAndCity
import com.example.notesapp.db.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NoteViewModel(val database: NoteDao, application: Application) : AndroidViewModel(application) {

    sealed class ResponseState {
        data class Success(val data: List<NoteAndCity>) : ResponseState()
//        data class Success(val data: PagedList<NoteAndCity>) : ResponseState()
        data class Error(val error: String) : ResponseState()
        object Loading : ResponseState()
        object Empty : ResponseState()
    }

//    var pagedListLiveData: LiveData<PagedList<NoteAndCity>>? = null
//    val noteList: LiveData<PagedList<NoteAndCity>> =database.getAllNotesPaged().toLiveData(pageSize = 50)

    val databaseResponse = MutableStateFlow<ResponseState>(ResponseState.Empty)

    // Coroutine that will be canceled when the ViewModel is cleared.
    var result:List<NoteAndCity?>?=null

            fun addNote(note: Note) {
                viewModelScope.launch(Dispatchers.IO) {
                    database.addNote(note)
                }
            }

            fun getAllNotes() {
                viewModelScope.launch(Dispatchers.IO) {
                    databaseResponse.value = ResponseState.Loading
                    try {
//                        val config = PagedList.Config.Builder()
//                        .setPageSize(30) //mandatory to provide
//                        .setInitialLoadSizeHint(50) //default: page size*3
//                        .setPrefetchDistance(10) //default: page size
//                        .build()
//                        val pageData = LivePagedListBuilder(database.getAllNotesPaged(), config).build()

                        result=database.getAllNotes()
                        databaseResponse.value = ResponseState.Success(result as List<NoteAndCity>)
                        }
                    catch (t: Throwable){
                        databaseResponse.value = ResponseState.Error(t.toString())
                    }
                }
            }

            fun updateNote(note: Note) {
                viewModelScope.launch(Dispatchers.IO) {
                    database.updateNote(note)
                }
            }

            fun deleteNote(note: Note) {
                viewModelScope.launch(Dispatchers.IO) {
                    database.deleteNote(note)
                }
            }
}