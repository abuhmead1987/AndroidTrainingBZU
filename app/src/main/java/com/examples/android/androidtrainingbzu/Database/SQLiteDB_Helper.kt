package com.examples.android.androidtrainingbzu.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.examples.android.androidtrainingbzu.Models.Employee
import com.examples.android.androidtrainingbzu.Utils.Utils
import java.util.*

class SQLiteDB_Helper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_DEFINITIONS.toString())
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE)
        onCreate(sqLiteDatabase)
    }

    fun insertEmployee(employee: Employee): Boolean {
        return try {
            databaseWritableManager!!.execSQL("INSERT INTO " + EMPLOYEE_TABLE +
                    "           (ID" +
                    "           ,name" +
                    "           ,email" +
                    "           ,phone" +
                    "           ,address" +
                    "           ,dept" +
                    "           ,picResPath" +
                    "           ,picResID" +
                    "           ,hireDate)" +
                    "     VALUES" +
                    "           (" + employee.id +
                    "           ,'" + employee.name + "'" +
                    "           ,'" + employee.email + "'" +
                    "           ,'" + employee.phone + "'" +
                    "           ,'" + employee.address + "'" +
                    "           ,'" + employee.dept + "'" +
                    "           ,'" + employee.picResPath + "'" +
                    "           ," + employee.picResID +
                    "           ,'" + employee.hireDate + "')")
            true
        } catch (ex: Exception) {
            Log.e("InsertEmployee", ex.message!!)
            false
        }
    }

    fun insertEmployees(employees: LinkedList<Employee>?): Boolean {
        return try {
            var contentValues: ContentValues
            databaseWritableManager!!.beginTransaction()
            for (employee in employees!!) {
                contentValues = ContentValues()
                if (employee != null) {
                    contentValues.put("ID", employee.id)
                    contentValues.put("name", employee.name)
                    contentValues.put("email", employee.email)
                    contentValues.put("phone", employee.phone)
                    contentValues.put("address", employee.address)
                    contentValues.put("dept", employee.dept)
                    contentValues.put("picResPath", employee.picResPath)
                    contentValues.put("picResID", employee.picResID)
                    contentValues.put("hireDate", "'" + employee.hireDate + "'")
                }
                databaseWritableManager!!.insert(EMPLOYEE_TABLE, null, contentValues)
            }
            databaseWritableManager!!.setTransactionSuccessful()
            true
        } catch (ex: SQLException) {
            Log.e("EmployeesTransaction", ex.message!!)
            false
        } finally {
            databaseWritableManager!!.endTransaction()
        }
    }

    val employeesCount: Int
        get() = try {
            val employeesCursor =
                databaseReadableManager!!.rawQuery("SELECT count(*)as count FROM " + EMPLOYEE_TABLE,
                    null)
            employeesCursor.moveToNext()
            val count = employeesCursor.getInt(0)
            employeesCursor.close()
            count
        } catch (ex: SQLException) {
            Log.e("getEmployees", ex.message!!)
            -1
        }

    val employees: LinkedList<Employee>?
        @SuppressLint("Range")
        get() = try {
            val employeesCursor =
                databaseReadableManager!!.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE, null)
            if (employeesCursor.count > 0) {
                val employeesList = LinkedList<Employee>()
                var emp: Employee
                var emp_id_col_index: Int
                while (employeesCursor.moveToNext()) {
                    emp_id_col_index = employeesCursor.getColumnIndex("name")
                    emp = Employee(
                        employeesCursor.getString(emp_id_col_index),
                        employeesCursor.getString(employeesCursor.getColumnIndex("email")),
                        employeesCursor.getString(employeesCursor.getColumnIndex("phone")),
                        employeesCursor.getString(employeesCursor.getColumnIndex("address")),
                        employeesCursor.getString(employeesCursor.getColumnIndex("dept")),
                        employeesCursor.getInt(employeesCursor.getColumnIndex("ID")),
                        employeesCursor.getInt(employeesCursor.getColumnIndex("picResID")),
                        Utils.getDateFromString(employeesCursor.getString(
                            employeesCursor.getColumnIndex("hireDate"))))
                    employeesList.add(emp)
                }
                employeesCursor.close();
                employeesList
            } else {
                null
            }
        } catch (ex: SQLException) {
            Log.e("getEmployees", ex.message!!)
            null
        }

    fun getEmployees(empID: String): LinkedList<Employee>? {
        return try {
            val employeesCursor =
                databaseReadableManager!!.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE + " where ID like '%?%' ",
                    arrayOf(empID))
            if (employeesCursor.count > 0) {
                val employeesList = LinkedList<Employee>()
                while (employeesCursor.moveToNext()) {
                    employeesList.add(Employee(employeesCursor.getString(employeesCursor.getColumnIndexOrThrow(
                        "name")),
                        employeesCursor.getString(employeesCursor.getColumnIndexOrThrow("email")),
                        employeesCursor.getString(employeesCursor.getColumnIndexOrThrow("phone")),
                        employeesCursor.getString(employeesCursor.getColumnIndexOrThrow("address")),
                        employeesCursor.getString(employeesCursor.getColumnIndexOrThrow("dept")),
                        employeesCursor.getInt(employeesCursor.getColumnIndexOrThrow("ID")),
                        employeesCursor.getInt(employeesCursor.getColumnIndexOrThrow("picResID")),
                        Utils.getDateFromString(employeesCursor.getString(employeesCursor.getColumnIndexOrThrow(
                            "hireDate")))))
                }
                employeesList
            } else {
                null
            }
        } catch (ex: SQLException) {
            Log.e("getEmployees", ex.message!!)
            null
        }
    }

    companion object {
        private const val DATABASE_VERSION = 1

        // Has to be 1 first time or app will crash.
        private const val DATABASE_NAME = "HR_Sys"
        private const val EMPLOYEE_TABLE = "Employees"
        private val DATABASE_DEFINITIONS = StringBuilder()
        private var databaseWritableManager: SQLiteDatabase? = null
        private var databaseReadableManager: SQLiteDatabase? = null
        private var instance: SQLiteDB_Helper? = null
        fun getInstance(context: Context): SQLiteDB_Helper? {
            if (instance == null) {
                instance = SQLiteDB_Helper(context)
            }
            return instance
        }
    }

    init {
        DATABASE_DEFINITIONS.append("CREATE TABLE " + EMPLOYEE_TABLE +
                " (ID INTEGER PRIMARY KEY, name TEXT, email TEXT, phone TEXT, address TEXT, dept TEXT, picResPath TEXT, picResID INTEGER, hireDate DATE );")
        if (databaseWritableManager == null) databaseWritableManager = writableDatabase
        if (databaseReadableManager == null) databaseReadableManager = readableDatabase
    }
}