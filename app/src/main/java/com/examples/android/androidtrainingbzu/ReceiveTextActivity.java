package com.examples.android.androidtrainingbzu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_text);
        TextView textView = findViewById(R.id.txt_sharedText);
        Intent intent = getIntent();
        if (intent != null && intent.getData()!=null && intent.getData().getScheme() != null) {
            switch (intent.getData().getScheme()) {
                case "http":
                    Uri uri = intent.getData();
                    if (uri != null) {
                        String uri_string = uri.toString();
                        textView.setText(uri_string);
                    }
                    break;
            }
        }else {
            textView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }
}
