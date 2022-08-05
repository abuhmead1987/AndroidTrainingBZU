package com.examples.android.androidtrainingbzu;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ForResultsActivity extends AppCompatActivity {
    private static final int NAME_ACTIVITY=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_results);
    }

    public void callActivityForResult(View view) {
        Intent intent=new Intent(this, NameActivity.class);
        startActivityForResult(intent,NAME_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            ((TextView) findViewById(R.id.txt_name)).setText(data.getStringExtra("user_name"));
        }else {
            Toast.makeText(this, "HUM!!!, you canceled the request",Toast.LENGTH_LONG).show();
        }
    }
}
