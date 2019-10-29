package com.github.spazzze.exto.databinding

import android.content.res.ColorStateList
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.spazzze.exto.R
import com.github.spazzze.exto.databinding.abs.ArrayDatabindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel
import com.github.spazzze.exto.extensions.refreshColorState
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * @author Space
 * @date 27.02.2017
 */

@BindingAdapter("setCustomColors")
fun setColorSchemeResources(swipeRefreshLayout: SwipeRefreshLayout, set: Boolean) {
    if (!set) return
    swipeRefreshLayout.setColorSchemeResources(
            R.color.colorRefreshProgress_1,
            R.color.colorRefreshProgress_2,
            R.color.colorRefreshProgress_3)
}

@BindingAdapter("colorRes")
fun changeFabColor(fab: FloatingActionButton, @ColorRes colorId: Int?) {
    fab.backgroundTintList = ColorStateList.valueOf(fab.context.resources.getColor(colorId
            ?: return))
}

@BindingAdapter("enabled")
fun setEnabled(fab: FloatingActionButton, enabled: Boolean) {
    fab.isEnabled = enabled
    fab.refreshColorState(enabled)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T : IRecyclerItemViewModel> setItems(spinner: Spinner, items: ObservableList<T>?) {
    (spinner.adapter as? ArrayDatabindingAdapter<T>)?.run { setItems(items ?: return) }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["selectedItem", "selectedItemAttrChanged"], requireAll = false)
fun selectSpinnerItem(spinner: Spinner, position: Int, inverseBindingListener: InverseBindingListener) {
    val tag = spinner.getTag(R.id.spinner_tag) as? Boolean
    if (tag != true) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) = inverseBindingListener.onChange()
        }
        spinner.setTag(R.id.spinner_tag, true)
    }
    if (position >= 0 && position < spinner.count && spinner.selectedItemPosition != position) spinner.setSelection(position)
}

@Suppress("UNCHECKED_CAST")
@InverseBindingAdapter(attribute = "selectedItem", event = "selectedItemAttrChanged")
fun captureSelectedSpinnerItem(spinner: Spinner): Int = spinner.selectedItemPosition