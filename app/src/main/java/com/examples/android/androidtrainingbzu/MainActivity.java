package com.examples.android.androidtrainingbzu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startNewActivity(View view) {
        switch (view.getId()){
            case R.id.btn_startLongActivity:
                startActivity(new Intent(this,LongTextActivity.class));
                break;
            case R.id.btn_startLifecycleActivity:
                startActivity(new Intent(this,ActivityLifcycle.class));//startActivity(new Intent(this,LifecycleOfActivity.class));
                break;
            case R.id.btn_startActivityResul:
                startActivity(new Intent(this,ForResultsActivity.class));//startActivity(new Intent(this,LifecycleOfActivity.class));
                break;
            case R.id.btn_startimplicitActivity:
                startActivity(new Intent(this,ImplicitActivity.class));//startActivity(new Intent(this,LifecycleOfActivity.class));
                break;
            case R.id.btn_startView1Activity:
                startActivity(new Intent(this,ViewsActivity_1.class));//startActivity(new Intent(this,LifecycleOfActivity.class));
                break;

        }
    }
}
