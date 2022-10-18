package com.example.easylearn.data.db.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LessonDb(
    val courseId: String,
    val duration: Int,
    val id: String,
    val lesson: Int,
    val src: String,
    val title: String
): Parcelable