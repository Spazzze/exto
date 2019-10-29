package com.github.spazzze.exto.view.interfaces

import android.content.Context
import androidx.fragment.app.FragmentActivity

/**
 * @author Space
 * @date 29.05.2018
 */

interface IView {

    val ctx: Context

    val currentActivity: FragmentActivity?
}