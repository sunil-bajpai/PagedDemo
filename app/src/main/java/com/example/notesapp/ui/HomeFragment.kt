package com.example.notesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.db.NoteAndCity
import com.example.notesapp.db.NoteDatabase
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private lateinit var binding:FragmentHomeBinding
private lateinit var viewModel: NoteViewModel

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
                DataBindingUtil.inflate<ViewDataBinding>(
                        inflater,
                        R.layout.fragment_home,
                        container,
                        false
                ) as FragmentHomeBinding
        val rootView = binding.getRoot()
        return rootView
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerViewNotes.setHasFixedSize(true)
        binding.recyclerViewNotes.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        val application = requireActivity().application

        val dataSource = NoteDatabase.getInstance(application).getNoteDao()

        val noteViewModelFactory =
            NoteViewModelFactory(
                dataSource, application
            )

        viewModel =
            ViewModelProviders.of(this,noteViewModelFactory).get(NoteViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Main) {

                viewModel.getAllNotes()
                delay(500)

                val notes= viewModel.result

//              val notes=NoteDatabase(it).getNoteDao().getAllNotes()
//              set adapter
                if(!notes.isNullOrEmpty()) {
                    binding.recyclerViewNotes.adapter = NotesAdapter(notes as List<NoteAndCity>)
                }

                binding.add.setOnClickListener(View.OnClickListener {

                val action=HomeFragmentDirections.actionAddNote()
                Navigation.findNavController(it).navigate(action)
                })
            }
        }
    }
}