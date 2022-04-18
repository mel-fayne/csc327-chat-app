package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // created a virtual variables instead... to fix unresolved references  --- delete line if neccessary
        val signup_button = findViewById<Button>(R.id.signup_button)
        val signin_text = findViewById<TextView>(R.id.signin_text)

        signup_button.setOnClickListener {
            val username = findViewById<TextView>(R.id.username_editText)
            val email = findViewById<TextView>(R.id.email_editText)
            val password = findViewById<TextView>(R.id.password_editText)

            Log.d("MainActivity", "Username is: $username")
            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Password: $password")

            // Firebase Authentication to create a user with email and password
        }

        signin_text.setOnClickListener {
            Log.d("MainActivity", "...showing login activity")
            // launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}