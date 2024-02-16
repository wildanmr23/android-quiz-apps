package com.example.quizapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivitySignInBinding
import com.example.quizapp.ui.content.ContentActivity
import com.example.quizapp.ui.prepare.PrepareActivity
import com.example.quizapp.ui.register.SignUpActivity
import com.example.quizapp.utils.startActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupRedirectText.setOnClickListener {
            startActivity<SignUpActivity>()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (checkValid(email, pass, )){
                if (email.isNotEmpty() && pass.isNotEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity<PrepareActivity>()
                        } else {
                            Toast.makeText(this, "Account Not Found. Please Register or Try Again!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun checkValid(email: String, pass: String): Boolean {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email Not Valid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (pass.isEmpty()){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pass.length < 8){
            Toast.makeText(this, "Password Must be More than 8 karakter", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}