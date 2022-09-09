package com.example.easylearn.ui.discover

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.databinding.FragmentCourseBinding
import com.example.easylearn.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseFragment: Fragment(R.layout.fragment_course){

    private val viewModel: CourseViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCourseBinding.bind(view)
        val courseAdapter = CourseAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = courseAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.courses.observe(viewLifecycleOwner){ result ->
                courseAdapter.submitList(result.data)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }


    }

}