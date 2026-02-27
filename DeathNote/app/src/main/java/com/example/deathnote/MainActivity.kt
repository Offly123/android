package com.example.deathnote

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deathnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Настройка RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(emptyList(),
            onItemClick = { note ->
                showEditDialog(note)
            },
            onItemLongClick = { note ->
                showDeleteDialog(note)
            }
        )

        binding.recyclerView.adapter = adapter

        // Наблюдение за изменениями в списке заметок
        noteViewModel.allNotes.observe(this) { notes ->
            adapter = NoteAdapter(notes,
                onItemClick = { note ->
                    showEditDialog(note)
                },
                onItemLongClick = { note ->
                    showDeleteDialog(note)
                }
            )
            binding.recyclerView.adapter = adapter
        }

        // Обработка добавлений новой заметки
        binding.btnAdd.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(title = title, content = content)
                noteViewModel.insert(note)

                binding.editTextTitle.text.clear()
                binding.editTextContent.text.clear()

                Toast.makeText(this, "Заметка добавлена", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showDeleteDialog(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Удалить заметку")
            .setMessage("Вы уверены, что хотите удалить заметку?")
            .setPositiveButton("Удалить") { dialog, which ->
                noteViewModel.delete(note)
                Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена",null)
            .show()
    }

    private fun showEditDialog(note: Note) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Редактировать заметку")

        val inputTitle = android.widget.EditText(this).apply {
            setText(note.title)
        }

        val inputContent = android.widget.EditText(this).apply {
            setText(note.content)
        }

        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)
            addView(inputTitle)
            addView(inputContent)
        }

        builder.setView(layout)

        builder.setPositiveButton("Сохранить") { dialog, which ->
            val updateNote = note.copy(
                title = inputTitle.text.toString(),
                content = inputContent.text.toString()
            )

            noteViewModel.update(updateNote)
            Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Отмена", null)
        builder.show()
    }
}