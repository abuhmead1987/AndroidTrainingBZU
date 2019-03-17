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
                startActivity(new Intent(this,ActivityLifcycle.class));
                break;
            case R.id.btn_startActivityResul:
                startActivity(new Intent(this,ForResultsActivity.class));
                break;
            case R.id.btn_startimplicitActivity:
                startActivity(new Intent(this,ImplicitActivity.class));
                break;
            case R.id.btn_startView1Activity:
                startActivity(new Intent(this,ViewsActivity_1.class));
                break;
            case R.id.btn_startWebviewActivity:
                startActivity(new Intent(this,WebviewActivity.class));
                break;
            case R.id.btn_startDialogsActivity:
                startActivity(new Intent(this,DialogsActivity.class));
                break;
            case R.id.btn_startScaleGestureDetectorActivity:
                startActivity(new Intent(this,ScaleGestureDetectorActivity.class));
                break;
            case R.id.btn_startMotionEventActivity:
                startActivity(new Intent(this,MotionEventActivity.class));
                break;
            case R.id.btn_startMenuActivity:
                startActivity(new Intent(this,MenusActivity.class));
                break;
            case R.id.btn_startTabsActivity:
                startActivity(new Intent(this,TabsActivity.class));
                break;
            case R.id.btn_startRecyclerViewActivity:
                startActivity(new Intent(this,HR_MainActivity.class));
                break;

        }
    }
}
