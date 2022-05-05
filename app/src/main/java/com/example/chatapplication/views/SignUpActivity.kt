package com.example.chatapplication.views

import Extensions.toast
import FirebaseUtils.firebaseAuth
import FirebaseUtils.firebaseUser
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*


class SignUpActivity : AppCompatActivity() {
    lateinit var userid: String
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    //Creating member variables of FirebaseDatabase and DatabaseReference
    private var mFirebaseDatabaseInstances: FirebaseDatabase?=null
    private var mFirebaseDatabase: DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        createAccountInputsArray = arrayOf(email_txt, password_txt, confirmpassword_txt)

        //Get instance of FirebaseDatabase
        mFirebaseDatabaseInstances= FirebaseDatabase.getInstance()

        signup_button.setOnClickListener {
            signUp()
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
                       // saveUser()
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

    private fun saveUser(){
        //Getting reference to ?users? node
        mFirebaseDatabase=mFirebaseDatabaseInstances!!.getReference("users")
        //Getting current user from FirebaseAuth
        val user=FirebaseAuth.getInstance().currentUser
        Log.d("SignUpActivity", "User : $user")
        //add username, email to database
        userid=user!!.uid ?: ""
        userEmail=user.email ?: ""
        //Creating a new user
        val myUser=User(userid, username_txt.text.toString(), userEmail)
        //Writing data into database using setValue() method
        mFirebaseDatabase!!.child(userid).setValue(myUser)
    }
}

class User(val uid:String, val username:String, val email:String)

// authentication reference :
// https://medium.com/@mutebibrian256/firebase-authentication-with-email-and-password-in-android-using-kotlin-5fbe61ee6252
