package com.example.easylearn.data.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(
    val courseId: String,
    val duration: Int,
    val id: String,
    val lesson: Int,
    val src: String,
    val title: String
): Parcelable