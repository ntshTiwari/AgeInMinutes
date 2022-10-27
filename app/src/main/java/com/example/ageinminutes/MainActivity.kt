package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    /// this will give an error, as at this point the content view and everything is not set, so we cannot get any view,
    /// we will have to make it nullable here.
//    var selectedDate: TextView = findViewById<TextView>(R.id.selectedDateView)
    var selectedDate: TextView? = null

    var ageInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedDate = findViewById<TextView>(R.id.selectedDateView)
        ageInMinutes = findViewById<TextView>(R.id.ageInMinutes)

        val dateSelectorButton: Button = findViewById<Button>(R.id.dateSelector)

        dateSelectorButton.setOnClickListener { view ->
            onDateSelect();
        }
    }

    fun onDateSelect() {
        /// retrieves a calender object
        val myCalender = Calendar.getInstance();
        val year = myCalender.get(Calendar.YEAR);
        val month = myCalender.get(Calendar.MONTH);
        val date = myCalender.get(Calendar.DAY_OF_MONTH);

        /// shows a DatePickerDialog
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{ selectedView, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDateString = "${selectedDayOfMonth}/${selectedMonth+1}/${selectedYear}";
                selectedDate?.text = selectedDateString;

                /// this is a better way of storing date formats
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val dateInDateFormat = simpleDateFormat.parse(selectedDateString);

                val currentDate = simpleDateFormat.parse(simpleDateFormat.format((System.currentTimeMillis())))

                displaySelectedDateInMinutes(currentDate, dateInDateFormat);
            },
            year,
            month,
            date,
        ).show();
        /// year, month, date, are initial params that will be selected in the datePicker
    }

    fun displaySelectedDateInMinutes(currentDate: Date, selectedDate: Date) {
        val currentDateInMinutes = currentDate.time / 60000;
        val selectedDateInMinutes = selectedDate.time / 60000;

        val res = currentDateInMinutes - selectedDateInMinutes;

        ageInMinutes?.text = res.toString()
    }
}