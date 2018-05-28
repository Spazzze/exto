package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.view.View

/**
 * @author elena
 * @date 25/01/17
 */

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("onClick")
fun bindOnClick(view: View, runnable: Runnable) {
    view.setOnClickListener { runnable.run() }
}