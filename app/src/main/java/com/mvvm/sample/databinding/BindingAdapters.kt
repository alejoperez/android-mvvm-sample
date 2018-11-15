package com.mvvm.sample.databinding

import android.databinding.BindingAdapter
import android.text.InputFilter
import android.widget.EditText

object BindingAdapters {

    const val EMPTY = 0

    @JvmStatic
    @BindingAdapter("app:errorText")
    fun EditText.setErrorText(errorResource: Int) {
        error = when (errorResource) {
            EMPTY -> null
            else -> context.getString(errorResource)
        }
    }

    @JvmStatic
    @BindingAdapter("app:filters")
    fun EditText.setInputFilters(filterArray: Array<InputFilter>) {
        filters = filterArray
    }
}