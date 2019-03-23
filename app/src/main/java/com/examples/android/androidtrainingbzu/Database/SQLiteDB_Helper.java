package com.examples.android.androidtrainingbzu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.examples.android.androidtrainingbzu.Models.Employee;
import com.examples.android.androidtrainingbzu.Utils.Utils;

import java.util.LinkedList;

public class SQLiteDB_Helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Has to be 1 first time or app will crash.
    private static final String DATABASE_NAME = "HR_Sys";
    private static final String EMPLOYEE_TABLE = "Employees";
    private static final StringBuilder DATABASE_DEFINITIONS = new StringBuilder();
    private static SQLiteDatabase databaseWritableManager;
    private static SQLiteDatabase databaseReadableManager;
    private static SQLiteDB_Helper instance = null;

    private SQLiteDB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_DEFINITIONS.append("CREATE TABLE " + EMPLOYEE_TABLE +
                " (ID INTEGER PRIMARY KEY, name TEXT, email TEXT, phone TEXT, address TEXT, dept TEXT, picResPath TEXT, picResID INTEGER, hireDate DATE );");
        if (databaseWritableManager == null)
            databaseWritableManager = getWritableDatabase();
        if (databaseReadableManager == null)
            databaseReadableManager = getReadableDatabase();
    }

    public static SQLiteDB_Helper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteDB_Helper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_DEFINITIONS.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertEmployee(Employee employee) {
        try {
            databaseWritableManager.execSQL("INSERT INTO " + EMPLOYEE_TABLE +
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
                    "           (" + employee.getID() +
                    "           ,'" + employee.getName() + "'" +
                    "           ,'" + employee.getEmail() + "'" +
                    "           ,'" + employee.getPhone() + "'" +
                    "           ,'" + employee.getAddress() + "'" +
                    "           ,'" + employee.getDept() + "'" +
                    "           ,'" + employee.getPicResPath() + "'" +
                    "           ," + employee.getPicResID() +
                    "           ,'" + employee.getHireDate() + "')");
            return true;
        } catch (Exception ex) {
            Log.e("InsertEmployee", ex.getMessage());
            return false;
        }
    }

    public boolean insertEmployees(LinkedList<Employee> employees) {
        try {
            ContentValues contentValues;
            databaseWritableManager.beginTransaction();
            for (Employee employee : employees) {
                contentValues = new ContentValues();
                contentValues.put("ID", employee.getID());
                contentValues.put("name", employee.getName());
                contentValues.put("email", employee.getEmail());
                contentValues.put("phone", employee.getPhone());
                contentValues.put("address", employee.getAddress());
                contentValues.put("dept", employee.getDept());
                contentValues.put("picResPath", employee.getPicResPath());
                contentValues.put("picResID", employee.getPicResID());
                contentValues.put("hireDate", "'" + employee.getHireDate() + "'");
                databaseWritableManager.insert(EMPLOYEE_TABLE, null, contentValues);
            }
            databaseWritableManager.setTransactionSuccessful();
            return true;
        } catch (SQLException ex) {
            Log.e("EmployeesTransaction", ex.getMessage());
            return false;
        } finally {
            databaseWritableManager.endTransaction();
        }
    }

    public int getEmployeesCount() {
        try {
            Cursor employeesCursor = databaseReadableManager.rawQuery("SELECT count(*)as count FROM " + EMPLOYEE_TABLE, null);
            employeesCursor.moveToNext();
            int count = employeesCursor.getInt(0);
            employeesCursor.close();
            return count;
        } catch (SQLException ex) {
            Log.e("getEmployees", ex.getMessage());
            return -1;
        }
    }

    public LinkedList<Employee> getEmployees() {
        try {
            Cursor employeesCursor = databaseReadableManager.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE, null);
            if (employeesCursor.getCount() > 0) {
                LinkedList<Employee> employeesList = new LinkedList<>();
                Employee emp;
                int emp_id_col_index;
                while (employeesCursor.moveToNext()) {
                    emp_id_col_index=employeesCursor.getColumnIndex("name");
                    emp=new Employee(
                            employeesCursor.getString(emp_id_col_index),
                            employeesCursor.getString(employeesCursor.getColumnIndex("email")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("phone")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("address")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("dept")),
                            employeesCursor.getInt(employeesCursor.getColumnIndex("ID")),
                            employeesCursor.getInt(employeesCursor.getColumnIndex("picResID")),
                            Utils.getDateFromString(employeesCursor.getString(
                                    employeesCursor.getColumnIndex("hireDate"))));
                    employeesList.add(emp);
                }
                // employeesCursor.close();
                return employeesList;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Log.e("getEmployees", ex.getMessage());
            return null;
        }
    }

    public LinkedList<Employee> getEmployees(String empID) {
        try {
            Cursor employeesCursor = databaseReadableManager.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE + " where ID like '%?%' ", new String[]{empID});
            if (employeesCursor.getCount() > 0) {
                LinkedList<Employee> employeesList = new LinkedList<>();
                while (employeesCursor.moveToNext()) {
                    employeesList.add(new Employee(employeesCursor.getString(employeesCursor.getColumnIndex("name")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("email")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("phone")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("address")),
                            employeesCursor.getString(employeesCursor.getColumnIndex("dept")),
                            employeesCursor.getInt(employeesCursor.getColumnIndex("ID")),
                            employeesCursor.getInt(employeesCursor.getColumnIndex("picResID")),
                            Utils.getDateFromString(employeesCursor.getString(employeesCursor.getColumnIndex("hireDate")))));
                }
                return employeesList;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Log.e("getEmployees", ex.getMessage());
            return null;
        }
    }
}
