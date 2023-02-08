package com.hahn.notepad.ui.activity


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hahn.notepad.R
import com.hahn.notepad.database.AppDatabase
import com.hahn.notepad.database.dao.NotepadDAO
import com.hahn.notepad.databinding.ActivityNoteFormBinding
import com.hahn.notepad.extensions.toast
import com.hahn.notepad.model.Notepad
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class NoteFormActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityNoteFormBinding.inflate(layoutInflater)
    }
    private val noteDao: NotepadDAO by lazy {
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
            noteDao.findById(id).filterNotNull().collect { foundNote->
                noteId = foundNote.id
                completedFields(foundNote)
            }
        }
    }

    private fun tryLoadIdNote() {
        noteId = intent.getStringExtra(NOTE_ID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_form_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.details_menu_save -> {
                addNote()
                Log.i("onclick", "item clicado ${item}")
                toast("Salvo com sucesso")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNote() {
        val note = createNote()
        lifecycleScope.launch {
            noteDao.save(note)
            finish()
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