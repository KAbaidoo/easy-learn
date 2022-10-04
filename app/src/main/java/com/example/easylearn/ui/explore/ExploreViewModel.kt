package com.example.easylearn.ui.explore

import androidx.hilt.Assisted
import androidx.lifecycle.*

import com.example.easylearn.data.pojo.Course
import com.example.easylearn.data.CourseRepository


import androidx.hilt.lifecycle.ViewModelInject
import com.bumptech.glide.Glide.init
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch


class ExploreViewModel @ViewModelInject constructor(

    private val repository: CourseRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    private val courseEventChannel = Channel<CourseEvent>()
    val courseEvent = courseEventChannel.receiveAsFlow()

//    private val _courses = MutableLiveData<List<Course>>()
//    val courses:LiveData<List<Course>>
//    get() = _courses


//    init {
//        loadCourses()
//    }
//
//    fun loadCourses(){
//        viewModelScope.launch {
//            _courses.value = repository.getAllCourses()
//        }
//    }

    val courseFlow = searchQuery.asFlow().flatMapLatest {
        repository.searchCourses(it)
    }
    val courseApiResponse = courseFlow.asLiveData()



    fun onCourseSelected(course: Course) = viewModelScope.launch {
        courseEventChannel.send(CourseEvent.NavigateToCourseDetailScreen(course))
    }

    sealed class CourseEvent {
        data class NavigateToCourseDetailScreen(val course: Course) : CourseEvent()
    }
}