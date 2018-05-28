package com.github.spazzze.exto.databinding.interfaces

/**
 * @author Space
 * @date 06.04.2017
 */

interface IRecyclerBindingAdapter<I : IRecyclerItemViewModel> {

    val bindingHelper: IRecyclerBindingHelper<I>

    val items: List<I>

    fun setItems(newItems: List<I>)
}