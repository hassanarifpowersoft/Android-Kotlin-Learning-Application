package com.example.android_kotlin_learning_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseSignUpActivity : AppCompatActivity() {

    lateinit var databaseReference : DatabaseReference

    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var firstNameEditText : EditText
    lateinit var lastNameEditText : EditText
    lateinit var emailEditText : EditText
    lateinit var phoneNumberEditText : EditText
    lateinit var addressEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebase_database_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        firstNameEditText = findViewById<EditText>(R.id.editTextFirstName)
        lastNameEditText = findViewById<EditText>(R.id.editTextLastName)
        emailEditText = findViewById<EditText>(R.id.editTextEmail)
        phoneNumberEditText = findViewById<EditText>(R.id.editTextPhoneNumber)
        addressEditText = findViewById<EditText>(R.id.editTextAddress)

        val registerButton = findViewById<Button>(R.id.buttonRegister)
        registerButton.setOnClickListener{
            var username = usernameEditText.text.toString()
            var password = passwordEditText.text.toString()
            var firstName = firstNameEditText.text.toString()
            var lastName = lastNameEditText.text.toString()
            var email = emailEditText.text.toString()
            var phoneNumber = phoneNumberEditText.text.toString()
            var address = addressEditText.text.toString()

            var user = User(username,password,firstName,lastName,email,phoneNumber,address)
            registerUserInFirebaseDatabase(user)
        }

    }

    private fun registerUserInFirebaseDatabase(user: User) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(user.username).get().addOnSuccessListener{
            if(it.exists()){
                Toast.makeText(this,"user with this username already exist try new unique username", Toast.LENGTH_SHORT).show()
            }else{
                databaseReference.child(user.username).setValue(user).addOnSuccessListener{
                    Toast.makeText(this,"User Registered Successfully", Toast.LENGTH_SHORT).show()
                    clearAllTextFields()
                }
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Connect with Database", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearAllTextFields(){
        usernameEditText.text.clear()
        passwordEditText.text.clear()
        firstNameEditText.text.clear()
        lastNameEditText.text.clear()
        emailEditText.text.clear()
        phoneNumberEditText.text.clear()
        addressEditText.text.clear()
    }


}