package com.example.chatapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_log_in.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        signin_button.setOnClickListener {
            val email = email_editText.text.toString()
            val password = password_editText.text.toString()

            Log.d("LoginActivity", "Email is: $email")
            Log.d("LoginActivity", "Password: $password")

            // Firebase Authentication to create a user with email and password
        }

        signup_text.setOnClickListener{
            finish()
        }
    }

}