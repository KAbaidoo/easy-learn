package com.example.easylearn.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.easylearn.data.pojo.Course

@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}