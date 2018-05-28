package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager
import com.github.spazzze.exto.databinding.abs.BindingPagerAdapter

/**
 * @author elena
 * @date 23/12/16
 */

@Suppress("UNCHECKED_CAST")
@BindingAdapter("pagerItems")
fun <T : Any> setPagerItems(pager: ViewPager, items: List<T>?) {
    (pager.adapter as? BindingPagerAdapter<T>)?.setItems(items ?: return)
}

@BindingAdapter("currentPage")
fun setPage(pager: ViewPager, position: Int) {
    if (pager.childCount > position) pager.currentItem = position
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("pagerItems", "currentPage")
fun <T : Any> setPagerItemsAndPage(pager: ViewPager, items: List<T>?, position: Int) {
    val adapter = pager.adapter as? BindingPagerAdapter<T> ?: return
    items?.let { adapter.setItems(it) }
    if (adapter.count > position) pager.currentItem = position
}