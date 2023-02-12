package com.hahn.notepad.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hahn.notepad.R
import com.hahn.notepad.database.AppDatabase
import com.hahn.notepad.database.dao.NotepadDao
import com.hahn.notepad.databinding.ActivityDetailsOfNotepadBinding
import com.hahn.notepad.extensions.navigate
import com.hahn.notepad.model.Notepad
import kotlinx.coroutines.launch


class DetailsOfNotepad : AppCompatActivity() {

    private var noteList: Notepad? = null
    private var noteId: String? = null
    private val binding by lazy {
        ActivityDetailsOfNotepadBinding.inflate(layoutInflater)
    }

    private val noteDao: NotepadDao by lazy {
        AppDatabase.getInstance(this).notepadDao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadIdNote()
    }

    override fun onResume() {
        super.onResume()
        getNote()
    }

    private fun tryLoadIdNote() {
        noteId = intent.getStringExtra(NOTE_ID)

    }

    private fun getNote() {
        lifecycleScope.launch {
            noteDao.findById(noteId).collect() { noteFound ->
                noteList = noteFound
                noteList?.let {
                    completedFields(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_of_notepad_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.details_menu_save -> {
                navigate(NoteFormActivity::class.java){
                    putExtra(NOTE_ID, noteList?.id)
                }
            }
            R.id.details_menu_del -> {
                noteList?.let {
                    lifecycleScope.launch {
                        noteDao.remove(it)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun completedFields(note: Notepad) {
        with(binding) {
            binding.activityDetailsTitle.text = note.title
            binding.activityDetailsDescription.text= note.description
        }
    }


}