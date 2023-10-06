package com.tutorials.DOBCalc

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.Month
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    //    private fun clickDatePicker(view: View, year:Int, month: Month) {
    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "Year was $selectedYear, Month was ${selectedMonth + 1}, Day of selectedMonth was $selectedDayOfMonth", Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    /* how much time has passed between selected date and 1970 */
                    val selectedDatesInMinutes = theDate.time/60000
                    /* how much time has passed between now and 1970 */
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time/60000
                        /* get the difference between both dates */
                        val differenceInMinutes = currentDateInMinutes - selectedDatesInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show();
    }
}