package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signup_button.setOnClickListener {
            val username = email_edittext_register.text.toString()
            val email = email_editText.text.toString()
            val password = password_editText.text.toString()

            Log.d("MainActivity", "Username is: " + username)
            Log.d("MainActivity", "Email is: " + email)
            Log.d("MainActivity", "Password: $password")

            // Firebase Authentication to create a user with email and password
        }

        signin_text.setOnClickListener {
            Log.d("MainActivity", "Try to show login activity")

            // launch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}