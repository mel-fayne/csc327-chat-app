package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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

            // converting input variables to strings... for easier manipulation in logCats and passing to third_party functions
            val username_as_str = username.text.toString()
            val email_as_str = email.text.toString()
            val password_as_str = password.text.toString()

            // to avoid the user pressing the signup button with empty inputs...
            if (email_as_str.isEmpty() || password_as_str.isEmpty()){
                Toast.makeText(this, "Please enter a text in email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("MainActivity", "Username is: $username_as_str")
            Log.d("MainActivity", "Email is: $email_as_str")
            Log.d("MainActivity", "Password: $password_as_str")

            // Firebase Authentication to create a user with email and password
            // createing a new user using email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_as_str, password_as_str)
                // if user is done entering details...
                .addOnCompleteListener{
                    if(!it.isSuccessful) return@addOnCompleteListener

                    // else, if unsuccessful
                    Log.d("Main", "Successfully created a user using uid : ${it.result.user?.uid}") // the question feature was a suggestion from that red-bulb suggestion thing
                }
                .addOnFailureListener {
                    Log.d("main", "Failed to create new user : ${it.message}")
                }

        }

        signin_text.setOnClickListener {
            Log.d("MainActivity", "...showing login activity")
            // launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}