package com.example.myap

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myap.databinding.ActivityMainBinding

data class Note(
    val id: Int = 0, // Varsayılan id
    val title: String,
    val subject: String,
    val date: String,
    val time: String
)

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private val notesList = mutableListOf<Note>()
    private lateinit var notesRepository: NotesRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Veritabanı başlat
        notesRepository = NotesRepository(this)

        // RecyclerView kurulumu
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter(
            notesList,
            dbHelper = NotesDatabaseHelper(this)
        )
        recyclerView.adapter = notesAdapter

        // Verileri yükle
        loadNotes()

        // Yeni not eklemek için FAB
        binding.fab.setOnClickListener {
            val intent = Intent(this, NewNotes::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_NOTE)
        }
    }

    private fun loadNotes() {
        notesList.clear()
        notesList.addAll(notesRepository.getAllNotes())
        notesAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_NEW_NOTE && resultCode == RESULT_OK && data != null) {
            val title = data.getStringExtra("NOTE_TITLE") ?: ""
            val subject = data.getStringExtra("NOTE_SUBJECT") ?: ""
            val selectedDate = data.getStringExtra("NOTE_DATE") ?: ""
            val selectedTime = data.getStringExtra("NOTE_TIME") ?: ""

            // Yeni notu ekle
            val newNote = Note(title = title, subject = subject, date = selectedDate, time = selectedTime)
            val insertedId = notesRepository.insertNote(newNote)
            val savedNote = newNote.copy(id = insertedId.toInt())

            notesList.add(savedNote)
            notesAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val REQUEST_CODE_NEW_NOTE = 1
    }
}
