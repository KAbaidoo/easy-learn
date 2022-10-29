package com.example.easylearn.ui.explore

import androidx.hilt.Assisted
import androidx.lifecycle.*

import com.example.easylearn.data.api.Course
import com.example.easylearn.data.Repository


import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch


class ExploreViewModel @ViewModelInject constructor(

    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    private val exploreEventChannel = Channel<CourseEvent>()
    val courseEvent = exploreEventChannel.receiveAsFlow()


    val courseFlow = searchQuery.asFlow().flatMapLatest {
        repository.searchCourses(it)
    }
    val courseApiResponse = courseFlow.asLiveData()



    fun onCourseSelected(course: Course) = viewModelScope.launch {
        exploreEventChannel.send(CourseEvent.NavigateToCourseDetailScreen(course))
    }

    sealed class CourseEvent {
        data class NavigateToCourseDetailScreen(val course: Course) : CourseEvent()
    }
}