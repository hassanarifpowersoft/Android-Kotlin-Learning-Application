package com.example.android_kotlin_learning_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseLoginActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebase_database_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val signuptextView = findViewById<TextView>(R.id.textViewSignUp)

        signuptextView.setOnClickListener{
            val intent = Intent(this, FirebaseDatabaseSignUpActivity::class.java)
            startActivity(intent)
        }


        loginButton.setOnClickListener{
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if(username.isNotEmpty()){
                validateUserFromDatabase(username,password)
            }else{
                Toast.makeText(this,"Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateUserFromDatabase(username: String, password: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).get().addOnSuccessListener{
            if(it.exists()){
                val user = it.child("username").value
                val pas = it.child("password").value
                if (username == user && password == pas){
                    val intent = Intent(this, FirebaseDatabaseUserProfileActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Username or Password is Incorrect", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"This user is not exist on database please first sign up", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Connect with Database", Toast.LENGTH_SHORT).show()
        }
    }
}

