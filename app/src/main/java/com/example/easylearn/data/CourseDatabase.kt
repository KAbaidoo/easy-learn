package com.example.easylearn.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}