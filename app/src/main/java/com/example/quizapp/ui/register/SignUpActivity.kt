package com.example.quizapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivitySignUpBinding
import com.example.quizapp.ui.login.SignInActivity
import com.example.quizapp.utils.startActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupRedirectText.setOnClickListener {
            startActivity<SignInActivity>()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passET.text.toString().trim()
            val confirmPass = binding.confirmPassEt.text.toString().trim()

            if (checkValid(email, pass, confirmPass)) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //Comment this section for learn
//            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
//                if (pass == confirmPass) {
//
//                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            val intent = Intent(this, SignInActivity::class.java)
//                            startActivity(intent)
//                        } else {
//                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                } else {
//                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Empty Field Are Not Allowed", Toast.LENGTH_SHORT).show()
//            }
        }

    }

    private fun checkValid(email: String, pass: String, confirmPass: String): Boolean {
        if (email.isEmpty()) {
            Toast.makeText(this, "Email kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email Not Valid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (pass.isEmpty()){
            Toast.makeText(this, "Please Create Your Password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (pass.length < 8){
            Toast.makeText(this, "Password Must be More than 8 karakter", Toast.LENGTH_SHORT).show()
            return false
        }

        if (pass != confirmPass){
            Toast.makeText(this, "Password Not Matching", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}