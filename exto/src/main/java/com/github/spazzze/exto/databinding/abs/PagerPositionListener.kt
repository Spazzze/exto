package com.github.spazzze.exto.databinding.abs

import androidx.databinding.ObservableInt
import androidx.viewpager.widget.ViewPager

/**
 * @author Space
 * @date 18.02.2017
 */

class PagerPositionListener(private val savedPosition: ObservableInt) : ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) = savedPosition.set(position)
}