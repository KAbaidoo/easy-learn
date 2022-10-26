package com.example.easylearn.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM course_table")
    fun getAllCourses(): Flow<List<CourseDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonDb>)


    @Query("DELETE FROM course_table")
    suspend fun deleteAllCourses()



}