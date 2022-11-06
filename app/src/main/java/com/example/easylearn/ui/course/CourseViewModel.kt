package com.example.easylearn.ui.course

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.easylearn.data.Repository
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.db.entities.CourseDb
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

    private val _courseEventChannel = Channel<HomeViewModel.HomeEvent>()
    val courseEvent = _courseEventChannel.receiveAsFlow()



    sealed class CourseEvent {
        data class SomeCourseEvent(val courseId: String) : CourseViewModel.CourseEvent()
    }

}