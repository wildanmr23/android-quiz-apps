package com.example.quizapp.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityScoreBinding
import com.example.quizapp.ui.main.MainActivity
import com.example.quizapp.utils.startActivity

class ScoreActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_NICKNAME = "extra_nickname"
    }

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data
        if (intent != null){
            val score = intent.getIntExtra(EXTRA_SCORE,0)
            val nickname = intent.getStringExtra(EXTRA_NICKNAME)

            binding.tvNickname.text = nickname
            binding.tvScore.text = score.toString()
        }

        onClick()
    }

    private fun onClick() {
        binding.btnDone.setOnClickListener {
            startActivity<MainActivity>()
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity>()
        finishAffinity()
    }
}