package com.example.easylearn.ui.discover

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import com.example.easylearn.data.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    repository: CourseRepository
) : ViewModel() {

    val courses = repository.getCourses().asLiveData()
}