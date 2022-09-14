package com.example.easylearn.ui.discover

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.Course
import com.example.easylearn.databinding.FragmentDiscoverBinding
import com.example.easylearn.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover), CourseAdapter.OnItemClickListener {

    private val viewModel: DiscoverViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDiscoverBinding.bind(view)
        val courseAdapter = CourseAdapter(this)

        binding.apply {
            recyclerView.apply {
                adapter = courseAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }


            viewModel.courses.observe(viewLifecycleOwner) { result ->
                courseAdapter.submitList(result.data)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }

        //Collect events from viewModel
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.courseEvent.collect { event ->
                when (event) {
                    is DiscoverViewModel.CourseEvent.NavigateToCourseDetailScreen -> {
                        val action =
                            DiscoverFragmentDirections.actionNavigationDiscoverToDetailFragment(
                                event.course
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onItemClick(course: Course) {
        viewModel.onCourseSelected(course)
    }

}