package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WebviewActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var edt_link: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        edt_link = findViewById(R.id.edt_url)
        edt_link.setOnKeyListener(View.OnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform action on key press
                loadURL(edt_link.getText().toString())
                return@OnKeyListener true
            }
            false
        })
        webView = findViewById(R.id.webview)
        // Enable Javascript
        webView.getSettings().javaScriptEnabled = true
        // Add a WebViewClient
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // Inject CSS when page is done loading
                injectCSS()
                super.onPageFinished(view, url)
            }
        })
        // Load a webpage
        val intent = intent
        if (intent != null && intent.data != null && intent.data!!.scheme != null) {
            when (intent.data!!.scheme) {
                "http", "https" -> {
                    val uri = intent.data
                    if (uri != null) {
                        val uri_string = uri.toString()
                        edt_link.setText(uri_string)
                        loadURL(uri_string)
                    }
                }
            }
        } else {
            Toast.makeText(this, "You didn't pass URL", Toast.LENGTH_LONG).show()
            loadURL(edt_link.getText().toString())
        }
    }

    private fun loadURL(url: String) {
        try {
            webView!!.loadUrl(url)
        } catch (e: Exception) {
            Log.e("loadURL", e.message!!)
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun injectCSS() {
        try {
            webView!!.loadUrl(
                "javascript:(function() { " +
                        "var links = document.getElementsByTagName('a');"
                        + "for(var i = 0; i < links.length; i++) {" +
                        "links[i].style.color=\"red\";" +
                        "}" +
                        "})()"
            )
        } catch (e: Exception) {
            Log.i("injectCSS", e.message!!)
        }
    }

    fun loadURL(view: View?) {
        webView!!.reload()
    }
}