package com.example.easylearn.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easylearn.data.api.Lesson
import com.example.easylearn.databinding.ItemLessonDetailBinding

class DetailLessonAdapter() :
    ListAdapter<Lesson, DetailLessonAdapter.LessonViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailLessonAdapter.LessonViewHolder {
        val binding =
            ItemLessonDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailLessonAdapter.LessonViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

  inner  class LessonViewHolder(private val binding: ItemLessonDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: Lesson) {
            binding.apply {
                textViewTitle.text = lesson.title
                textViewLesson.text = "lesson no. ${lesson.lesson}"
                textViewDuration.text = "${lesson.duration.div(60000)} mins"
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson) =
            oldItem == newItem
    }

}