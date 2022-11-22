package com.example.easylearn.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity(tableName = "lesson_table")
@Parcelize
data class LessonDb(
    val courseId: String,
    val duration: Long,
    @PrimaryKey(autoGenerate = false) val id: String,
    val lesson: Int,
    val src: String,
    val title: String,
    val isComplete:Boolean = false
): Parcelable