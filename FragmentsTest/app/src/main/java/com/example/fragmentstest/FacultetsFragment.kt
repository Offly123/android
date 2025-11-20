package com.example.fragmentstest

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentstest.

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FacultetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FacultetsFragment : Fragment() {
    private lateinit var binding: FacultetsFragmentBinding

    private val facultyList = mutableListOf<String>(
        "ФКТиПМ",
        "Ещё факультет",
        "Любимый РГФ"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FacultyFragmentBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}