package com.examples.android.androidtrainingbzu

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.examples.android.androidtrainingbzu.Models.Employee
import com.examples.android.androidtrainingbzu.Utils.Utils
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.util.*

/**
 * A fragment representing a single Employee detail screen.
 * This fragment is either contained in a [HR_MainActivity]
 * in two-pane mode (on tablets) or a [EmployeeDetailActivity]
 * on handsets.
 */
class EmployeeDetailFragment
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
    : Fragment(), OnDateSetListener {
    private var txtvu_hireDate: TextView? = null

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: Employee? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireArguments().containsKey(ARG_ITEM_ID)) {
            val empIndex = requireArguments().getInt(ARG_ITEM_ID)
//            if (empIndex > -1) mItem = HR_MainActivity.Companion.employeeLinkedList!!.get(empIndex)
            val activity: Activity? = this.activity
            val appBarLayout =
                activity!!.findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
            if (appBarLayout != null) {
                if (empIndex > -1) appBarLayout.title = mItem?.name else appBarLayout.title =
                    "New Emp!"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.employee_info_edit, container, false)
        txtvu_hireDate = rootView.findViewById<View>(R.id.edt_date) as TextView
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            (requireActivity().findViewById<View>(R.id.imgvu_emp_pic) as ImageView).setImageResource(
                mItem!!.picResID)
            (rootView.findViewById<View>(R.id.edt_name) as EditText).setText(mItem!!.name)
            (rootView.findViewById<View>(R.id.edt_email) as EditText).setText(mItem!!.email)
            (rootView.findViewById<View>(R.id.edt_phone) as EditText).setText(mItem!!.phone)
            (rootView.findViewById<View>(R.id.edt_address) as EditText).setText(mItem!!.address)
            txtvu_hireDate!!.text = Utils.getDateFormatedString(mItem!!.hireDate)
        }
        txtvu_hireDate!!.setOnClickListener {
            val year: Int
            val month: Int
            val day: Int
            val date = txtvu_hireDate!!.text.toString().split("\\.").toTypedArray()
            if (date.size != 3) {
                val c = Calendar.getInstance()
                year = c[Calendar.YEAR]
                month = c[Calendar.MONTH]
                day = c[Calendar.DAY_OF_MONTH]
            } else {
                year = date[2].toInt()
                month = date[1].toInt()
                day = date[0].toInt()
            }

            // Create a new instance of DatePickerDialog and return it.
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this@EmployeeDetailFragment, year, month, day)
            datePickerDialog.show()
        }
        return rootView
    }

    /**
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     * [Calendar.MONTH])
     * @param dayOfMonth th selected day of the month (1-31, depending on
     */
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        var month = month
        txtvu_hireDate!!.text = "" + dayOfMonth + "." + ++month + "." + year
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}