package com.github.spazzze.exto.view.fragments.interfaces

import com.github.spazzze.exto.view.interfaces.IBaseView
import com.github.spazzze.exto.view.interfaces.INavigableView

/**
 * @author Space
 * @date 01.04.2017
 */

interface IBaseFragment<out A : IBaseView> : IBaseView, INavigableView {

    val parentActivity: A?
}