package com.hahn.notepad.ui.recyclerView.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hahn.notepad.databinding.ItemNoteBinding
import com.hahn.notepad.model.Notepad


class NoteListAdapter(
    private val context: Context,
    private val noteLists: MutableList<Notepad> = mutableListOf(),
    var handleItemClicked: (noteList: Notepad) -> Unit = {},
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = noteLists.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteList = noteLists[position]
        holder.bind(noteList)
    }

    fun update(noteLists: List<Notepad>) {
        notifyItemRangeRemoved(0, this.noteLists.size)
        this.noteLists.clear()
        this.noteLists.addAll(noteLists)
        notifyItemRangeInserted(0, this.noteLists.size)
    }

    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var noteList: Notepad

        init {
            itemView.setOnClickListener {
                if (::noteList.isInitialized) {
                    handleItemClicked(noteList)
                }
            }
        }

        fun bind(noteList: Notepad) {
            this.noteList = noteList
            val title = binding.itemNoteTitle
            val desc = binding.itemNoteDescription
            title.text = noteList.title
            desc.text = noteList.description
        }

    }
}


