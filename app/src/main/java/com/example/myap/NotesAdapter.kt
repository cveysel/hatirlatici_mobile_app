package com.example.myap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val notesList: MutableList<Note>,
    private val dbHelper: NotesDatabaseHelper
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvSubject: TextView = itemView.findViewById(R.id.tvSubject)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]

        holder.tvTitle.text = note.title
        holder.tvSubject.text = note.subject
        holder.tvDate.text = "Tarih: ${note.date}"
        holder.tvTime.text = "Saat: ${note.time}"

        // Silme butonuna tıklanınca ilgili notu sil
        holder.buttonDelete.setOnClickListener {
            dbHelper.deleteNote(note.id)
            notesList.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount(): Int = notesList.size
}
