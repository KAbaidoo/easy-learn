package com.example.easylearn.util

import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged (crossinline listener:(String) -> Unit ){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}

inline fun Button.onButtonClicked(crossinline action: () -> Unit){
    this.setOnClickListener( object : View.OnClickListener{
        override fun onClick(v: View?) {
            action()
        }
    })
}