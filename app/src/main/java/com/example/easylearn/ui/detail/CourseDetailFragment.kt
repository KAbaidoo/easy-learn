package com.example.easylearn.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easylearn.R
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.api.Lesson
import com.example.easylearn.databinding.FragmentCourseDetailBinding
import com.example.easylearn.util.ApiResult
import com.example.easylearn.util.onButtonClicked
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment :Fragment(R.layout.fragment_course_detail){
    private val viewModel: CourseDetailViewModel by viewModels()
    lateinit var course: Course
    lateinit var lessons: List<Lesson>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val lessonAdapter = LessonAdapter()
        val binding = FragmentCourseDetailBinding.bind(view)

        binding.apply {
            Glide.with(view)
                .load(viewModel.courseBanner)
                .into(imageViewBanner)
            textViewTitle.setText(viewModel.courseTitle)
            textViewOffered.text =  "Offered by ${viewModel.courseOffered}"
            textViewDescriptionBody.text = viewModel.courseDescription
            ratingBarDetail.rating = viewModel.courseRating as Float

            textViewCpd.text = "  •  ${viewModel.courseCPD} points"

            viewModel.courseDuration.observe(viewLifecycleOwner){
                textViewHours.text = "${it} minutes"
            }
            viewModel.courseModules.observe(viewLifecycleOwner){
                textViewLessons.text= "${it} lessons  •  "
            }
            lessonsRecyclerView.apply {
                adapter = lessonAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(false)
            }
            viewModel.lessons.observe(viewLifecycleOwner){result ->
                when (result){
                    is ApiResult.Success -> {
                        progressBar.isVisible = false
                        lessonAdapter.submitList(result.data)
                        lessons = result.data!!
                        course = viewModel.course!!

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

            startButton.onButtonClicked {
                viewModel.startButtonClicked(course,lessons)
            }
        }

    }




    companion object{
        private const val TAG = "CourseDetailFragment"
    }
}
