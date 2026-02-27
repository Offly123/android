package com.example.deathnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val respository: NoteRepository
    val allNotes: LiveData<List<Note>>
    val searchResults = MutableLiveData<List<Note>>()

    init {
        val noteDao = AppDatabase.getDatabase(application).noteDao()
        respository = NoteRepository(noteDao)
        allNotes = respository.allNotes
    }

    fun insert(note: Note) = viewModelScope.launch {
        respository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        respository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        respository.delete(note)
    }

    fun deleteAll(note: Note) = viewModelScope.launch {
        respository.deleteAll()
    }
}