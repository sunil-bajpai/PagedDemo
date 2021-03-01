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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

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

    @InternalCoroutinesApi
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

        viewModel.getAllNotes()
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Main) {

                viewModel.databaseResponse.collect()
                {
                    when(it){
                        is NoteViewModel.ResponseState.Success -> {
                            Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
                            binding.recyclerViewNotes.adapter = NotesAdapter(it.data)
                        }
                        is NoteViewModel.ResponseState.Error -> {
                            Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                        }
                        is NoteViewModel.ResponseState.Loading -> {
                            Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

//                viewModel.getAllNotes()
//                val notes= viewModel.result

//              val notes=NoteDatabase(it).getNoteDao().getAllNotes()
//              set adapter

//                binding.add.setOnClickListener(View.OnClickListener {
//
//                val action=HomeFragmentDirections.actionAddNote()
//                Navigation.findNavController(it).navigate(action)
//                })
            }
        }
        binding.add.setOnClickListener(View.OnClickListener {

            val action=HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        })
    }
}