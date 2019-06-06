package com.github.spazzze.exto.databinding.abs

import android.databinding.ObservableArrayList
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.spazzze.exto.extensions.onChange
import com.github.spazzze.exto.extensions.replaceAllBy

/**
 * @author Space
 * @date 24.12.2016
 */

abstract class BindingPagerAdapter<T : Any>(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    @Volatile
    private var items = ObservableArrayList<T>().onChange { notifyDataSetChanged() }

    abstract fun createFragment(position: Int): Fragment

    abstract fun getItemTitle(position: Int): String

    override fun getCount(): Int = items.size

    override fun getItem(position: Int) = createFragment(position)

    override fun getPageTitle(position: Int) = getItemTitle(position)

    operator fun get(position: Int): T = items[position]

    fun setItems(newItems: List<T>) = items.replaceAllBy(newItems)
}