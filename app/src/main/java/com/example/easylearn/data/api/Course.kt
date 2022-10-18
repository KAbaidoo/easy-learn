package com.example.easylearn.data.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Course(
    val id: String,
    val title: String,
    val about: String,
    val offered: String,
    val banner: String,
    val rating: Float,
    val price: Float,
    val modules: Int,
    val cpd: Int,
    val duration: Int
) : Parcelable