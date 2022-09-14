package com.example.easylearn.ui.detail

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import com.example.easylearn.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailFragment : BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_dialog,container,false)
    }
}