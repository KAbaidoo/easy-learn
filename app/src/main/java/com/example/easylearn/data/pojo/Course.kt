package com.example.easylearn.data.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "course_table")
@Parcelize
data class Course(
    @PrimaryKey val id: String,
    val title: String,
    val about: String,
    val offered: String,
    val banner: String,
    val rating: Float,
    val price: Float,
    val modules: Int,
    val cpd: Int,
    val duration: Int
):Parcelable