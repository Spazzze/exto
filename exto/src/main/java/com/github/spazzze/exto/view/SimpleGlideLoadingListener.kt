package com.github.spazzze.exto.view

import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception

/**
 * @author Space
 * @date 29.05.2018
 */

interface SimpleGlideLoadingListener<T> : RequestListener<T, GlideDrawable> {

    val onLoadComplete: () -> Unit
    val onExceptionAction: (Exception) -> Unit

    override fun onResourceReady(resource: GlideDrawable?, model: T?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean) =
            onLoadComplete().run { false }

    override fun onException(e: Exception, model: T?, target: Target<GlideDrawable>?, isFirstResource: Boolean) =
            onExceptionAction(e).run { false }
}