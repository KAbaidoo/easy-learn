package com.example.easylearn.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easylearn.data.api.Course
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.databinding.ItemCourseBinding
import com.example.easylearn.databinding.ItemLessonDetailBinding
import com.example.easylearn.databinding.ItemLessondbCourseBinding
import com.example.easylearn.ui.explore.ExploreCourseAdapter

class CourseLessonAdapter(private val listener: OnItemClickListener) :
    ListAdapter<LessonDb, CourseLessonAdapter.LessonDbViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonAdapter.LessonDbViewHolder {
        val binding =
            ItemLessondbCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonDbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseLessonAdapter.LessonDbViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

  inner  class LessonDbViewHolder(private val binding: ItemLessondbCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
      init {
          binding.apply {
              root.setOnClickListener{
                  val position = adapterPosition
                  if (position != RecyclerView.NO_POSITION){
                      val lessonDb = getItem(position)
                      listener.onItemClick(lessonDb)
                  }
              }
          }
      }

        fun bind(lessonDb: LessonDb) {
            binding.apply {
//                Glide.with(itemView)
//                    .load(LessonDb.banner)
//                    .into(imageViewLogo)
//
                textViewTitle.text = lessonDb.title
                textViewLesson.text = "lesson ${lessonDb.lesson}"
                textViewDuration.text = "${lessonDb.duration.div(60000)} mins"
                textViewComplete.text = "${if (lessonDb.isComplete) "complete" else "not complete" }"

            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(lessonDb: LessonDb)
    }

    class DiffCallback : DiffUtil.ItemCallback<LessonDb>() {
        override fun areItemsTheSame(oldItem: LessonDb, newItem: LessonDb) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LessonDb, newItem: LessonDb) =
            oldItem == newItem
    }

}