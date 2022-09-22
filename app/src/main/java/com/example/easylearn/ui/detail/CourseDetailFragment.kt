package com.example.easylearn.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.easylearn.R
import com.example.easylearn.databinding.FragmentCourseDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment :Fragment(R.layout.fragment_course_detail){
    private val viewModel: CourseDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCourseDetailBinding.bind(view)
        binding.apply {
            textViewTitle.setText(viewModel.courseTitle)
            textViewOffered.text =  "Offered by ${viewModel.courseOffered}"
            textViewDescriptionBody.text = viewModel.courseDescription
            ratingBarDetail.rating = viewModel.courseRating as Float
            textViewLessons.text= "${viewModel.courseModules} Lessons  •  "
            textViewHours.text = "${viewModel.courseDuration} Hours"

        }
    }
}
