package com.example.easylearn.data

import com.example.easylearn.data.api.CourseApi
import com.example.easylearn.data.db.CourseDatabase
import com.example.easylearn.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

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


    //    =======================================
//    Api requests
    suspend fun searchCourses(query: String) = flow {

        emit(ApiResult.Loading())

        //Loading state

        try {
            val response = api.searchCourses(query)
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                errorMsg?.let {
                    emit(ApiResult.Failure(data = null, errorMsg = it))
                }
            }
        } catch (throwable: Throwable) {
            emit(ApiResult.Exception(throwable, null))
        }

    }.flowOn(Dispatchers.IO)


}