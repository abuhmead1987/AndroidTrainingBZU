package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.examples.android.androidtrainingbzu.Database.FirebaseDB_Helper
import com.examples.android.androidtrainingbzu.Database.SQLiteDB_Helper
import com.examples.android.androidtrainingbzu.Models.Employee
import com.examples.android.androidtrainingbzu.Utils.DataSource
import com.examples.android.androidtrainingbzu.Utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

class HR_MainActivity : AppCompatActivity() {
    private val TAG = HR_MainActivity::class.java.simpleName
    var firebaseDB_helper: FirebaseDB_Helper? = null

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        var employeeLinkedList: LinkedList<Employee>? = LinkedList<Employee>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr__main)
        firebaseDB_helper = FirebaseDB_Helper.Companion.getInstance(this)
    }

    fun startActivity() {
        val intent = Intent(applicationContext, EmployeeListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun callInitialize(view: View) {
        executor.execute {
            when (view.id) {
                R.id.btn_inialize -> {
                    fillListItemsFrom(DataSource.INITIALIZE)
                }
                R.id.btn_LoadFromSQLite -> {
                    fillListItemsFrom(DataSource.SQLIT_DB)
                }
                R.id.btn_LoadFromWebAPI -> {
                    fillListItemsFrom(DataSource.WEB_API)
                }
                R.id.btn_loadFromFirebase -> {
                    fillListItemsFrom(DataSource.FIREBASE)
                }
            }
        }
        handler.post {
            startActivity(Intent(this@HR_MainActivity, EmployeeListActivity::class.java))
        }
    }

    interface IsDataFilled {
        fun fillEmpList(result: Boolean)
    }

    private fun fillListItemsFrom(source: String?) {
        when (source) {
            DataSource.INITIALIZE -> {
                employeeLinkedList!!.clear()
                var e: Employee
                var empID = 1
                val depts = arrayOf("Accounting", "HR", "IT")
                val avatars = intArrayOf(R.drawable.emp_pic1,
                    R.drawable.emp_pic2,
                    R.drawable.emp_pic3,
                    R.drawable.emp_pic4,
                    R.drawable.emp_pic5,
                    R.drawable.emp_pic6,
                    R.drawable.emp_pic7)
                var i = 0
                while (i < 20) {
                    e = Employee("Emplyee $empID Name ",
                        "Emplyee $empID Email ",
                        "Emplyee $empID Phone ",
                        "Emplyee $empID Address ",
                        depts[i % 3],
                        empID++,
                        avatars[i % 7],
                        Utils.getDateFromString("22.05.2016"))
                    employeeLinkedList!!.addLast(e)
                    i++
                }
            }
            DataSource.SQLIT_DB -> {
                val sqlitedbHelper: SQLiteDB_Helper? =
                    SQLiteDB_Helper.Companion.getInstance(this@HR_MainActivity)

                if (sqlitedbHelper?.employeesCount!! > 0) {
                    employeeLinkedList!!.clear()
                    employeeLinkedList = sqlitedbHelper.employees
                } else {
                    sqlitedbHelper.insertEmployees(employeeLinkedList)
                    //                        for (Employee ee : employeeLinkedList) {
//                            ss.insertEmployee(ee);
//                        }
                    fillListItemsFrom(DataSource.SQLIT_DB)
                }
            }
            DataSource.WEB_API -> {
                var connection: HttpURLConnection? = null
                var reader: BufferedReader? = null
                try {
                    val url = URL("http://192.168.43.36:8070/APIs/EmpAPIService.svc/getEmployees")
//                    connection = url.openConnection() as HttpURLConnection
//                    connection.connect()
//                    val stream = connection.inputStream
//                    reader = BufferedReader(InputStreamReader(stream))
//                    val buffer = StringBuffer()
//                    var line = ""
//                    while (reader.readLine().also { line = it } != null) {
//                        buffer.append("""$line""".trimIndent())
//                    }

                    val jsonObject = JSONObject(url.readText())
                    val isSuccess = jsonObject.getString("status")
                    if (isSuccess == "Success") {
                        val empList = jsonObject.getJSONArray("data")
                        var emp: JSONObject? = null
                        var e: Employee? = null
                        employeeLinkedList!!.clear()
                        for (i in 0 until empList.length()) {
                            emp = empList.getJSONObject(i)
                            e = Employee(emp.getString("name"),
                                emp.getString("email"),
                                emp.getString("phone"),
                                emp.getString("address"),
                                emp.getString("dept"),
                                emp.getInt("ID"),
                                emp.getInt("picResID"),
                                null)
                            employeeLinkedList!!.add(e)
                        }
                    }
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    connection?.disconnect()
//                    try {
//                        reader!!.close()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
                }
            }

            DataSource.FIREBASE -> firebaseDB_helper?.empRef?.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        employeeLinkedList!!.clear()
                        var e: Employee
                        for (ds in dataSnapshot.children) {
                            //Utils.getDateFromString("22.05.2016")
                            e = Employee(ds.child("name").getValue(String::class.java),
                                ds.child("email").getValue(String::class.java),
                                ds.child("phone").getValue(String::class.java),
                                ds.child("address").getValue(String::class.java),
                                ds.child("dept").getValue(String::class.java),
                                ds.child("id").getValue(Int::class.java)!!,
                                ds.child("picResID").getValue(Int::class.java)!!,
                                ds.child("hireDate").getValue(Date::class.java)
                            )
                            employeeLinkedList!!.addLast(e)
                        }
                        val intent =
                            Intent(this@HR_MainActivity, EmployeeListActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else if (employeeLinkedList!!.size > 0) {
                        val empRef = firebaseDB_helper?.empRef
                        for (e in employeeLinkedList!!) {
                            empRef!!.child("Emp_" + e.id).setValue(e)
                        }
                        fillListItemsFrom(DataSource.FIREBASE)
                    } else {
                        fillListItemsFrom(DataSource.INITIALIZE)
                        fillListItemsFrom(DataSource.FIREBASE)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // ...
                }
            })
        }
    }
//    inner class AsyncInitializerTask : AsyncTask<String?, Void?, Void?>() {
//        override fun onPostExecute(aVoid: Void?) {
//            super.onPostExecute(aVoid)
//            startActivity(Intent(this@HR_MainActivity, EmployeeListActivity::class.java))
//        }
//
//        protected override fun doInBackground(vararg strings: String): Void? {
//            fillEmpList(strings[0])
//            return null
//        }
//
//        private fun fillEmpList(source: String?) {
//            when (source) {
////                DataSource.INITIALIZE -> {
////                    employeeLinkedList!!.clear()
////                    var e: Employee
////                    var empID = 1
////                    val depts = arrayOf("Accounting", "HR", "IT")
////                    val avatars = intArrayOf(R.drawable.emp_pic1,
////                        R.drawable.emp_pic2,
////                        R.drawable.emp_pic3,
////                        R.drawable.emp_pic4,
////                        R.drawable.emp_pic5,
////                        R.drawable.emp_pic6,
////                        R.drawable.emp_pic7)
////                    var i = 0
////                    while (i < 20) {
////                        e = Employee("Emplyee $empID Name ",
////                            "Emplyee $empID Email ",
////                            "Emplyee $empID Phone ",
////                            "Emplyee $empID Address ",
////                            depts[i % 3],
////                            empID++,
////                            avatars[i % 7],
////                            Utils.getDateFromString("22.05.2016"))
////                        employeeLinkedList!!.addLast(e)
////                        i++
////                    }
////                }
//                DataSource.FIREBASE -> firebaseDB_helper.getEmpRef()
//                    .addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            if (dataSnapshot.hasChildren()) {
//                                employeeLinkedList!!.clear()
//                                var e: Employee
//                                for (ds in dataSnapshot.children) {
//                                    //Utils.getDateFromString("22.05.2016")
//                                    e = Employee(ds.child("name").getValue(String::class.java),
//                                        ds.child("email").getValue(String::class.java),
//                                        ds.child("phone").getValue(String::class.java),
//                                        ds.child("address").getValue(String::class.java),
//                                        ds.child("dept").getValue(String::class.java),
//                                        ds.child("id").getValue(Int::class.java)!!,
//                                        ds.child("picResID").getValue(Int::class.java)!!,
//                                        ds.child("hireDate").getValue(Date::class.java)
//                                    )
//                                    employeeLinkedList!!.addLast(e)
//                                }
//                                val intent =
//                                    Intent(this@HR_MainActivity, EmployeeListActivity::class.java)
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                startActivity(intent)
//                            } else if (employeeLinkedList!!.size > 0) {
//                                val empRef = firebaseDB_helper.getEmpRef()
//                                for (e in employeeLinkedList!!) {
//                                    empRef!!.child("Emp_" + e.getID()).setValue(e)
//                                }
//                                fillEmpList(DataSource.FIREBASE)
//                            } else {
//                                fillEmpList(DataSource.INITIALIZE)
//                                fillEmpList(DataSource.FIREBASE)
//                            }
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            // ...
//                        }
//                    })
//                DataSource.SQLIT_DB -> {
//                    val ss: SQLiteDB_Helper =
//                        SQLiteDB_Helper.Companion.getInstance(this@HR_MainActivity)
//                    if (ss.employeesCount > 0) {
//                        employeeLinkedList!!.clear()
//                        employeeLinkedList = ss.employees
//                    } else {
//                        fillEmpList(DataSource.INITIALIZE)
//                        ss.insertEmployees(employeeLinkedList)
//                        //                        for (Employee ee : employeeLinkedList) {
////                            ss.insertEmployee(ee);
////                        }
//                        fillEmpList(DataSource.SQLIT_DB)
//                    }
//                }
//                DataSource.WEB_API -> JsonTask(this@HR_MainActivity).execute("http://172.19.16.180:8070/APIs/EmpAPIService.svc/getEmployees")
//            }
//        }
//    }
//
//    inner class JsonTask(private val context: Context) : AsyncTask<String?, String?, String?>() {
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        protected override fun doInBackground(vararg params: String): String? {
//            var connection: HttpURLConnection? = null
//            var reader: BufferedReader? = null
//            try {
//                val url = URL(params[0])
//                connection = url.openConnection() as HttpURLConnection
//                connection!!.connect()
//                val stream = connection.inputStream
//                reader = BufferedReader(InputStreamReader(stream))
//                val buffer = StringBuffer()
//                var line = ""
//                while (reader.readLine().also { line = it } != null) {
//                    buffer.append("""
//    $line
//
//    """.trimIndent())
//                }
//                return buffer.toString()
//            } catch (e: MalformedURLException) {
//                e.printStackTrace()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            } finally {
//                connection?.disconnect()
//                try {
//                    reader?.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            Log.i("Result", result!!)
//            try {
//                val jsonObject = JSONObject(result)
//                val isSuccess = jsonObject.getString("status")
//                if (isSuccess == "Success") {
//                    val empList = jsonObject.getJSONArray("data")
//                    var emp: JSONObject? = null
//                    var e: Employee? = null
//                    employeeLinkedList!!.clear()
//                    for (i in 0 until empList.length()) {
//                        emp = empList.getJSONObject(i)
//                        e = Employee(emp.getString("name"),
//                            emp.getString("email"),
//                            emp.getString("phone"),
//                            emp.getString("address"),
//                            emp.getString("dept"),
//                            emp.getInt("ID"),
//                            emp.getInt("picResID"),
//                            null)
//                        employeeLinkedList!!.add(e)
//                    }
//                    val intent = Intent(applicationContext, EmployeeListActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                }
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }
//    }


}