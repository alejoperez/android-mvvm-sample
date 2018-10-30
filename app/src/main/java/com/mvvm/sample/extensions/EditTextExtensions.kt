package com.mvvm.sample.extensions

import android.content.Context
import android.text.InputFilter

private val noWhiteSpaces: InputFilter = InputFilter { string, start, end, _, _, _ ->
    (start until end)
            .filter { Character.isSpaceChar(string[it]) }
            .forEach { _ -> return@InputFilter "" }
    null
}

private val onlyNumbers: InputFilter = InputFilter { string, start, end, _, _, _ ->
    (start until end)
            .filterNot { Character.isDigit(string[it]) }
            .forEach { _ -> return@InputFilter "" }
    null
}

fun Context.getWhiteSpaceFilters(): Array<InputFilter> = arrayOf(noWhiteSpaces)

fun Context.getOnlyNumbersFilters(): Array<InputFilter> = arrayOf(onlyNumbers, noWhiteSpaces)