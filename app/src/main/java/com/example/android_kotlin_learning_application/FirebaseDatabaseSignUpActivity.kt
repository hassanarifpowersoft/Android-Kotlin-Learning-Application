package com.example.android_kotlin_learning_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseSignUpActivity : AppCompatActivity() {

    lateinit var databaseReference : DatabaseReference

    lateinit var usernameTextInputEditText : TextInputEditText
    lateinit var passwordTextInputEditText : TextInputEditText
    lateinit var firstNameTextInputEditText : TextInputEditText
    lateinit var lastNameTextInputEditText : TextInputEditText
    lateinit var emailTextInputEditText : TextInputEditText
    lateinit var phoneNumberTextInputEditText : TextInputEditText
    lateinit var addressTextInputEditText : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebase_database_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextUsername)
        passwordTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)
        firstNameTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        lastNameTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextLastName)
        emailTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        phoneNumberTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextPhoneNumber)
        addressTextInputEditText = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)

        val registerButton = findViewById<Button>(R.id.buttonRegister)
        registerButton.setOnClickListener{
            var username = usernameTextInputEditText.text.toString()
            var password = passwordTextInputEditText.text.toString()
            var firstName = firstNameTextInputEditText.text.toString()
            var lastName = lastNameTextInputEditText.text.toString()
            var email = emailTextInputEditText.text.toString()
            var phoneNumber = phoneNumberTextInputEditText.text.toString()
            var address = addressTextInputEditText.text.toString()

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
        usernameTextInputEditText.setText("")
        passwordTextInputEditText.setText("")
        firstNameTextInputEditText.setText("")
        lastNameTextInputEditText.setText("")
        emailTextInputEditText.setText("")
        phoneNumberTextInputEditText.setText("")
        addressTextInputEditText.setText("")
    }


}