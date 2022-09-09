package com.example.easylearn.api

import com.example.easylearn.data.Course
import retrofit2.http.GET
interface CourseApi {

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    @GET("/api/courses/all")
    suspend fun getCourses(): List<Course>
}