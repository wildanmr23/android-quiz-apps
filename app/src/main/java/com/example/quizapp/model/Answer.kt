package com.example.quizapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Answer(

	@field:SerializedName("answer")
	val answer: String? = null,

	@field:SerializedName("correct_answer")
	val correctAnswer: Boolean? = null,

	@field:SerializedName("is_click")
	var isClick: Boolean? = null,

	@field:SerializedName("option")
	val option: String? = null
) : Parcelable