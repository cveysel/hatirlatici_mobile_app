package com.example.myap

import android.content.ContentValues
import android.content.Context

class NotesRepository(context: Context) {
    private val dbHelper = NotesDatabaseHelper(context)

    fun insertNote(note: Note): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(NotesDatabaseHelper.COLUMN_TITLE, note.title)
            put(NotesDatabaseHelper.COLUMN_SUBJECT, note.subject)
            put(NotesDatabaseHelper.COLUMN_DATE, note.date)
            put(NotesDatabaseHelper.COLUMN_TIME, note.time)
        }
        val id = db.insert(NotesDatabaseHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }


    fun getAllNotes(): List<Note> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            NotesDatabaseHelper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val notes = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_TITLE))
            val subject = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_SUBJECT))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(NotesDatabaseHelper.COLUMN_TIME))
            notes.add(Note(id, title, subject, date, time))
        }
        cursor.close()
        db.close()
        return notes
    }

}
