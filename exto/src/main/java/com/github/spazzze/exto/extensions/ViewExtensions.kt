package com.github.spazzze.exto.extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.AnimRes
import android.support.annotation.DrawableRes
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.spazzze.exto.R
import com.github.spazzze.exto.view.CropCircleTransformation

/**
 * @author Space
 * @date 01.04.2017
 */

fun View.animateView(@AnimRes animId: Int) = execute {
    startAnimation(AnimationUtils.loadAnimation(context, animId))
}

fun View.animateViewChanges(@AnimRes animInId: Int, @AnimRes animIOutId: Int, change: () -> Unit) = execute {
    startAnimation(AnimationUtils.loadAnimation(context, animInId).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                change()
                startAnimation(AnimationUtils.loadAnimation(context, animIOutId))
            }
        })
    })
}

fun ImageView.load(path: String?,
                   cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESULT) {
    Glide.with(context)
            .load(path ?: "")
            .skipMemoryCache(true)
            .diskCacheStrategy(cacheStrategy)
            .into(this)
}

fun ImageView.load(path: String?, placeholder: Drawable,
                   cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESULT) {
    Glide.with(context)
            .load(path ?: "")
            .error(placeholder)
            .placeholder(placeholder)
            .dontAnimate()
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(cacheStrategy)
            .into(this)
}

fun ImageView.load(path: String?, @DrawableRes placeholderRes: Int,
                   cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESULT) {
    Glide.with(context)
            .load(path ?: "")
            .error(placeholderRes)
            .placeholder(placeholderRes)
            .dontAnimate()
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(cacheStrategy)
            .into(this)
}

fun ImageView.roundedImage(path: String?, placeholder: Drawable,
                           cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESULT) {
    Glide.with(context)
            .load(path ?: "")
            .error(placeholder)
            .placeholder(placeholder)
            .centerCrop()
            .bitmapTransform(CropCircleTransformation(context))
            .skipMemoryCache(true)
            .diskCacheStrategy(cacheStrategy)
            .crossFade()
            .into(this)
}

fun ImageView.roundedImage(path: String?, placeholder: Drawable, borderWidth: Float, borderColor: Int,
                           cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESULT) {
    Glide.with(context)
            .load(path ?: "")
            .error(placeholder)
            .placeholder(placeholder)
            .centerCrop()
            .bitmapTransform(CropCircleTransformation(context, borderWidth, borderColor))
            .skipMemoryCache(true)
            .diskCacheStrategy(cacheStrategy)
            .crossFade()
            .into(this)
}

@Suppress("DEPRECATION")
fun FloatingActionButton.refreshColorState(enabled: Boolean) {
    backgroundTintList = when (enabled) {
        true -> ColorStateList.valueOf(context.getThemeResource(R.attr.colorPrimary))
        else -> ColorStateList.valueOf(context.resources.getColor(android.R.color.darker_gray))
    }
}