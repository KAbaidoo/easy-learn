package com.example.easylearn.ui.explore

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.Course
import com.example.easylearn.databinding.FragmentDiscoverBinding
import com.example.easylearn.ui.DetailActivity

import com.example.easylearn.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_discover), CourseAdapter.OnItemClickListener {

    private val viewModel: ExploreViewModel by viewModels()


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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.courseEvent.collect { event ->
                when (event) {
                    is ExploreViewModel.CourseEvent.NavigateToCourseDetailScreen -> {
                        val intent = Intent(requireContext(),DetailActivity::class.java)
                        startActivity(intent)

//                        val action =
//                           HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailFragment(event.course)
//
//                        val navHost =
//                            childFragmentManager.findFragmentById(R.id.nav_host_fragment_screen_home) as NavHostFragment
//                        val navController = navHost.findNavController()
//                        navController.navigate(action)

                    }
                }
            }
        }

    }

    override fun onItemClick(course: Course) {
        viewModel.onCourseSelected(course)
    }

}