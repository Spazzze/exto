package com.github.spazzze.exto.databinding.interfaces

/**
 * @author Space
 * @date 18.08.2019
 */

interface IRecyclerPageBindingAdapter<I : IRecyclerPageItemViewModel> : IRecyclerBindingAdapter<I> {

    fun addProgressItem(item: I)

    fun removeProgressItem(): Boolean
}