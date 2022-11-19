package com.examples.android.androidtrainingbzu

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.examples.android.androidtrainingbzu.Fragments.TimePickerFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class DialogsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Exit? -Dialog Title-")
        alertDialog.setMessage("Exit the current activity? -Dialog Message-")
        alertDialog.setPositiveButton("Yes") { dialog, which -> finish() }
        alertDialog.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun pickDate(view: View?) {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog: Dialog = DatePickerDialog(this,
            { view, year, month, dayOfMonth -> processDatePickerResult(year, month, dayOfMonth) },
            year,
            month,
            day)
        datePickerDialog.show()
    }

    fun pickTime(view: View?) {
        val newFragment: DialogFragment = TimePickerFragment()
        newFragment.show(supportFragmentManager, getString(R.string.time_picker))
    }

    fun processDatePickerResult(year: Int, month: Int, day: Int) {
        // The month integer returned by the date picker starts counting at 0
        // for January, so you need to add 1 to show months starting at 1.
        val month_string = Integer.toString(month + 1)
        val day_string = Integer.toString(day)
        val year_string = Integer.toString(year)
        // Assign the concatenated strings to dateMessage.
        val dateMessage = "$month_string/$day_string/$year_string"
        (findViewById<View>(R.id.txt_date) as TextView).text = "Date : $dateMessage"
    }

    fun processTimePickerResult(hourOfDay: Int, minute: Int) {
        // Convert time elements into strings.
        val hour_string = Integer.toString(hourOfDay)
        val minute_string = Integer.toString(minute)
        // Assign the concatenated strings to timeMessage.
        val timeMessage = "$hour_string:$minute_string"
        (findViewById<View>(R.id.txt_time) as TextView).text = "Time : $timeMessage"
    }

    var snackbar: Snackbar? = null
    fun showSnackbar(view: View?) {
        snackbar = Snackbar
            .make(view!!, "This is a Snackbar, show Toast?", Snackbar.LENGTH_LONG)
            .setAction("Yes") {
                Toast.makeText(applicationContext,
                    "Snackbar action button clicked",
                    Toast.LENGTH_LONG).show()
            }
        snackbar!!.setActionTextColor(Color.RED) //getResources().getColor(R.color.green));
        val sbView = snackbar!!.view
        val textView =
            sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.YELLOW)
        snackbar!!.show()
    }
}