package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.examples.android.androidtrainingbzu.Adapters.SpinnerCustomAdapter

class ViewsActivity_1 : AppCompatActivity() {
    var radioGroup: RadioGroup? = null
    var spinner: Spinner? = null
    var countryNames = arrayOf("India", "China", "Australia", "Portugle", "America", "New Zealand")
    var flags = intArrayOf(R.drawable.bar_chart,
        R.drawable.emp_pic3,
        R.drawable.ic_app_name,
        R.drawable.ic_user_icon,
        R.drawable.rounded_butoon,
        R.drawable.ic_user_icon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views_1)
        radioGroup = findViewById<View>(R.id.rg_gender) as RadioGroup
        radioGroup!!.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                1 -> Toast.makeText(applicationContext, "Index 1", Toast.LENGTH_LONG).show()
                2 -> Toast.makeText(applicationContext, "Index 2", Toast.LENGTH_LONG).show()
            }
        }
        spinner = findViewById<View>(R.id.spinner) as Spinner
        spinner!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                Toast.makeText(applicationContext,
                    "You select " + (view as TextView).text.toString(),
                    Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        val spin = findViewById<View>(R.id.spinner_custom) as Spinner
        val customAdapter = SpinnerCustomAdapter(applicationContext, flags, countryNames)
        spin.adapter = customAdapter
    }

    fun showToastOfText(view: View) {
        val on = (view as ToggleButton).isChecked
        if (on) {
            Toast.makeText(this,
                "Button is On and text is :" + view.text.toString(),
                Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,
                "Button is Off and text is :" + view.text.toString(),
                Toast.LENGTH_LONG).show()
        }
    }
}