package com.example.easylearn.ui.course

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.easylearn.data.Repository
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.data.db.entities.relations.CourseWithLessons
import com.example.easylearn.ui.explore.ExploreViewModel
import com.example.easylearn.ui.home.HomeViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CourseViewModel @ViewModelInject constructor(
    @Assisted private val state: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    val courseId = state.get<String>("courseId")
    private val _courseWithLessons = MutableLiveData<CourseWithLessons>()

    val courseWithLessons: LiveData<CourseWithLessons>
        get() {
            courseId?.let {
                loadCourseWithLessons(courseId)
            }
            return _courseWithLessons
        }


    fun loadCourseWithLessons(id: String) {
        viewModelScope.launch {
            _courseWithLessons.value = repository.getSavedCourseWithLessons(id)
        }
    }

    fun setCompleted(lessonDbs: List<LessonDb>) {
        viewModelScope.launch {
            repository.setLessonCompleted(lessonDbs)
        }
    }
//    private fun setCompleted() {
//        playlist?.let { it ->
//            val payload =  mutableListOf<LessonDb>()
//            for (i in 0..currentItem) {
//
//                val lessonDb =  it[i].copy( isComplete = true)
//
//                payload.add(lessonDb)
//
//            }
//            viewModel.setCompleted(payload)
//        }
//
//    }

    private val _courseEventChannel = Channel<HomeViewModel.HomeEvent>()
    val courseEvent = _courseEventChannel.receiveAsFlow()

    sealed class CourseEvent {
        data class SomeCourseEvent(val courseId: String) : CourseViewModel.CourseEvent()
    }
}

