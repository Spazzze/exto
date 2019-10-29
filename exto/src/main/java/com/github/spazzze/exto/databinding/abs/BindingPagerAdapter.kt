package com.github.spazzze.exto.databinding.abs

import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.spazzze.exto.extensions.onChange
import com.github.spazzze.exto.extensions.replaceAllBy

/**
 * @author Space
 * @date 24.12.2016
 */

abstract class BindingPagerAdapter<T : Any>(fm: FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

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