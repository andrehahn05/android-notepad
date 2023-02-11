package com.hahn.notepad.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.hahn.notepad.database.AppDatabase
import com.hahn.notepad.databinding.ActivityListBinding
import com.hahn.notepad.extensions.navigate
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getNote()
            }
        }
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

    private fun configRecyclerView() {
        val divider = DividerItemDecoration(this,VERTICAL)
         binding.activityListRv.addItemDecoration(divider)
         binding.activityListRv.adapter = adapter
         navigateToDetails()
    }

    private fun navigateToForm() {
        navigate(NoteFormActivity::class.java)
    }

    private fun navigateToDetails() {
        adapter.handleItemClicked = { noteList ->
            navigate(DetailsOfNotepad::class.java) {
                putExtra(NOTE_ID, noteList.id)
            }
        }
    }
}
