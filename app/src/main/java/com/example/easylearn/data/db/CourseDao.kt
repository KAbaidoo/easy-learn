package com.example.easylearn.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easylearn.data.pojo.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM course_table")
    fun getAllCourses(): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Query("DELETE FROM course_table")
    suspend fun deleteAllCourses()

    @Query ("SELECT * FROM course_table WHERE title LIKE '%'||:searchQuery||'%' ORDER BY  title")
    fun getCourses(searchQuery:String) : Flow<List<Course>>

}