package com.github.spazzze.exto.databinding.interfaces

import android.databinding.ObservableList

/**
 * @author Space
 * @date 06.04.2017
 */

interface IRecyclerViewModel<I : IRecyclerItemViewModel> {

    val items: ObservableList<I>

    val bindingHelper: IRecyclerBindingHelper<I>
}