package com.github.spazzze.exto.databinding.interfaces

/**
 * @author Space
 * @date 20.08.2019
 */

interface IRecyclerPageBindingHelper<out I : IRecyclerItemViewModel> : IRecyclerBindingHelper<I> {

    val progressViewModel: I
}