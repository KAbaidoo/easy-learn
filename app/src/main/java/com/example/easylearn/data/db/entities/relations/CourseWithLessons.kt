package com.example.easylearn.data.db.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.easylearn.data.db.entities.CourseDb
import com.example.easylearn.data.db.entities.LessonDb

data class CourseWithLessons (
    @Embedded val course: CourseDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "courseId"
    )
    val lessons: List<LessonDb>

)