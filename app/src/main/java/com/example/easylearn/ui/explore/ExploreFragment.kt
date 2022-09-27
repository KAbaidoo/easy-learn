package com.example.easylearn.ui.explore

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.Course
import com.example.easylearn.databinding.FragmentExploreBinding

import com.example.easylearn.util.Resource
import com.example.easylearn.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore), CourseAdapter.OnItemClickListener {

    private val viewModel: ExploreViewModel by viewModels()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentExploreBinding.bind(view)
        val courseAdapter = CourseAdapter(this)

        binding.apply {
            recyclerView.apply {
                adapter = courseAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.courses.observe(viewLifecycleOwner) {
                courseAdapter.submitList(it)

//                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
//                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
//                textViewError.text = result.error?.localizedMessage
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }
        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }


    override fun onItemClick(course: Course) {
        viewModel.onCourseSelected(course)
    }


}