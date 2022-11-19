package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ImplicitActivity : AppCompatActivity() {
    var edt_mobile: EditText? = null
    var edt_email: EditText? = null
    var edt_url: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit)
        edt_email = findViewById(R.id.edt_email)
        edt_mobile = findViewById(R.id.edt_phone)
        edt_url = findViewById(R.id.edt_url)
//        edt_email.requestFocus()
    }

    fun callImplicitIntent(view: View) {
        val intent: Intent
        try {
            when (view.id) {
                R.id.btn_callMobile -> {
                    //Create an intent for action
                    intent = Intent(Intent.ACTION_DIAL)
                    // Provide data as a URI
                    intent.data = Uri.parse("tel:" + edt_mobile!!.text.toString())
                    //Start the activity
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                R.id.btn_url -> {
                    intent = Intent(Intent.ACTION_VIEW)
                    // Provide data as a URI
                    intent.data = Uri.parse(edt_url!!.text.toString())
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    //Start the activity
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                R.id.btn_email -> {
                    val mailto = "mailto:" + edt_email!!.text.toString() +
                            "?cc=" + "email@example.com" +
                            "&subject=" + Uri.encode("Email Subject") +
                            "&body=" + Uri.encode("Email Body")
                    intent = Intent(Intent.ACTION_SENDTO)
                    // Provide data as a URI
                    //intent.setData(Uri.parse("mailto:"+edt_email.getText().toString()));
                    intent.data = Uri.parse(mailto)
                    //Start the activity
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                R.id.btn_setAlarm -> {
                    intent = Intent(AlarmClock.ACTION_SET_ALARM)
                    var hour = Calendar.HOUR_OF_DAY
                    var minute = Calendar.MINUTE
                    val calendar = Calendar.getInstance()
                    minute += calendar[Calendar.MINUTE]
                    if (minute >= 60) {
                        hour += 1
                        minute -= 60
                    }
                    hour = calendar[Calendar.HOUR_OF_DAY]
                    intent.putExtra(AlarmClock.EXTRA_HOUR, hour)
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, minute)
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Alarm Message")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true)
                    }
                    intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                R.id.btn_captureImage -> {
                    intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    //                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.withAppendedPath(locationForPhotos, targetFilename));
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                    }
                }
                R.id.btn_selectContact, R.id.edt_phone -> {
                    intent = Intent(Intent.ACTION_PICK)
                    intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivityForResult(intent, REQUEST_SELECT_CONTACT)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("callImplicitIntent", e.message!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val thumbnail = data!!.getParcelableExtra<Bitmap>("data")
            (findViewById<View>(R.id.img_capturedImage) as ImageView).setImageBitmap(thumbnail)
        } else if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            val contactUri = data!!.data
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val cursor = contentResolver.query(contactUri!!, projection,
                null, null, null)
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                val numberIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(numberIndex)
                edt_mobile!!.setText(number)
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_SELECT_CONTACT = 2
    }
}