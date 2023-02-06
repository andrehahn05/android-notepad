package com.hahn.notepad.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hahn.notepad.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleFab()
    }

    private fun handleFab() {
        val fab = binding.activityListFab
        fab.setOnClickListener {
            navigateToForm()
        }
    }

    private fun navigateToForm() {
        Intent(this@ListActivity, NoteFormActivity::class.java)
            .apply {
                startActivity(this)
            }
    }
}