package com.github.spazzze.exto.databinding.interfaces

/**
 * @author Space
 * @date 06.04.2017
 */

interface IRecyclerBindingHelper<out I : IRecyclerItemViewModel> {

    val layoutRes: Int

    val variableId: Int
}