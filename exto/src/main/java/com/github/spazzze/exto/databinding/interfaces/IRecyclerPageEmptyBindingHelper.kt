package com.github.spazzze.exto.databinding.interfaces

interface IRecyclerPageEmptyBindingHelper<out I : IRecyclerItemViewModel> : IRecyclerBindingHelper<I> {

    val emptyViewModel: I
}