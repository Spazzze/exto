package com.github.spazzze.exto.databinding.interfaces

import androidx.databinding.ObservableInt

/**
 * @author Space
 * @date 18.08.2019
 */

interface IRecyclerPageBindingAdapter<I : IRecyclerItemViewModel> : IRecyclerBindingAdapter<I> {

    val progressItemIndex: ObservableInt

    fun addProgressItem()

    fun removeProgressItem(): Boolean
}