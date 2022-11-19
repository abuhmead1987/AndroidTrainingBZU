package com.examples.android.androidtrainingbzu.Database

import android.content.Context
import com.examples.android.androidtrainingbzu.Models.Employee
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class FirebaseDB_Helper private constructor(context: Context) {
    var database: FirebaseDatabase? = null
    var empRef: DatabaseReference? = null
    var deptsRef: DatabaseReference? = null
    fun updateEmployees(employeeLinkedList: LinkedList<Employee>): Boolean {
        return try {
            for (e in employeeLinkedList) {
                empRef!!.child("Emp_" + e.id).setValue(e)
            }
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun insertOrUpdateEmployee(employee: Employee): Boolean {
        return try {
            empRef!!.child("Emp_" + employee.id).setValue(employee)
            true
        } catch (ex: Exception) {
            false
        }
    }

    companion object {
        private var firebaseDB_helperInstance: FirebaseDB_Helper? = null
        fun getInstance(context: Context): FirebaseDB_Helper? {
            if (firebaseDB_helperInstance == null) {
                firebaseDB_helperInstance = FirebaseDB_Helper(context)
            }
            return firebaseDB_helperInstance
        }
    }

    init {
        FirebaseApp.initializeApp(context)
        database = FirebaseDatabase.getInstance()
        empRef = database!!.getReference("Employees")
        deptsRef = database!!.getReference("Departments")
    }
}