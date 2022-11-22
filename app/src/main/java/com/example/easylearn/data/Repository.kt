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
    suspend fun saveLessonDb(lessonDb: LessonDb) =
        withContext(Dispatchers.IO) { courseDao.insertLesson(lessonDb) }

    suspend fun saveCourseDb(courseDb: CourseDb) =
        withContext(Dispatchers.IO) { courseDao.insertCourse(courseDb) }

    suspend fun getSavedCourseWithLessons(id: String) =
        withContext(Dispatchers.IO) { courseDao.getCourseWithLessons(id) }

    suspend fun getSavedLessons(id: String) =
        withContext(Dispatchers.IO) { courseDao.getCourseLessons(id) }

    suspend fun getSavedCourses() =
        withContext(Dispatchers.IO) { courseDao.getAllCourses() }

    suspend fun deleteCourse(courseId: String) = withContext(Dispatchers.IO) {
        courseDao.deleteCourse(courseId)
    }


    suspend fun updateCompletedLessons(payload: List<LessonDb>) = withContext(Dispatchers.IO){
        courseDao.updateLessons(payload)
    }

}