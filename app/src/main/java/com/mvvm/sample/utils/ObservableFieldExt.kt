package com.mvvm.sample.utils

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.annotation.StringRes
import com.mvvm.sample.databinding.BindingAdapters

fun ObservableInt.checkField(@StringRes errorResource: Int, isValid: Boolean) {
    if (!isValid) {
        set(BindingAdapters.EMPTY)
        set(errorResource)
    }
}

fun ObservableField<String>.getValueOrDefault(default: String = ""): String = this.get() ?: default

fun ObservableField<Boolean>.getValueOrDefault(default: Boolean = false): Boolean = this.get() ?: default