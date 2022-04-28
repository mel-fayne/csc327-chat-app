package com.example.chatapplication.views

import Extensions.toast
import FirebaseUtils.firebaseAuth
import FirebaseUtils.firebaseUser
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.example.chatapplication.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        createAccountInputsArray = arrayOf(email_txt, password_txt, confirmpassword_txt)
        signup_button.setOnClickListener {
            signUp()
        }

        signin_text.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            toast("Sign into your account")
            finish()
        }
    }

    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            toast("welcome back")
        }
    }

    private fun notEmpty(): Boolean = email_txt.text.toString().trim().isNotEmpty() &&
            password_txt.text.toString().trim().isNotEmpty() &&
            confirmpassword_txt.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            password_txt.text.toString().trim() == confirmpassword_txt.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            toast("Passwords do not matching !")
        }
        return identical
    }

    private fun signUp() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = email_txt.text.toString().trim()
            userPassword = password_txt.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Created account successfully !")

                        sendEmailVerification()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        toast("Failed to Authenticate !")
                    }
                }
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Email sent to $userEmail")
                }
            }
        }
    }
}

// authentication reference : https://medium.com/@mutebibrian256/firebase-authentication-with-email-and-password-in-android-using-kotlin-5fbe61ee6252
