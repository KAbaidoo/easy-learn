package com.example.easylearn.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "course_table")
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
)