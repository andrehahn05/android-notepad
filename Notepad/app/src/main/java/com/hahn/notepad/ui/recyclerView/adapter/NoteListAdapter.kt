package com.hahn.notepad.ui.recyclerView.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hahn.notepad.databinding.ItemNoteBinding
import com.hahn.notepad.model.Notepad


class NoteListAdapter(
    private val context: Context,
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private val noteLists = mutableListOf<Notepad>()

    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var noteList: Notepad

        fun bind(note: Notepad) {
            this.noteList = note
            val title = binding.itemNoteTitle
            val desc = binding.itemNoteDescription
            title.text = note.title
            desc.text = note.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = noteLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteList = noteLists[position]
        holder.bind(noteList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(noteLists: List<Notepad>){
        this.noteLists.clear()
        this.noteLists.addAll(noteLists)
        notifyDataSetChanged()
    }
}