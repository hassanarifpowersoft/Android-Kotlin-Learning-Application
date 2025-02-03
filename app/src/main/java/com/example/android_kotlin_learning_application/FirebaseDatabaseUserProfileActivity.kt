package com.example.android_kotlin_learning_application

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseUserProfileActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var userInfromationTextView : TextView
    lateinit var currentUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebase_database_user_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userInfromationTextView = findViewById<TextView>(R.id.textViewUserInfromation)
        var username = intent.getStringExtra(FirebaseDatabaseLoginActivity.KEY_USER_NAME)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username.toString()).get().addOnSuccessListener{
            currentUser = User(
                    it.child("username").value.toString(),
                    it.child("password").value.toString(),
                    it.child("firstName").value.toString(),
                    it.child("lastName").value.toString(),
                    it.child("email").value.toString(),
                    it.child("phoneNumber").value.toString(),
                    it.child("address").value.toString()
                    )
            userInfromationTextView.text = "Welcome ${currentUser.firstName} ${currentUser.lastName} your email ${currentUser.email} is register in our database and your username is  ${currentUser.username} as a new user your phone number is ${currentUser.phoneNumber} and your  address is ${currentUser.address}"
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Connect with Database", Toast.LENGTH_SHORT).show()
        }



    }
}