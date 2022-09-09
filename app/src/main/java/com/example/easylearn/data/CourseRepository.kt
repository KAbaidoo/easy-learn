package com.example.easylearn.data

import androidx.room.withTransaction
import com.example.easylearn.api.CourseApi
import com.example.easylearn.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val api: CourseApi,
    private val db: CourseDatabase
) {
    private val courseDao = db.courseDao()

    fun getCourses() = networkBoundResource(
        query = {
            courseDao.getAllCourses()
        },
        fetch = {
            delay(2000)
            api.getCourses()
        },
        saveFetchResult = { courses ->
            db.withTransaction {
                courseDao.deleteAllCourses()
                courseDao.insertCourses(courses)
            }
        }
    )
}