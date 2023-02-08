package com.hahn.notepad.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hahn.notepad.database.AppDatabase
import com.hahn.notepad.databinding.ActivityListBinding
import com.hahn.notepad.extensions.nav
import com.hahn.notepad.ui.recyclerView.adapter.NoteListAdapter
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
    private val adapter by lazy {
        NoteListAdapter(this)
    }
    private val binding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }
    private val noteDao by lazy {
        AppDatabase.getInstance(this).notepadDao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configRecyclerView()
        handleFab()
//        lifecycleScope.launch { getNote() }
    }

    private suspend fun getNote() {
        noteDao.findAll().collect() { adapter.update(it) }

    }

    private fun handleFab() {
        val fab = binding.activityListFab
        fab.setOnClickListener {
            navigateToForm()
        }
    }

    private fun navigateToForm() {
        val intent = Intent(this@ListActivity, NoteFormActivity::class.java)
        startActivity(intent)
    }

    private fun configRecyclerView() {
        val recyclerView = binding.activityListRv
        recyclerView.adapter = adapter

        }
    }
