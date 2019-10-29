package com.github.spazzze.exto.databinding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.request.RequestListener
import com.github.spazzze.exto.extensions.load
import com.github.spazzze.exto.extensions.loadAsBitmap
import com.github.spazzze.exto.extensions.roundedImage

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.load(url)
}

@BindingAdapter("placeholder")
fun loadImage(view: ImageView, placeholder: Drawable?) {
    view.load("", placeholder ?: return)
}

@BindingAdapter("imageUrl", "placeholder")
fun loadImage(view: ImageView, url: String?, placeholder: Drawable?) {
    view.load(url, placeholder ?: return)
}

@BindingAdapter("imageUrl", "placeholderRes")
fun loadImage(view: ImageView, url: String?, @DrawableRes placeholderRes: Int?) {
    view.load(url, placeholderRes ?: return)
}

@BindingAdapter("roundedImage", "placeholder")
fun setRoundedImage(view: ImageView, url: String?, placeholder: Drawable?) {
    view.roundedImage(url, placeholder ?: return)
}

@BindingAdapter("roundedImage", "placeholderRes")
fun setRoundedImage(view: ImageView, url: String?, @DrawableRes placeholderRes: Int?) {
    view.roundedImage(url, placeholderRes ?: return)
}

@BindingAdapter("imageUrl", "onResourceReady")
fun loadImage(view: ImageView, url: String?, onResourceReady: (Bitmap) -> Unit) {
    view.loadAsBitmap(url, onResourceReady)
}

@BindingAdapter("imageUrl", "imageListener")
fun loadImageWithListener(view: ImageView, url: String?, listener: RequestListener<Drawable>) {
    view.load(url, listener)
}

@BindingAdapter("imageUrl", "imageListener", "onResourceReady")
fun loadImageWithListener(view: ImageView, url: String?, listener: RequestListener<Bitmap>, onResourceReady: (Bitmap) -> Unit) {
    view.loadAsBitmap(url, listener, onResourceReady)
}

@BindingAdapter("srcCompat")
fun loadImageDrawable(view: ImageView, drawable: Drawable?) {
    view.setImageDrawable(drawable ?: return)
}

@BindingAdapter("srcCompat")
fun loadImageDrawable(view: ImageView, drawableRes: Int?) {
    drawableRes ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) view.setImageResource(drawableRes)
    else view.setImageDrawable(VectorDrawableCompat.create(view.resources, drawableRes, null))
}

@BindingAdapter("alpha")
fun setAlpha(view: View, alpha: Float?) {
    if (alpha == null || alpha > 1f) return
    view.alpha = alpha
}