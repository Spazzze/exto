package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.view.View
import android.view.animation.Animation

/**
 * @author Space
 * @date 01.04.2017
 */

@BindingAdapter("enableAnimation", "animation")
fun applyAnimation(view: View, startAnimation: Boolean, animation: Animation?) {
    when (startAnimation) {
        true -> view.startAnimation(animation)
        else -> {
            view.clearAnimation()
            animation?.cancel()
        }
    }
}