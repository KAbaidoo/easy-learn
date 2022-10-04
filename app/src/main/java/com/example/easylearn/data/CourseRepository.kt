package com.example.easylearn.data

import com.example.easylearn.data.api.CourseApi
import com.example.easylearn.data.db.CourseDatabase
import com.example.easylearn.data.pojo.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val api: CourseApi,
    private val db: CourseDatabase
) {
    private val courseDao = db.courseDao()

//        suspend fun searchCourses(query: String) = api.searchCourses(query)


//    fun getAllLessons(courseId) = flow {
//        emit(api.getAllLessons())
//    }.flowOn(Dispatchers.IO)

    suspend fun searchCourses(query: String) = flow {
        emit(api.searchCourses(query))
    }.flowOn(Dispatchers.IO)



}