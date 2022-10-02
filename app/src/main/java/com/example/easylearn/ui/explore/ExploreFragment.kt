package com.example.easylearn.ui.explore

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.pojo.Course
import com.example.easylearn.databinding.FragmentExploreBinding

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

//        binding.myToolbar.inflateMenu(R.menu.top_app_bar)
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
                adapter = courseAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.courses.observe(viewLifecycleOwner) {
                courseAdapter.submitList(it)

//                progressBar.isVisible = it is Resource.Loading && it.data.isNullOrEmpty()
//                textViewError.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
//                textViewError.text = it.error?.localizedMessage
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