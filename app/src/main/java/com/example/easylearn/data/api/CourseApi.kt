package com.example.easylearn.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CourseApi {

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    @GET("/api/courses/search")
    suspend fun searchCourses(@Query("query") query: String=""): Response<List<Course>>

    @GET("/api/lessons/for")
    suspend fun getLessons(@Query("courseId") id: String): Response<List<Lesson>>

}