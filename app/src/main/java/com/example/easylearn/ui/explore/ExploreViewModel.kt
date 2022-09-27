package com.example.easylearn.ui.explore

import androidx.hilt.Assisted
import androidx.lifecycle.*

import com.example.easylearn.data.Course
import com.example.easylearn.data.CourseDao
import com.example.easylearn.data.CourseRepository


import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest

import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class ExploreViewModel @ViewModelInject constructor(
    private val courseDao: CourseDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
//    val courses = courseDao.getCourses().asLiveData()

    private val courseEventChannel = Channel<CourseEvent>()
    val courseEvent = courseEventChannel.receiveAsFlow()


    private val courseFlow = searchQuery.asFlow().flatMapLatest {
        courseDao.getCourses(it)
    }
    val courses = courseFlow.asLiveData()

    fun onCourseSelected(course: Course)= viewModelScope.launch {
        courseEventChannel.send(CourseEvent.NavigateToCourseDetailScreen(course))
    }

    sealed class CourseEvent{
        data class NavigateToCourseDetailScreen(val course: Course): CourseEvent()
    }
}