package com.examples.android.androidtrainingbzu.Database;

import android.content.Context;

import com.examples.android.androidtrainingbzu.Models.Employee;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;

public class FirebaseDB_Helper {

    private FirebaseDatabase database = null;
    private DatabaseReference empRef = null;
    private DatabaseReference deptsRef = null;

    private  static FirebaseDB_Helper firebaseDB_helperInstance=null;

    private FirebaseDB_Helper(Context context) {
        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();
        empRef = database.getReference("Employees");
        deptsRef = database.getReference("Departments");
    }
    public static FirebaseDB_Helper getInstance(Context context){
        if (firebaseDB_helperInstance ==null){
            firebaseDB_helperInstance=new FirebaseDB_Helper(context);
        }
        return firebaseDB_helperInstance;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
    public DatabaseReference getEmpRef() {
        return empRef;
    }
    public DatabaseReference getDeptsRef() {
        return deptsRef;
    }

    public boolean updateEmployees(LinkedList<Employee> employeeLinkedList) {
        try {
            for (Employee e : employeeLinkedList) {
                empRef.child("Emp_" + e.getID()).setValue(e);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean insertOrUpdateEmployee(Employee employee) {
        try {
            empRef.child("Emp_" + employee.getID()).setValue(employee);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
