package com.github.spazzze.exto.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import android.view.View
import androidx.databinding.ObservableBoolean

/**
 * @author elena
 * @date 25/01/17
 */

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean) = if (visible) View.VISIBLE else View.GONE

@BindingConversion
fun convertBooleanToVisibility(visible: ObservableBoolean) = if (visible.get()) View.VISIBLE else View.GONE

@BindingAdapter("onClick")
fun bindOnClick(view: View, runnable: Runnable) {
    view.setOnClickListener { runnable.run() }
}