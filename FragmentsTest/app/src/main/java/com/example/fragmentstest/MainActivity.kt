package com.example.fragmentstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        showFacultyFragment()
    }

    private fun showFacultyFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, FacultetsFragment())
            .commit()
    }

    fun showGroupsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, GroupsFragment())
            .addToBackStack(null)
            .commit()
    }
}