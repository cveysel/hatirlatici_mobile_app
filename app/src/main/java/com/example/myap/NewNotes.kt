package com.example.myap

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class NewNotes : BaseActivity() {

    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_notes)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedTime = findViewById(R.id.tvSelectedTime)

        val btnSelectDate = findViewById<Button>(R.id.btnSelectDate)
        val btnSelectTime = findViewById<Button>(R.id.btnSelectTime)
        val btnAddNote = findViewById<FloatingActionButton>(R.id.add)


        btnSelectDate.setOnClickListener { showDatePickerDialog() }
        btnSelectTime.setOnClickListener { showTimePickerDialog() }
        btnAddNote.setOnClickListener { sendNoteBack() }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate.text = selectedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                tvSelectedTime.text = selectedTime
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun sendNoteBack() {
        val title = findViewById<EditText>(R.id.baslik).text.toString()
        val subject = findViewById<EditText>(R.id.konu).text.toString()
        val selectedDate = tvSelectedDate.text.toString()
        val selectedTime = tvSelectedTime.text.toString()

        if (title.isNotBlank() && subject.isNotBlank() && selectedDate.isNotBlank() && selectedTime.isNotBlank()) {
            val intent = Intent()
            intent.putExtra("NOTE_TITLE", title)
            intent.putExtra("NOTE_SUBJECT", subject)
            intent.putExtra("NOTE_DATE", selectedDate)
            intent.putExtra("NOTE_TIME", selectedTime)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            // Kullanıcıya uyarı gösterilebilir
        }
    }
}
