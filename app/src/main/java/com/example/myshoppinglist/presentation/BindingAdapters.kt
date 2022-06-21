package com.example.myshoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.example.myshoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("nameErrorStateLiveData")
fun bindNameErrorStateLiveData(textInputLayout: TextInputLayout, error: Boolean) {
    val message = if (error) {
        textInputLayout.context.getString(R.string.error_input_name)
        } else {
            null
        }
    textInputLayout.error = message
}
@BindingAdapter("countErrorStateLiveData")
fun bindCountErrorStateLiveData(textInputLayout: TextInputLayout, error: Boolean) {
    val message = if (error) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}