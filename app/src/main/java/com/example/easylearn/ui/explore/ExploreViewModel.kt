package com.example.easylearn.ui.explore

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.easylearn.data.Course
import com.example.easylearn.data.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor(
    repository: CourseRepository
) : ViewModel() {

    val courses = repository.getCourses().asLiveData()

    private val courseEventChannel = Channel<CourseEvent>()
    val courseEvent = courseEventChannel.receiveAsFlow()

    fun onCourseSelected(course: Course)= viewModelScope.launch {
        courseEventChannel.send(CourseEvent.NavigateToCourseDetailScreen(course))
    }

    sealed class CourseEvent{
        data class NavigateToCourseDetailScreen(val course: Course): CourseEvent()
    }
}