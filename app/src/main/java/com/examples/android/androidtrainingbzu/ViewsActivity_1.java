package com.examples.android.androidtrainingbzu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.examples.android.androidtrainingbzu.Adapters.SpinnerCustomAdapter;

public class ViewsActivity_1 extends AppCompatActivity {

    RadioGroup radioGroup;
    Spinner spinner;
    String[] countryNames={"India","China","Australia","Portugle","America","New Zealand"};
    int flags[] = {R.drawable.bar_chart, R.drawable.emp_pic3, R.drawable.ic_app_name, R.drawable.ic_user_icon, R.drawable.rounded_butoon, R.drawable.ic_user_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_1);
        radioGroup=(RadioGroup)findViewById(R.id.rg_gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case 1:
                        Toast.makeText(getApplicationContext(), "Index 1",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Index 2",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        spinner=(Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"You select "+((TextView)view).getText().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spin = (Spinner) findViewById(R.id.spinner_custom);
        SpinnerCustomAdapter customAdapter=new SpinnerCustomAdapter(getApplicationContext(),flags,countryNames);
        spin.setAdapter(customAdapter);
    }

    public void showToastOfText(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
           Toast.makeText(this, "Button is On and text is :"+((ToggleButton) view).getText().toString(),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Button is Off and text is :"+((ToggleButton) view).getText().toString(),Toast.LENGTH_LONG).show();
        }
    }
}
