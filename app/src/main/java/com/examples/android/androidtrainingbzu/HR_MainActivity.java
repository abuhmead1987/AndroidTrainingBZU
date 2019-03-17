package com.examples.android.androidtrainingbzu;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.examples.android.androidtrainingbzu.Models.Employee;
import com.examples.android.androidtrainingbzu.Utils.DataSource;
import com.examples.android.androidtrainingbzu.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class HR_MainActivity extends AppCompatActivity{
    public static LinkedList<Employee> employeeLinkedList = new LinkedList();
    private final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr__main);
    }

    public void startActivity() {
        Intent intent = new Intent(getApplicationContext(), EmployeeListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void callInitialize(View view) {
        switch (view.getId()) {
            case R.id.btn_inialize:
                new AsyncInitializerTask().execute(DataSource.INITIALIZE);
//                employeeLinkedList.clear();
//                Employee e;
//                int empID = 1;
//                String[] depts = {"Accounting", "HR", "IT"};
//                int[] avatars = {R.drawable.emp_pic1, R.drawable.emp_pic2, R.drawable.emp_pic3, R.drawable.emp_pic4, R.drawable.emp_pic5, R.drawable.emp_pic6, R.drawable.emp_pic7};
//                for (int i = 0; i < 20; i++) {
//                    e = new Employee("Emplyee " + empID + " Name ", "Emplyee " + empID + " Email ", "Emplyee " + empID + " Phone ",
//                            "Emplyee " + empID + " Address ", depts[i % 3], empID++, avatars[i % 7], Utils.getDateFromString("22.05.2016"));
//                    employeeLinkedList.addLast(e);
//                }
//                startActivity();
                break;
//            case R.id.btn_loadFromFirebase:
//                new AsyncInitializerTask().execute(DataSource.FIREBASE);
//                break;
//            case R.id.btn_LoadFromSQLite:
//                new AsyncInitializerTask().execute(DataSource.SQLIT_DB);
//                break;
//            case R.id.btn_LoadFromWebAPI:
//                new AsyncInitializerTask().execute(DataSource.WEB_API);
//                break;
        }
    }

    public interface IsDataFilled {
        public void fillEmpList(boolean result);
    }

    private class AsyncInitializerTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(HR_MainActivity.this, EmployeeListActivity.class));
        }

        @Override
        protected Void doInBackground(String... strings) {
            fillEmpList(strings[0]);
            return null;
        }

        private void fillEmpList(String source) {
            switch (source) {
                case DataSource.INITIALIZE:
                    employeeLinkedList.clear();
                    Employee e;
                    int empID = 1;
                    String[] depts = {"Accounting", "HR", "IT"};
                    int[] avatars = {R.drawable.emp_pic1, R.drawable.emp_pic2, R.drawable.emp_pic3, R.drawable.emp_pic4, R.drawable.emp_pic5, R.drawable.emp_pic6, R.drawable.emp_pic7};
                    for (int i = 0; i < 20; i++) {
                        e = new Employee("Emplyee " + empID + " Name ", "Emplyee " + empID + " Email ", "Emplyee " + empID + " Phone ",
                                "Emplyee " + empID + " Address ", depts[i % 3], empID++, avatars[i % 7], Utils.getDateFromString("22.05.2016"));
                        employeeLinkedList.addLast(e);
                    }
                    break;
//                case DataSource.FIREBASE:
//                    firebaseDB_helper.getEmpRef().addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.hasChildren()) {
//                                employeeLinkedList.clear();
//                                Employee e;
//                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    //Utils.getDateFromString("22.05.2016")
//                                    e = new Employee(ds.child("name").getValue(String.class)
//                                            , ds.child("email").getValue(String.class),
//                                            ds.child("phone").getValue(String.class),
//                                            ds.child("address").getValue(String.class),
//                                            ds.child("dept").getValue(String.class),
//                                            ds.child("id").getValue(Integer.class),
//                                            ds.child("picResID").getValue(Integer.class),
//                                            ds.child("hireDate").getValue(Date.class)
//                                    );
//                                    employeeLinkedList.addLast(e);
//                                }
//                                Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            } else {
//                                fillEmpList(DataSource.INITIALIZE);
//                                fillEmpList(DataSource.FIREBASE);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            // ...
//                        }
//                    });
//                    break;
//                case DataSource.SQLIT_DB:
//                    SQLiteDB_Helper ss = SQLiteDB_Helper.getInstance(MainActivity.this);
//                    if (ss.getEmployeesCount() > 0) {
//                        employeeLinkedList.clear();
//                        employeeLinkedList = ss.getEmployees();
//                    } else {
//                        fillEmpList(DataSource.INITIALIZE);
//                        //ss.insertEmployees(employeeLinkedList);
//                        for (Employee ee : employeeLinkedList) {
//                            ss.insertEmployee(ee);
//                        }
//                        fillEmpList(DataSource.SQLIT_DB);
//                    }
//                    break;
//                case DataSource.WEB_API:
//                    new JsonTask(MainActivity.this).execute("http://192.168.137.1:8070/APIs/EmpAPIService.svc/getEmployees");
//
//                    break;
            }

        }

    }

    public class JsonTask extends AsyncTask<String, String, String> {

        private Context context;

        public JsonTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("Result", result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String isSuccess = jsonObject.getString("status");
                if (isSuccess.equals("Success")) {
                    JSONArray empList = jsonObject.getJSONArray("data");
                    JSONObject emp = null;
                    Employee e = null;
                    HR_MainActivity.employeeLinkedList.clear();
                    for (int i = 0; i < empList.length(); i++) {
                        emp = empList.getJSONObject(i);
                        e = new Employee(emp.getString("name"),
                                emp.getString("email"),
                                emp.getString("phone"),
                                emp.getString("address"),
                                emp.getString("dept"),
                                emp.getInt("ID"),
                                emp.getInt("picResID"),
                                null);
                        HR_MainActivity.employeeLinkedList.add(e);
                    }
                    Intent intent = new Intent(getApplicationContext(), EmployeeListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
