package com.example.laptoppleasedontdie

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FacultyFragment : Fragment() , MainActivity.Edit {

    companion object {
        private var INSTANCE: FacultyFragment? = null

        fun getInstance(): FacultyFragment{
            if (INSTANCE == null) INSTANCE=FacultyFragment()
            return INSTANCE ?: throw Exception("FacultyFragment не создан")
        }
    }

    private val viewModel: FacultyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_faculty, container, false)
    }

    override fun append() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}