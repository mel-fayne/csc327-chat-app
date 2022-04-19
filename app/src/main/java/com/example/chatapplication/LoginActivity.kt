package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*   // I could not fix this error

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // created a virtual variables instead... to fix unresolved references  --- delete line if neccessary
        val signin_button = findViewById<Button>(R.id.signin_button)
        val login_email = findViewById<TextView>(R.id.email_editText)
        val login_password = findViewById<TextView>(R.id.password_editText)
        val signup_text = findViewById<TextView>(R.id.signup_text)


        signin_button.setOnClickListener {

            val email_as_string = login_email.text.toString()
            val password_as_string = login_password.text.toString()

            Log.d("LoginActivity", "Email is: $email_as_string")
            Log.d("LoginActivity", "Password: $password_as_string")

            // Firebase Authentication to sign in a user with email and password
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email_as_string, password_as_string)
                // if user is done entering details...
                .addOnCompleteListener{
                    // if unsuccessful
                    if(!it.isSuccessful) return@addOnCompleteListener

                    // else if successful...
                    Log.d("LoginActivity", "Successfuly logged in as user with ID  : ${it.result.user?.uid}") // the question feature was a suggestion from that red-bulb suggestion thing
                }
                .addOnFailureListener {
                    Log.d("LoginActivity", "Failed to Login : ${it.message}")
                }
        }

        signup_text.setOnClickListener{
            Log.d("LoginActivity", "Redirect page to Signup page")
            // redirecting to Signup page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}