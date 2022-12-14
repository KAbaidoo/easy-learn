package com.example.easylearn.data.db

import androidx.room.*

import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.data.db.entities.relations.CourseWithLessons


@Dao
interface CourseDao {

    @Query("SELECT * FROM course_table")
    suspend fun getAllCourses(): List<CourseDb>

    @Query("SELECT * FROM course_table WHERE id = :courseId")
    suspend fun getCourseWithLessons(courseId : String): CourseWithLessons

    @Query("SELECT * FROM lesson_table WHERE courseId = :courseId")
    suspend fun getCourseLessons(courseId : String): List<LessonDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(courseDb: CourseDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lessonDb : LessonDb)
    
    @Query("DELETE FROM course_table WHERE id = :courseId")
    suspend fun deleteCourse(courseId: String)

    @Update
    suspend fun updateLessons(lessonDbs: List<LessonDb>)


}