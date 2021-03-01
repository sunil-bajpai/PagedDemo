package com.example.notesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.NotesLayoutBinding
import com.example.notesapp.db.NoteAndCity

class PagedNoteAdapter() : PagingDataAdapter<NoteAndCity, PagedNoteAdapter.myViewHolder>(DIFF_CALLBACK) {

    inner class myViewHolder(val myview: NotesLayoutBinding) : RecyclerView.ViewHolder(myview.root)

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val item = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            DataBindingUtil.inflate<NotesLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.notes_layout,
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<NoteAndCity>() {
            override fun areItemsTheSame(
                oldNote: NoteAndCity,
                newNote: NoteAndCity
            ) = oldNote.id == newNote.id

            override fun areContentsTheSame(
                oldNote: NoteAndCity,
                newNote: NoteAndCity
            ) = oldNote == newNote
        }
    }
}
