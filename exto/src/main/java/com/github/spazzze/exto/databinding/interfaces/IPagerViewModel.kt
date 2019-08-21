package com.github.spazzze.exto.databinding.interfaces

import android.databinding.ObservableInt
import com.github.spazzze.exto.databinding.abs.PagerPositionListener

/**
 * @author elena
 * @date 17/04/17
 */


interface IPagerViewModel {

    val currentPage: ObservableInt

    val pageChangeListener: PagerPositionListener
}