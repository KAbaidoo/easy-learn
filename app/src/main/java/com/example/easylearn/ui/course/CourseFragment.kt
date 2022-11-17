package com.example.easylearn.ui.course

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.databinding.FragmentCourseBinding
import com.example.easylearn.databinding.FragmentHomeBinding
import com.example.easylearn.ui.explore.ExploreFragmentDirections
import com.example.easylearn.ui.explore.ExploreViewModel
import com.example.easylearn.ui.home.HomeAdapter
import com.example.easylearn.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseFragment : Fragment(R.layout.fragment_course),CourseLessonAdapter.OnItemClickListener{

    private val viewModel: CourseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCourseBinding.bind(view)
        val courseLessonAdapter= CourseLessonAdapter(this)


        binding.apply {

            lessonDbRecyclerView.apply {
                adapter = courseLessonAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.courseWithLessons.observe(viewLifecycleOwner){
                courseLessonAdapter.submitList(it.lessons)
            }

        }
    }


    companion object {
        private const val TAG = "CourseFragment"
    }

    override fun onItemClick(lessonDb: LessonDb) {
        TODO("Not yet implemented")
    }
}
