package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatDrawableManager
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextSwitcher
import android.widget.TextView

/**
 * @author elena
 * @date 23/12/16
 */

@BindingAdapter("showError")
fun showError(til: TextInputLayout, @StringRes error: Int?) {
    when (error) {
        null -> til.error = null
        else -> til.error = til.context.getString(error)
    }
    til.isErrorEnabled = error != null
}

@BindingAdapter("switchText")
fun switchText(switcher: TextSwitcher, text: String?) {
    switcher.setText(text ?: return)
}

@BindingAdapter("linkify")
fun addLinks(view: TextView, enabled: Boolean) {
    if (!enabled) return
    view.autoLinkMask = Linkify.ALL
    view.linksClickable = true
    view.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("setFocus")
fun setFocus(v: View, isNeeded: Boolean) {
    if (isNeeded) v.requestFocus()
}

@BindingAdapter("pickerItems", "wrapSelectorWheel")
fun setItems(picker: NumberPicker, values: Array<String>?, wrapSelectorWheel: Boolean?) {
    if (values == null || values.isEmpty()) return
    picker.displayedValues = null
    picker.minValue = 0
    picker.maxValue = values.size - 1
    picker.wrapSelectorWheel = wrapSelectorWheel ?: false
    picker.displayedValues = values
}

@BindingAdapter("pickerItems", "wrapSelectorWheel")
fun setItems(picker: NumberPicker, values: List<String>?, wrapSelectorWheel: Boolean?) {
    if (values == null || values.isEmpty()) return
    picker.displayedValues = null
    picker.minValue = 0
    picker.maxValue = values.size - 1
    picker.wrapSelectorWheel = wrapSelectorWheel ?: false
    picker.displayedValues = values.toTypedArray()
}

@BindingAdapter("customizeBackgroundTint")
fun setBackgroundTint(editText: EditText, @ColorRes color: Int) {
    tintView(editText, color)
}

private fun tintView(view: View, color: Int) {
    val nd = view.background?.constantState?.newDrawable() ?: return
    nd.colorFilter = AppCompatDrawableManager.getPorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    view.background = nd
}