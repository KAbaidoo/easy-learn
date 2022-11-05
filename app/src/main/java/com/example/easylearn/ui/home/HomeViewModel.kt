package com.example.easylearn.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easylearn.data.Repository
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.ui.explore.ExploreViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {


    private val _ongoingCourses = MutableLiveData<List<CourseDb>>()
    val ongoingCourses: LiveData<List<CourseDb>>
        get() {
            loadOngoingCourses()
            return _ongoingCourses
        }


    private val homeEventChannel = Channel<HomeEvent>()
    val homeEvent = homeEventChannel.receiveAsFlow()



     fun loadOngoingCourses() {
        viewModelScope.launch {
            _ongoingCourses.value = repository.getSavedCourses()
        }

    }

    fun onCourseSelected(courseId: String) = viewModelScope.launch {
        homeEventChannel.send(HomeEvent.NavigateToCourseScreen(courseId))
    }

    fun deleteCourse(courseId: String)= viewModelScope.launch {
        repository.deleteCourse(courseId)
       loadOngoingCourses()
    }

    sealed class HomeEvent {
        data class NavigateToCourseScreen(val courseId: String) : HomeEvent()
    }

}