package com.example.chatapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_log_in.*   // I could not fix this error

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // created a virtual variables instead... to fix unresolved references  --- delete line if neccessary
        val signin_button = findViewById<Button>(R.id.signup_button)
        val login_email = findViewById<TextView>(R.id.email_editText)
        val login_password = findViewById<TextView>(R.id.password_editText)
        val signup_text = findViewById<TextView>(R.id.signup_text)



        signin_button.setOnClickListener {

            val email_as_string = login_email.text.toString()
            val password_as_string = login_password.text.toString()

            Log.d("LoginActivity", "Email is: $email_as_string")
            Log.d("LoginActivity", "Password: $password_as_string")

            // Firebase Authentication to create a user with email and password
        }

        signup_text.setOnClickListener{
            finish()
        }
    }

}