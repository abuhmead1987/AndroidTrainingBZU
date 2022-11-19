package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.os.Vibrator
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class FormCheckActivity : AppCompatActivity() {
    private var vib: Vibrator? = null
    var animShake: Animation? = null
    private var signupInputName: EditText? = null
    private var signupInputEmail: EditText? = null
    private var signupInputPassword: EditText? = null
    private var signupInputDOB: EditText? = null
    private var signupInputLayoutName: TextInputLayout? = null
    private var signupInputLayoutEmail: TextInputLayout? = null
    private var signupInputLayoutPassword: TextInputLayout? = null
    private var signupInputLayoutDOB: TextInputLayout? = null
    private var btnSignUp: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_check)
        signupInputLayoutName = findViewById<View>(R.id.signup_input_layout_name) as TextInputLayout
        signupInputLayoutEmail =
            findViewById<View>(R.id.signup_input_layout_email) as TextInputLayout
        signupInputLayoutPassword =
            findViewById<View>(R.id.signup_input_layout_password) as TextInputLayout
        signupInputLayoutDOB = findViewById<View>(R.id.signup_input_layout_dob) as TextInputLayout
        signupInputName = findViewById<View>(R.id.signup_input_name) as EditText
        signupInputEmail = findViewById<View>(R.id.signup_input_email) as EditText
        signupInputPassword = findViewById<View>(R.id.signup_input_password) as EditText
        signupInputDOB = findViewById<View>(R.id.signup_input_dob) as EditText
        btnSignUp = findViewById<View>(R.id.btn_signup) as Button
        animShake = AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
        vib = getSystemService(VIBRATOR_SERVICE) as Vibrator
        btnSignUp!!.setOnClickListener { submitForm() }
    }

    private fun submitForm() {
        if (!checkName()) {
            signupInputName!!.animation = animShake
            signupInputName!!.startAnimation(animShake)
            vib!!.vibrate(120)
            return
        }
        if (!checkEmail()) {
            signupInputEmail!!.animation = animShake
            signupInputEmail!!.startAnimation(animShake)
            vib!!.vibrate(120)
            return
        }
        if (!checkPassword()) {
            signupInputPassword!!.animation = animShake
            signupInputPassword!!.startAnimation(animShake)
            vib!!.vibrate(120)
            return
        }
        if (!checkDOB()) {
            signupInputDOB!!.animation = animShake
            signupInputDOB!!.startAnimation(animShake)
            vib!!.vibrate(120)
            return
        }
        signupInputLayoutName!!.isErrorEnabled = false
        signupInputLayoutEmail!!.isErrorEnabled = false
        signupInputLayoutPassword!!.isErrorEnabled = false
        signupInputLayoutDOB!!.isErrorEnabled = false
        Toast.makeText(applicationContext, "You are successfully Registered !!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun checkName(): Boolean {
        if (signupInputName!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            signupInputLayoutName!!.isErrorEnabled = true
            signupInputLayoutName!!.error = getString(R.string.err_msg_name)
            signupInputName!!.error = getString(R.string.err_msg_required)
            return false
        }
        signupInputLayoutName!!.isErrorEnabled = false
        return true
    }

    private fun checkEmail(): Boolean {
        val email = signupInputEmail!!.text.toString().trim { it <= ' ' }
        if (email.isEmpty() || !isValidEmail(email)) {
            signupInputLayoutEmail!!.isErrorEnabled = true
            signupInputLayoutEmail!!.error = getString(R.string.err_msg_email)
            signupInputEmail!!.error = getString(R.string.err_msg_required)
            requestFocus(signupInputEmail)
            return false
        }
        signupInputLayoutEmail!!.isErrorEnabled = false
        return true
    }

    private fun checkPassword(): Boolean {
        if (signupInputPassword!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            signupInputLayoutPassword!!.error = getString(R.string.err_msg_password)
            requestFocus(signupInputPassword)
            return false
        }
        signupInputLayoutPassword!!.isErrorEnabled = false
        return true
    }

    private fun checkDOB(): Boolean {
        try {
            var isDateValid = false
            val s = signupInputDOB!!.text.toString().split("/").toTypedArray()
            val date = s[0].toInt()
            val month = s[1].toInt()
            if (date < 32 && month < 13) isDateValid = true
            if (signupInputDOB!!.text.toString().trim { it <= ' ' }.isEmpty() && isDateValid) {
                signupInputLayoutDOB!!.error = getString(R.string.err_msg_dob)
                requestFocus(signupInputDOB)
                signupInputDOB!!.error = getString(R.string.err_msg_required)
                return false
            }
        } catch (ex: Exception) {
            signupInputLayoutDOB!!.error = getString(R.string.err_msg_dob)
            requestFocus(signupInputDOB)
            return false
        }
        signupInputDOB!!.error = null
        return true
    }

    private fun requestFocus(view: View?) {
        if (view!!.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
        private fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}