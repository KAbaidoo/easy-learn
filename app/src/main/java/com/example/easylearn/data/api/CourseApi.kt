package com.example.easylearn.data.api

import com.example.easylearn.data.pojo.Course
import com.example.easylearn.data.pojo.Lesson
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CourseApi {

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    @GET("/api/courses/search")
    suspend fun searchCourses(@Query("query") query: String=""): List<Course>

    @GET("/api/courses/lessons")
    suspend fun getAllLessons(@Query("id") id: String): List<Lesson>

}