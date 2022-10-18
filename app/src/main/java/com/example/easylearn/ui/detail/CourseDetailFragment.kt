package com.example.easylearn.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easylearn.R
import com.example.easylearn.data.api.Lesson
import com.example.easylearn.databinding.FragmentCourseDetailBinding
import com.example.easylearn.ui.explore.CourseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment :Fragment(R.layout.fragment_course_detail),LessonAdapter.OnItemClickListener{
    private val viewModel: CourseDetailViewModel by viewModels()
    private val lessonAdapter = LessonAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCourseDetailBinding.bind(view)
        binding.apply {
            Glide.with(view)
                .load(viewModel.courseBanner)
                .into(imageViewBanner)
            textViewTitle.setText(viewModel.courseTitle)
            textViewOffered.text =  "Offered by ${viewModel.courseOffered}"
            textViewDescriptionBody.text = viewModel.courseDescription
            ratingBarDetail.rating = viewModel.courseRating as Float
            textViewLessons.text= "${viewModel.courseModules} Lessons  •  "
            textViewHours.text = "${viewModel.courseDuration} Hours"
            textViewCpd.text = "  •  ${viewModel.courseCPD} points"

            lessonsRecyclerView.apply {
                adapter = lessonAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.lessons.observe(viewLifecycleOwner){
                Log.d(TAG,it.toString())
                lessonAdapter.submitList(it)
            }

        }
    }

    override fun onItemClick(lesson: Lesson) {
        TODO("Not yet implemented")
    }
    companion object{
        private const val TAG = "CourseDetailFragment"
    }
}
