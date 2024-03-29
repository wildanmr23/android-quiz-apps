package com.example.quizapp.ui.prepare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityPrepareBinding
import com.example.quizapp.ui.content.ContentActivity
import com.example.quizapp.utils.startActivity

class PrepareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrepareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrepareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {
        binding.btnStart.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            if (checkValidation(nickname)){
                startActivity<ContentActivity>(
                    ContentActivity.EXTRA_NICKNAME to nickname
                )
            }
        }
    }

    private fun checkValidation(nickname: String): Boolean {
        return if (nickname.isEmpty()){
            binding.etNickname.error = getString(R.string.please_field_your_nickname)
            false
        } else {
            true
        }
    }


}