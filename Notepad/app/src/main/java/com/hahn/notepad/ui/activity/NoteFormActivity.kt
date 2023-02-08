package com.hahn.notepad.ui.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hahn.notepad.R
import com.hahn.notepad.database.AppDatabase
import com.hahn.notepad.database.dao.NotepadDAO
import com.hahn.notepad.databinding.ActivityNoteFormBinding
import com.hahn.notepad.model.Notepad
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class NoteFormActivity : AppCompatActivity() {

    private var noteId: String? = null
    private var note: Notepad? = null
    private val binding by lazy {
        ActivityNoteFormBinding.inflate(layoutInflater)
    }
    private val noteDao: NotepadDAO by lazy {
        AppDatabase.getInstance(this).notepadDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            getNote()
        }
    }

    private fun completedFields(note: Notepad) {
        with(binding) {
            activityFormTitle.setText(note.title)
            activityFormDescription.setText(note.description)
        }
    }

    private suspend fun getNote() {
        noteId?.let { id ->
            noteDao.findById(id).filterNotNull().collect { it ->
                note = it
                completedFields(it)
            }
        } ?: finish()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_form_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.details_menu_save -> {
                note?.let {
                    lifecycleScope.launch {
                        noteDao.save(it)
                        finish()

                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}