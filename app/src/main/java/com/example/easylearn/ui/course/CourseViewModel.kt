package com.example.easylearn.ui.course

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.easylearn.data.Repository
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.data.db.entities.relations.CourseWithLessons
import com.example.easylearn.ui.home.HomeViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CourseViewModel @ViewModelInject constructor(
    @Assisted private val state: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    val courseId = state.get<String>("courseId")
    private val _courseLessons = MutableLiveData<List<LessonDb>>()

    val courseLessons: LiveData<List<LessonDb>>
        get() {
            courseId?.let {
                loadCourseLessons(courseId)
            }
            return _courseLessons
        }

    val currentIndex =  MutableLiveData<Int>()

    fun loadCourseLessons(id: String) {
        viewModelScope.launch {
            _courseLessons.value = repository.getSavedLessons(id)

        }
    }

    fun setCompleted() {
        viewModelScope.launch {
            _courseLessons.value?.let { it ->
                val payload =  mutableListOf<LessonDb>()
                for (i in 0..currentIndex.value!!) {
                    val lessonDb =  it[i].copy( isComplete = true)
                    payload.add(lessonDb)
                }
                repository.updateCompletedLessons(payload)
                Log.d(TAG,"${currentIndex.value}")
        }
    }


    }

    private val _courseEventChannel = Channel<HomeViewModel.HomeEvent>()
    val courseEvent = _courseEventChannel.receiveAsFlow()

    sealed class CourseEvent {
        data class SomeCourseEvent(val courseId: String) : CourseViewModel.CourseEvent()
    }
    companion object {
        private const val TAG = "CourseViewModel"
    }
}

