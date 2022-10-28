package com.example.easylearn.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.data.db.entities.relations.CourseWithLessons
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM coursedb")
    suspend fun getAllCourses(): Flow<List<CourseDb>>

    @Query("SELECT * FROM courseDb WHERE id = :courseId")
    suspend fun getCourseWithLessons(courseId : String): Flow<List<CourseWithLessons>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(courseDb: CourseDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lessonDb : LessonDb)





}