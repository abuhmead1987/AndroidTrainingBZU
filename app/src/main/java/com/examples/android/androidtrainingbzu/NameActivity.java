package com.examples.android.androidtrainingbzu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
    }

    public void finishActivity(View view) {
        Intent i=new Intent();
        i.putExtra("user_name",((EditText)findViewById(R.id.edt_name)).getText().toString());
        //finishActivity(RESULT_OK);
        setResult(RESULT_OK,i);
        finish();
    }

    public void cancelActivity(View view) {
        Intent i=new Intent();
        setResult(RESULT_CANCELED,i);
        finish();
    }

}
