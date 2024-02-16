package com.example.quizapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Contents(

	@field:SerializedName("contents")
	val contents: List<Content>? = null
) : Parcelable