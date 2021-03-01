package com.example.notesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.NotesLayoutBinding
import com.example.notesapp.db.NoteAndCity

class PagedNoteAdapter(private val notes:List<NoteAndCity>) : PagedListAdapter<NoteAndCity, PagedNoteAdapter.myViewHolder>(DIFF_CALLBACK) {

    inner class myViewHolder (val myview: NotesLayoutBinding):
        RecyclerView.ViewHolder(myview.root)

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
//            val note: NoteAndCity? = getItem(position)
        holder.myview.textViewTitle.text=notes[position].title
        holder.myview.textViewNote.text=notes[position].note
        holder.myview.city.text=notes[position].city
        holder.myview.zip.text= notes[position].zip.toString()
        holder.myview.root.setOnClickListener{
            val action=HomeFragmentDirections.actionAddNote()
            action.note=notes[position]
            Navigation.findNavController(it).navigate(action)
        }        }

        companion object {
            private val DIFF_CALLBACK = object :
                DiffUtil.ItemCallback<NoteAndCity>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                override fun areItemsTheSame(oldNote: NoteAndCity,
                                             newNote: NoteAndCity) = oldNote.id == newNote.id

                override fun areContentsTheSame(oldNote: NoteAndCity,
                                                newNote: NoteAndCity) = oldNote == newNote
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(DataBindingUtil.inflate<NotesLayoutBinding>(LayoutInflater.from(parent.context), R.layout.notes_layout,parent,false))
    }
}
