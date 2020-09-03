package com.github.spazzze.exto.view.viewmodels

import androidx.databinding.BaseObservable
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel

open class EmptyItemViewModel(override val id: String = "") : BaseObservable(), IRecyclerItemViewModel