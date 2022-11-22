package com.example.easylearn.ui.explore

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.api.Course
import com.example.easylearn.databinding.FragmentExploreBinding
import com.example.easylearn.util.ApiResult

import com.example.easylearn.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore), ExploreCourseAdapter.OnItemClickListener {

    private val viewModel: ExploreViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentExploreBinding.bind(view)
        val exploreCourseAdapter = ExploreCourseAdapter(this)


        binding.myToolbar.apply {
            val searchItem = menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            val pendingQuery = viewModel.searchQuery.value
            if (pendingQuery != null && pendingQuery.isNotEmpty()) {
                searchItem.expandActionView()
                searchView.setQuery(pendingQuery, false)
            }
            searchView.onQueryTextChanged {
                viewModel.searchQuery.value = it
            }
        }

        binding.apply {
            recyclerView.apply {
                adapter = exploreCourseAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.courseApiResponse.observe(viewLifecycleOwner) { result ->


                when (result){
                    is ApiResult.Success -> {
                        progressBar.isVisible = false
                        textViewError.isVisible = false
                        exploreCourseAdapter.submitList(result.data)
                    }
                    is ApiResult.Failure,null -> {
                        progressBar.isVisible = false
                        textViewError.isVisible = true
                        textViewError.text = result?.errorMsg
                    }
                    is ApiResult.Loading -> {
                        progressBar.isVisible = true
                    }
                    is ApiResult.Exception -> {
                        progressBar.isVisible = false
                        textViewError.isVisible = true
                        textViewError.text = result.error?.localizedMessage
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.courseEvent.collect { event ->
                when (event) {
                    is ExploreViewModel.CourseEvent.NavigateToCourseDetailScreen -> {
                        val action =
                            ExploreFragmentDirections.actionNavigationExploreToCourseDetailFragment(
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