package com.example.easylearn.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.easylearn.data.Repository
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.api.Lesson
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.data.db.entities.relations.CourseWithLessons
import com.example.easylearn.util.ApiResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
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
        }

    val lessons: LiveData<ApiResult<List<Lesson>>>
        get() = lessonsFlow.asLiveData()


    private val _courseModules = MutableLiveData<Int>()
    val courseModules: LiveData<Int>
        get() = _courseModules

    private val _courseDuration = MutableLiveData<Long>()
    val courseDuration: LiveData<Long>
        get() = _courseDuration

    private var lessonsFlow = flowOf<ApiResult<List<Lesson>>>()


    init {
        loadLessons()
    }

    private val courseDetailEventChannel = Channel<DetailEvent>()

    val courseDetailEvent = courseDetailEventChannel.receiveAsFlow()

    private fun loadLessons() {
        viewModelScope.launch {

            courseId?.let {
                lessonsFlow = repository.getLessons(it) as Flow<ApiResult<List<Lesson>>>
                lessonsFlow.collectLatest { ApiResult ->
                    var duration = 0L
                    _courseModules.value = ApiResult.data?.asFlow()?.map {
                        duration += it.duration.div(60000)
                    }?.count()
                    _courseDuration.value = duration
                }


            }
        }
    }


    fun startButtonClicked(course: Course, lessons: List<Lesson>) = viewModelScope.launch {
        saveCourse(course)
        saveLessons(lessons)
        joinAll()

//        send event to channel
        val savedCourseWithLesson = repository.getSavedCourseWithLessons(course.id)
        courseDetailEventChannel.send(DetailEvent.NavigateToCourseScreen(savedCourseWithLesson))

//        Log.d(TAG, res.toString())

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

 /*       var courseModules = state.get<Int>("courseModules") ?: lessonNum
        set(value) {
            field = value
            state.set("courseModules", value)
        }

    var courseDuration = state.get<Int>("courseDuration") ?: course?.duration

        set(value) {
            field = value
            state.set("courseDuration", value)
        }

  */


    companion object {
        private const val TAG = "CourseDetailViewModel"
    }

    sealed class DetailEvent {
        data class NavigateToCourseScreen(val courseWithLessons: CourseWithLessons) : DetailEvent()
    }

    private suspend fun saveCourse(course: Course) {
        course.let {
            val courseDb = CourseDb(
                it.id,
                it.title,
                it.about,
                it.offered,
                it.banner,
                it.rating,
                it.price,
                it.cpd,
                courseModules.value!!,
                courseDuration.value!!
            )

            repository.saveCourseDb(courseDb)
        }
    }

    private suspend fun saveLessons(lessons: List<Lesson>) {
        lessons.asFlow().map {
            LessonDb(
                it.courseId,
                it.duration,
                it.id,
                it.lesson,
                it.src,
                it.title
            )
        }.collect { lessonDb ->
            repository.saveLessonDb(lessonDb)
        }
    }
}
