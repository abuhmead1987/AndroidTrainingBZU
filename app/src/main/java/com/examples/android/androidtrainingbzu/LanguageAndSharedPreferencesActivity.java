package com.examples.android.androidtrainingbzu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.Locale;

public class LanguageAndSharedPreferencesActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_and_shared_preferences);
        ToggleButton logalToggle = findViewById(R.id.toggle_lang);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String local=sharedpreferences.getString("language_prefix","en");
        if(local.equals("ar")){
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Resources res = getResources();
            Configuration config = new Configuration(res.getConfiguration());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale);
                config.setLayoutDirection(locale);
            }else{
                ViewCompat.setLayoutDirection(findViewById(R.id.tv_localText), ViewCompat.LAYOUT_DIRECTION_RTL);
            }

            res.updateConfiguration(config, res.getDisplayMetrics());
            logalToggle.setChecked(true);
        }



        logalToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String language = "en";
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Configuration config;
                Resources res;
                Locale locale;
                if (buttonView.isChecked()) {
                    language = "ar";
                    editor.putString("language_prefix", "ar");
                    locale = new Locale(language);
                    Locale.setDefault(locale);
                    res = getResources();
                    config = new Configuration(res.getConfiguration());
                    config.locale = locale;
                    res.updateConfiguration(config, res.getDisplayMetrics());
                } else {
                    editor.putString("language_prefix", "en");
                    locale = new Locale(language);
                    Locale.setDefault(locale);
                    res = getResources();
                    config = new Configuration(res.getConfiguration());
                    config.locale = locale;

                }
                editor.apply();
                editor.commit();
                res.updateConfiguration(config, res.getDisplayMetrics());
                Intent refresh = new Intent(getApplicationContext(), LanguageAndSharedPreferencesActivity.class);
                startActivity(refresh);
                finish();
            }
        });
    }

}
