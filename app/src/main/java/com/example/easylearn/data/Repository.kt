package com.example.easylearn.data

import com.example.easylearn.data.api.Course
import com.example.easylearn.data.api.CourseApi
import com.example.easylearn.data.api.Lesson
import com.example.easylearn.data.db.CourseDatabase
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

import javax.inject.Inject

class Repository @Inject constructor(
    private val api: CourseApi,
    private val db: CourseDatabase
) {
    private val courseDao = db.courseDao()


    //    =======================================
//    Api requests
    suspend fun searchCourses(query: String) = flow {
        emit(ApiResult.Loading())       //Load state

        try {
            val response = api.searchCourses(query)
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))   //Success state
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                errorMsg?.let {
                    emit(ApiResult.Failure(data = null, errorMsg = it))     //Failure state
                }
            }
        } catch (throwable: Throwable) {
            emit(ApiResult.Exception(throwable, null))          //Exception
        }
    }.flowOn(Dispatchers.IO)


    suspend fun getLessons(courseId: String) = flow {
        emit(ApiResult.Loading())

        try {
            val response = api.getLessons(courseId)
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))   //Success state
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                errorMsg?.let {
                    emit(ApiResult.Failure(data = null, errorMsg = it))     //Failure state
                }
            }
        } catch (throwable: Throwable) {
            emit(ApiResult.Exception(throwable, null))          //Exception
        }
    }.flowOn(Dispatchers.IO)

    //    ==============================================================
//    Database operations
    suspend fun saveCourseWithLessons(courseDb: CourseDb, lessonsDb: List<LessonDb>?) {

//        course?.let {
//            val courseDb = CourseDb(
//                it.id,
//                it.title,
//                it.about,
//                it.offered,
//                it.banner,
//                it.rating,
//                it.price,
//                it.cpd
//            )
//            courseDao.insertCourse(courseDb)
//        }
  }


}