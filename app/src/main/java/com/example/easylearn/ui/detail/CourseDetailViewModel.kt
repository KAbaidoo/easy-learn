package com.example.easylearn.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.easylearn.data.Repository
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.api.Lesson
import com.example.easylearn.util.ApiResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class CourseDetailViewModel @ViewModelInject constructor(
    @Assisted private val state: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {
    val course = state.get<Course>("course")
    var courseId = state.get<String>("courseId") ?: course?.id
        set(value) {
            field = value
            state.set("courseId", value)
            loadLessons()
        }

    val lessons: LiveData<ApiResult<List<Lesson>>>
        get() = lessonsFlow.asLiveData()
//    private val _lessons = MutableLiveData<List<Lesson>>()

    private var lessonsFlow = flowOf<ApiResult<List<Lesson>>>()


    init {
        loadLessons()
    }

    private fun loadLessons() {
        viewModelScope.launch {

            courseId?.let {
                lessonsFlow = repository.getLessons(it) as Flow<ApiResult<List<Lesson>>>
                lessonsFlow.collectLatest {
                   courseModules =  it.data?.asFlow()?.map {
                        courseDuration = courseDuration?.plus(it.duration)
                        it
                    }?.count()

                }


            }
        }
    }


    fun startButtonClicked(c: Course, l: List<Lesson>) = viewModelScope.launch {
//        repository.saveCourseWithLessons(course,lessons.value)

    }


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