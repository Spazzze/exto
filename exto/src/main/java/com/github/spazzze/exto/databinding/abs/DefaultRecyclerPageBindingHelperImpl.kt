package com.github.spazzze.exto.databinding.abs

import com.github.spazzze.exto.BR
import com.github.spazzze.exto.R
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageBindingHelper
import com.github.spazzze.exto.view.viewmodels.ProgressItemViewModel

/**
 * @author Space
 * @date 20.08.2019
 */

class DefaultRecyclerPageBindingHelperImpl : IRecyclerPageBindingHelper<ProgressItemViewModel> {

    override val progressViewModel = ProgressItemViewModel()

    override val layoutRes: Int = R.layout.recyclerview_page_progress

    override val variableId: Int = BR.progressViewModel
}