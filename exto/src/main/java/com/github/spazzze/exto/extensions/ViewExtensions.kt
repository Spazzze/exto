package com.github.spazzze.exto.extensions

import android.content.res.ColorStateList
import android.graphics.Bitmap
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
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
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

fun ImageView.load(path: String?, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .load(path ?: "")
            .apply(RequestOptions().dontAnimate().fitCenter().diskCacheStrategy(cacheStrategy))
            .into(this)
}

fun ImageView.load(path: String?, listener: RequestListener<Drawable>, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .load(path ?: "")
            .apply(RequestOptions().dontAnimate().fitCenter().diskCacheStrategy(cacheStrategy))
            .listener(listener)
            .into(this)
}

fun ImageView.load(path: String?, placeholder: Drawable, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .load(path ?: "")
            .apply(RequestOptions().error(placeholder).placeholder(placeholder).dontAnimate().fitCenter().diskCacheStrategy(cacheStrategy))
            .into(this)
}

fun ImageView.load(path: String?, @DrawableRes placeholderRes: Int, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .load(path ?: "")
            .apply(RequestOptions().error(placeholderRes).placeholder(placeholderRes).dontAnimate().fitCenter().diskCacheStrategy(cacheStrategy))
            .into(this)
}

fun ImageView.roundedImage(path: String?, placeholder: Drawable, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .load(path ?: "")
            .apply(RequestOptions().error(placeholder).placeholder(placeholder).centerCrop().diskCacheStrategy(cacheStrategy).transforms(CropCircleTransformation()))
            .into(this)
}

fun ImageView.loadAsBitmap(path: String?, onResourceReady: (Bitmap) -> Unit, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .asBitmap()
            .load(path ?: "")
            .apply(RequestOptions().dontAnimate().fitCenter().diskCacheStrategy(cacheStrategy))
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    onResourceReady(bitmap)
                    setImageBitmap(bitmap)
                }
            })
}

fun ImageView.loadAsBitmap(path: String?, listener: RequestListener<Bitmap>, onResourceReady: (Bitmap) -> Unit, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.RESOURCE) {
    Glide.with(context)
            .asBitmap()
            .load(path ?: "")
            .listener(listener)
            .apply(RequestOptions().dontAnimate().fitCenter().diskCacheStrategy(cacheStrategy))
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    onResourceReady(bitmap)
                    setImageBitmap(bitmap)
                }
            })
}

@Suppress("DEPRECATION")
fun FloatingActionButton.refreshColorState(enabled: Boolean) {
    backgroundTintList = when (enabled) {
        true -> ColorStateList.valueOf(context.getThemeResource(R.attr.colorPrimary))
        else -> ColorStateList.valueOf(context.resources.getColor(android.R.color.darker_gray))
    }
}