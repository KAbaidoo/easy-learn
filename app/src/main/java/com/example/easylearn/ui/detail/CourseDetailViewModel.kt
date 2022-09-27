package com.example.easylearn.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.easylearn.data.Course





class CourseDetailViewModel  @ViewModelInject constructor(
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val course = state.get<Course>("course")

    var courseTitle = state.get<String>("courseTitle") ?: course?.title
        set(value) {
            field = value
            state.set("courseTitle", value)
        }

    var courseOffered = state.get<String>("courseOffered") ?: course?.offered
        set(value) {
            field = value
            state.set("courseOffered", value)
        }

    var courseDescription = state.get<String>("courseDescription") ?: course?.about
        set(value) {
            field = value
            state.set("courseDescription", value)
        }

    var courseBanner = state.get<String>("courseBanner") ?: course?.banner
        set(value) {
            field = value
            state.set("courseBanner", value)
        }

    var courseRating = state.get<Float>("courseRating") ?: course?.rating
        set(value) {
            field = value
            state.set("courseRating", value)
        }
    var coursePrice = state.get<Float>("coursePrice") ?: course?.price
        set(value) {
            field = value
            state.set("coursePrice", value)
        }
    var courseCPD = state.get<Int>("courseCPD") ?: course?.cpd
        set(value) {
            field = value
            state.set("courseCPD", value)
        }
    var courseModules = state.get<Int>("courseModules") ?: course?.modules
        set(value) {
            field = value
            state.set("courseModules", value)
        }


    var courseDuration = state.get<Int>("courseDuration") ?: course?.duration
        get() {
            return field?.div(60) as Int
        }
        set(value) {
            field = value
            state.set("courseDuration", value)
        }



}