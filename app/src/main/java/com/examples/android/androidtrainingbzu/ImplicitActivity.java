package com.examples.android.androidtrainingbzu;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class ImplicitActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_CONTACT = 2;
    EditText edt_mobile,edt_email,edt_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        edt_email=findViewById(R.id.edt_email);
        edt_mobile=findViewById(R.id.edt_phone);
        edt_url=findViewById(R.id.edt_url);
        edt_email.requestFocus();

    }

    public void callImplicitIntent(View view) {
        Intent intent;
        try {
            switch (view.getId()){
                case R.id.btn_callMobile:
                    //Create an intent for action
                    intent = new Intent(Intent.ACTION_DIAL);
                    // Provide data as a URI
                    intent.setData(Uri.parse("tel:"+edt_mobile.getText().toString()));
                    //Start the activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_url:
                    intent = new Intent(Intent.ACTION_VIEW);
                    // Provide data as a URI
                    intent.setData(Uri.parse(edt_url.getText().toString()));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    //Start the activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_email:
                    String mailto = "mailto:" +edt_email.getText().toString()+
                            "?cc=" + "email@example.com" +
                            "&subject=" + Uri.encode("Email Subject") +
                            "&body=" + Uri.encode("Email Body");
                    intent = new Intent(Intent.ACTION_SENDTO);
                    // Provide data as a URI
                    //intent.setData(Uri.parse("mailto:"+edt_email.getText().toString()));
                    intent.setData(Uri.parse(mailto));
                    //Start the activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_setAlarm:
                    intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    int hour = Calendar.HOUR_OF_DAY;
                    int minute = Calendar.MINUTE;
                    Calendar calendar = Calendar.getInstance();
                    minute += calendar.get(Calendar.MINUTE);
                    if (minute >= 60) {
                        hour += 1;
                        minute -= 60;
                    }
                    hour = calendar.get(Calendar.HOUR_OF_DAY);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Alarm Message");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
                    }
                    intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                    case R.id.btn_captureImage:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.withAppendedPath(locationForPhotos, targetFilename));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                        }
                        break;
                case R.id.btn_selectContact:
                case R.id.edt_phone:
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_SELECT_CONTACT);
                    }
                    break;
            }
        }catch (Exception e){
            Log.e("callImplicitIntent",e.getMessage());
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            ((ImageView)findViewById(R.id.img_capturedImage)).setImageBitmap(thumbnail);
        }else  if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                edt_mobile.setText(number);
            }
        }
    }
}
