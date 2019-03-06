package com.mvvm.sample.utils

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.annotation.StringRes
import com.mvvm.sample.databinding.BindingAdapters

fun ObservableInt.checkField(@StringRes errorResource: Int, isValid: Boolean) {
    if (!isValid) {
        set(BindingAdapters.EMPTY)
        set(errorResource)
    }
}

fun ObservableField<String>.getValueOrDefault(default: String = ""): String = this.get() ?: default

fun ObservableField<Boolean>.getValueOrDefault(default: Boolean = false): Boolean = this.get() ?: default