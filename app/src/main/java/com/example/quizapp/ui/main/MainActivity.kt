package com.example.quizapp.ui.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.ui.login.SignInActivity
import com.example.quizapp.utils.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()

    }

    private fun onClick() {
        binding.btnPlay.setOnClickListener {
            startActivity<SignInActivity>()
        }
    }
}