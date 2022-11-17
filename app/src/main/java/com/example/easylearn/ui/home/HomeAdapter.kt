package com.example.easylearn.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.databinding.ItemOngoingCourseBinding

class HomeAdapter(private val listener: OnItemClickListener) :
    ListAdapter<CourseDb, HomeAdapter.HomeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding =
            ItemOngoingCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class HomeViewHolder(private val binding: ItemOngoingCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val course = getItem(position)
                        listener.onItemClick(course.id)
                    }
                }
            }
        }

        fun bind(courseDb: CourseDb) {
            binding.apply {
                Glide.with(itemView)
                    .load(courseDb.banner)
                    .into(imageViewBanner)

                textViewTitle.text = courseDb.title
                textViewLessons.text = "${courseDb.modules} Lessons  •  "
                textViewHours.text = "${courseDb.duration} Minutes"

            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(courseId: String)
    }

    class DiffCallback : DiffUtil.ItemCallback<CourseDb>() {
        override fun areItemsTheSame(oldItem: CourseDb, newItem: CourseDb) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CourseDb, newItem: CourseDb) =
            oldItem == newItem
    }

}