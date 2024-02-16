package com.example.quizapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Content(

    @field:SerializedName("image")
	val image: String? = null,

    @field:SerializedName("answers")
	val answers: List<Answer>? = null,

    @field:SerializedName("body")
	val body: String? = null
) : Parcelable