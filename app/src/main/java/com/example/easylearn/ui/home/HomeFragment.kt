package com.example.easylearn.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.databinding.FragmentHomeBinding
import com.example.easylearn.ui.explore.ExploreFragmentDirections
import com.example.easylearn.ui.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), HomeAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        val homeAdapter = HomeAdapter(this)

        binding.apply {
            recyclerView.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.ongoingCourses.observe(viewLifecycleOwner) { coursesDb ->
                homeAdapter.submitList(coursesDb)


            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homeEvent.collect { event ->
                when (event) {
                    is HomeViewModel.HomeEvent.NavigateToCourseScreen -> {
                        val action =
                            HomeFragmentDirections.actionNavigationHomeToCourseFragment(
                                event.courseId
                            )
                        findNavController().navigate(action)

                    }
                }
            }
        }

    }


    override fun onItemClick(courseId: String) {

        viewModel.onCourseSelected(courseId)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
