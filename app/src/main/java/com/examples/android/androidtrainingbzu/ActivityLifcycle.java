package com.examples.android.androidtrainingbzu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ActivityLifcycle extends AppCompatActivity {

    private static final String TAG ="ActivityLifcycle" ;

    private TextView txt_counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifcycle);
        txt_counter=findViewById(R.id.txt_counter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("count",txt_counter.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        txt_counter.setText(savedInstanceState.getString("count"));
    }

    public void incrementNumber(View view) {
        int count=Integer.parseInt(txt_counter.getText().toString());
        txt_counter.setText(""+ ++count);
    }
}
