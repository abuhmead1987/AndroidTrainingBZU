package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.examples.android.androidtrainingbzu.Adapters.EmployeesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * An activity representing a list of Employees. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [EmployeeDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class EmployeeListActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(applicationContext, EmployeeDetailActivity::class.java)
            intent.putExtra(EmployeeDetailFragment.Companion.ARG_ITEM_ID, -1)
            startActivity(intent)
        }
        if (findViewById<View?>(R.id.employee_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        val recyclerView = findViewById<RecyclerView>(R.id.employee_list)
        recyclerView?.let { setupRecyclerView(it) }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter =
            EmployeesAdapter(this, HR_MainActivity.employeeLinkedList, mTwoPane)
    }

    override fun onResume() {
        super.onResume()
        (findViewById<View>(R.id.employee_list) as RecyclerView).adapter!!.notifyDataSetChanged()
    }
}