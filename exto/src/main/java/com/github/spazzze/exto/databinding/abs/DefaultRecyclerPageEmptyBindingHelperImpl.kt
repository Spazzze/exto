package com.github.spazzze.exto.databinding.abs

import com.github.spazzze.exto.BR
import com.github.spazzze.exto.R
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageEmptyBindingHelper
import com.github.spazzze.exto.view.viewmodels.EmptyItemViewModel

class DefaultRecyclerPageEmptyBindingHelperImpl : IRecyclerPageEmptyBindingHelper<EmptyItemViewModel> {

    override val emptyViewModel = EmptyItemViewModel()

    override val layoutRes: Int = R.layout.recyclerview_item_empty_space

    override val variableId: Int = BR.emptyViewModel
}