package com.github.spazzze.exto.view.fragments.interfaces

import androidx.fragment.app.FragmentManager
import com.github.spazzze.exto.view.interfaces.INavigableView

/**
 * @author Space
 * @date 27.04.2017
 */

interface IFragmentsFactory {

    val sfm: FragmentManager

    val containerId: Int

    fun onFragmentBackPressed(): Boolean = (sfm.findFragmentById(containerId) as? INavigableView)?.run { onBackPressed() } == true
}