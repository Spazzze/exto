package com.github.spazzze.exto.databinding.abs

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingHelper
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel

/**
 * @author Space
 * @date 05.04.2017
 */

data class RecyclerBindingHelper<out I : IRecyclerItemViewModel>(
        @LayoutRes override val layoutRes: Int,
        @IdRes override val variableId: Int) : IRecyclerBindingHelper<I>