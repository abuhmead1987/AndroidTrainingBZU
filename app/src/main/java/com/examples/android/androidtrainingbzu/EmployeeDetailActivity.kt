package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.examples.android.androidtrainingbzu.Models.Employee
import com.examples.android.androidtrainingbzu.Utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * An activity representing a single Employee detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [EmployeeListActivity].
 */
class EmployeeDetailActivity : AppCompatActivity() {
    private var fragment: EmployeeDetailFragment? = null
    private var empListIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            if (fragment != null) {
                val rootView = fragment!!.view
                var emp: Employee = Employee()
                if (empListIndex == -1) {
                    emp = Employee()
                    emp.picResID = R.drawable.emp_pic7
                    HR_MainActivity.employeeLinkedList!!.add(emp)
                } else {
                    emp = HR_MainActivity.employeeLinkedList!!.get(empListIndex)!!
                }
                emp.name =
                    rootView!!.findViewById<EditText>(R.id.edt_name) .text.toString()
                emp.email =
                    rootView.findViewById<EditText>(R.id.edt_email) .text.toString()
                emp.phone =
                    rootView.findViewById<EditText>(R.id.edt_phone) .text.toString()
                emp.address =
                    rootView.findViewById<EditText>(R.id.edt_address) .text.toString()
                emp.hireDate =
                    Utils.getDateFromString((rootView.findViewById<View>(R.id.edt_date) as TextView).text.toString())
            }
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            Snackbar.make(view, "Employee Info Saved...", Snackbar.LENGTH_LONG).show()
        }


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val arguments = Bundle()
            empListIndex = intent.getIntExtra(EmployeeDetailFragment.Companion.ARG_ITEM_ID, 0)
            arguments.putInt(EmployeeDetailFragment.Companion.ARG_ITEM_ID, empListIndex)
            fragment = EmployeeDetailFragment()
            fragment!!.arguments = arguments
            supportFragmentManager.beginTransaction()
                .add(R.id.employee_detail_container, fragment!!)
                .commit()
        }
    }
}