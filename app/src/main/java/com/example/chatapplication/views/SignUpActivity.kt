package com.example.chatapplication.views

import Extensions.toast
import FirebaseUtils.firebaseAuth
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.R
import com.example.chatapplication.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*


class SignUpActivity : AppCompatActivity() {

    lateinit var createAccountInputsArray: Array<EditText>

    lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        createAccountInputsArray = arrayOf(email_txt, password_txt, confirmpassword_txt)


        signup_button.setOnClickListener {

            val userName = username_txt.text.toString().trim()
            val userEmail = email_txt.text.toString().trim()
            val userPassword = password_txt.text.toString().trim()

            signUp(userName, userEmail, userPassword)
        }

        signin_text.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            toast("Sign into your account")
            Log.d("SignUpActivity", "..signed in")
            finish()
        }
    }

    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            toast("Welcome back")
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

    private fun signUp(userName: String, userEmail: String, userPassword: String) {
        if (identicalPassword()) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                       // addUserToDatabase(userName, userEmail, firebaseAuth.currentUser?.uid!!)
                        toast("Created account successfully !")
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        toast("Failed to Authenticate !")
                        Log.e("FirebaseAuth", "Failed login", task.getException());
                    }
                }
        }
    }

    private fun addUserToDatabase(userName: String, userEmail: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(userName, userEmail, uid))
    }

}

// authentication reference :
// https://medium.com/@mutebibrian256/firebase-authentication-with-email-and-password-in-android-using-kotlin-5fbe61ee6252
