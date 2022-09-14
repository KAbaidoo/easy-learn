package com.example.easylearn.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easylearn.data.Course
import com.example.easylearn.databinding.ItemCourseBinding

class CourseAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Course, CourseAdapter.CourseViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding =
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

  inner  class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
      init {
          binding.apply {
              root.setOnClickListener{
                  val position = adapterPosition
                  if (position != RecyclerView.NO_POSITION){
                      val course = getItem(position)
                      listener.onItemClick(course)
                  }
              }
          }
      }

        fun bind(course: Course) {
            binding.apply {
                Glide.with(itemView)
                    .load(course.banner)
                    .into(imageViewLogo)

                textViewName.text = course.title
                textViewOffered.text = "Offered by ${course.offered}"
                textViewCpd.text = "${course.cpd.toString()} cpd"
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(coure: Course)
    }

    class DiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Course, newItem: Course) =
            oldItem == newItem
    }

}