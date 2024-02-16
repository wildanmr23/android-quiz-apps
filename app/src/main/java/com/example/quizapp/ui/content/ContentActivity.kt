package com.example.quizapp.ui.content

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.adapter.ContentAdapter
import com.example.quizapp.databinding.ActivityContentBinding
import com.example.quizapp.model.Content
import com.example.quizapp.repository.Repository
import com.example.quizapp.ui.main.MainActivity
import com.example.quizapp.ui.score.ScoreActivity
import com.example.quizapp.utils.parcelableArrayList
import com.example.quizapp.utils.startActivity

class ContentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NICKNAME = "extra_nickname"
        const val EXTRA_CONTENTS = "extra_contents"
    }

    private lateinit var binding: ActivityContentBinding
    private lateinit var contentAdapter: ContentAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var dataSize = 0
    private var currentPosition = 0
    private var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Untuk menjaga data supaya tidak hilang ketika orientasi nya dirubah (potrait -> landscape) atau sebaliknya*/

        // Init
        contentAdapter = ContentAdapter()

        // Get Data
        if (intent != null) {
            nickname = intent.getStringExtra(EXTRA_NICKNAME)
        }

        if (savedInstanceState != null) {
            nickname = savedInstanceState.getString(EXTRA_NICKNAME)
            val contents = savedInstanceState.parcelableArrayList<Content>(EXTRA_CONTENTS)
            showDataContents(contents)
        } else {
            // Get Data from Repositiory
            val contents = Repository.getDataContents(this)
            // Show Data
            showDataContents(contents)
        }

        onClick()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.are_you_suree))
            .setMessage(getString(R.string.mesaage_exit))
            .setPositiveButton(getString(R.string.ya)) { dialog, _ ->
                dialog.dismiss()
                startActivity<MainActivity>()
                finishAffinity()
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.tidak)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.are_you_suree))
                .setMessage(getString(R.string.mesaage_exit))
                .setPositiveButton(getString(R.string.ya)) { dialog, _ ->
                    dialog.dismiss()
                    startActivity<MainActivity>()
                    finishAffinity()
                }
                .setNegativeButton(getString(R.string.tidak)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnNextContent.setOnClickListener {
            if (currentPosition < dataSize - 1) {
                binding.rvContent.smoothScrollToPosition(currentPosition + 1)
            } else {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.are_you_suree))
                    .setMessage(getString(R.string.message_input_data))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        val contents = contentAdapter.getResult()
                        val totalQuiz = contents.size
                        var totalCorrectAnswer = 0

                        for (content in contents) {
                            for (answer in content.answers!!) {
                                if (answer.isClick == true && answer.correctAnswer == true) {
                                    totalCorrectAnswer += 1
                                }
                            }
                        }

                        val totalScore = totalCorrectAnswer.toDouble() / totalQuiz * 100
                        startActivity<ScoreActivity>(
                            ScoreActivity.EXTRA_NICKNAME to nickname,
                            ScoreActivity.EXTRA_SCORE to totalScore.toInt()
                        )
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.btnPrevContent.setOnClickListener {
            binding.rvContent.smoothScrollToPosition(currentPosition - 1)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NICKNAME, nickname)
        val contents = contentAdapter.getResult()
        outState.putParcelableArrayList(EXTRA_CONTENTS, contents as ArrayList)
    }

    private fun showDataContents(contents: List<Content>?) {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()

        if (contents != null) {
            contentAdapter.setData(contents as MutableList<Content>)
        }

        snapHelper.attachToRecyclerView(binding.rvContent)
        binding.rvContent.layoutManager = layoutManager
        binding.rvContent.adapter = contentAdapter

        dataSize = layoutManager.itemCount
        binding.pbContent.max = dataSize - 1

        // first show index
        var index = "${currentPosition + 1} / $dataSize"
        binding.tvIndexContent.text = index

        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentPosition = layoutManager.findFirstVisibleItemPosition()
                index = "${currentPosition + 1} / $dataSize"
                binding.tvIndexContent.text = index
                binding.pbContent.progress = currentPosition
            }
        })

    }
}


