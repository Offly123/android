package com.example.deathnote

import android.icu.util.ULocale
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.deathnote.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter (
    private val notes: List<Note>,
    private val onItemClick: (Note) -> Unit,
    private val onItemLongClick: (Note) -> Unit
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.textViewTitle.text = note.title
            binding.textViewContent.text = note.content
            val date = Date(note.createdAt) // Здесь преобразование
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            binding.textViewDate.text = dateFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)

        holder.itemView.setOnClickListener { onItemClick(note) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(note)
            true
        }
    }

    override fun getItemCount() = notes.size
}