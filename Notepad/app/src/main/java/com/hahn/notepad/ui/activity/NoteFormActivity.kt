package com.hahn.notepad.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hahn.notepad.R
import com.hahn.notepad.database.AppDatabase
import com.hahn.notepad.database.dao.NotepadDao
import com.hahn.notepad.databinding.ActivityNoteFormBinding
import com.hahn.notepad.extensions.toast
import com.hahn.notepad.model.Notepad
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


open class NoteFormActivity : AppCompatActivity() {
    private var noteList: Notepad? = null
    private val binding by lazy {
        ActivityNoteFormBinding.inflate(layoutInflater)
    }
    private val noteDao: NotepadDao by lazy {
        AppDatabase.getInstance(this).notepadDao
    }
    private var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadIdNote()
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
            noteDao.findById(id).filterNotNull().collect { foundNote ->
                noteList = foundNote
                noteId = foundNote.id
                completedFields(foundNote)
            }
        }
    }

    private fun tryLoadIdNote() {
        noteId = intent.getStringExtra(NOTE_ID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        noteId?.let {
            menuInflater.inflate(R.menu.details_of_notepad_menu, menu)
        }
        menuInflater.inflate(R.menu.note_form_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.details_menu_save -> {
                addNote()
            }
            R.id.details_menu_del -> {
                remove()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        noteList?.let {
            lifecycleScope.launch {
                noteDao.remove(it)
                toast("Removido com sucesso")
                finish()
            }
        }
    }

    private fun addNote() {
        val note = createNote()
        lifecycleScope.launch {
            if (!(note.title.isEmpty() && note.description.isEmpty())) {
                noteDao.save(note)
                toast("Salvo com sucesso")
                finish()
            }
        }
    }

    private fun createNote(): Notepad {
        val title = binding.activityFormTitle.text.toString()
        val desc = binding.activityFormDescription.text.toString()
        return noteId?.let { id ->
            Notepad(
                id = id, title = title, description = desc
            )
        } ?: Notepad(
            title = title, description = desc
        )
    }
}