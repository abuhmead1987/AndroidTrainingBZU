package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.examples.android.androidtrainingbzu.LanguageAndSharedPreferencesActivity
import java.util.*

class LanguageAndSharedPreferencesActivity : AppCompatActivity() {
    lateinit var sharedpreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_and_shared_preferences)
        val logalToggle = findViewById<ToggleButton>(R.id.toggle_lang)
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        val local = sharedpreferences.getString("language_prefix", "en")
        if (local == "ar") {
            val locale = Locale("ar")
            Locale.setDefault(locale)
            val res = resources
            val config = Configuration(res.configuration)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale)
                config.setLayoutDirection(locale)
            } else {
                ViewCompat.setLayoutDirection(findViewById(R.id.tv_localText),
                    ViewCompat.LAYOUT_DIRECTION_RTL)
            }
            res.updateConfiguration(config, res.displayMetrics)
            logalToggle.isChecked = true
        }
        logalToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            var language = "en"
            val editor = sharedpreferences.edit()
            val config: Configuration
            val res: Resources
            val locale: Locale
            if (buttonView.isChecked) {
                language = "ar"
                editor.putString("language_prefix", "ar")
                locale = Locale(language)
                Locale.setDefault(locale)
                res = resources
                config = Configuration(res.configuration)
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            } else {
                editor.putString("language_prefix", "en")
                locale = Locale(language)
                Locale.setDefault(locale)
                res = resources
                config = Configuration(res.configuration)
                config.locale = locale
            }
            editor.apply()
            editor.commit()
            res.updateConfiguration(config, res.displayMetrics)
            val refresh =
                Intent(applicationContext, LanguageAndSharedPreferencesActivity::class.java)
            startActivity(refresh)
            finish()
        }
    }

    companion object {
        const val MyPREFERENCES = "MyPrefs"
    }
}