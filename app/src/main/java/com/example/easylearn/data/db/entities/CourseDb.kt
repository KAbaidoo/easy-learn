package com.example.easylearn.data.db.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat


@Entity(tableName = "course_table")
@Parcelize
data class CourseDb(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val about: String,
    val offered: String,
    val banner: String,
    val rating: Float,
    val price: Float,
    val cpd: Int,
    val modules: Int = 0,
    val duration: Long = 0,
    val completed: Boolean= false,
    val finished: Int = 0,
    val progress:Float = 0.0f,
    val started: Long = System.currentTimeMillis()

):Parcelable{
    val dateEnrolledDateFormatted: String
        get() = DateFormat.getDateInstance().format(started)
}