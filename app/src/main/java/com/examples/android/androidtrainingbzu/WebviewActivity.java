package com.examples.android.androidtrainingbzu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {
    WebView webView;
    EditText edt_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        edt_link= findViewById(R.id.edt_url);
        edt_link.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    loadURL(edt_link.getText().toString());
                    return true;
                }
                return false;
            }
        });
        webView = findViewById(R.id.webview);
        // Enable Javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // Add a WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Inject CSS when page is done loading
                injectCSS();
                super.onPageFinished(view, url);
            }
        });
        // Load a webpage
        Intent intent = getIntent();
        if (intent != null && intent.getData()!=null && intent.getData().getScheme() != null) {
            switch (intent.getData().getScheme()) {
                case "http":
                case "https":
                    Uri uri = intent.getData();
                    if (uri != null) {
                        String uri_string = uri.toString();
                        edt_link.setText(uri_string);
                        loadURL(uri_string);
                    }
                    break;
            }
        }else {
           Toast.makeText(this, "You didn't pass URL",Toast.LENGTH_LONG).show();
            loadURL(edt_link.getText().toString());
        }
    }

    private void loadURL(String url){
        try {
            webView.loadUrl(url);
        }catch (Exception e){
            Log.e("loadURL",e.getMessage());
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void injectCSS() {
        try {
            webView.loadUrl(
                    "javascript:(function() { " +
                            "var links = document.getElementsByTagName('a');"
                            + "for(var i = 0; i < links.length; i++) {" +
                            "links[i].style.color=\"red\";"+
                            "}"+
                            "})()"
            );
        } catch (Exception e) {
           Log.i("injectCSS", e.getMessage());
        }
    }

    public void loadURL(View view) {
        webView.reload();
    }
}
