package com.github.spazzze.exto.view

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception

/**
 * @author Space
 * @date 29.05.2018
 */

interface SimpleGlideLoadingListener<T> : RequestListener<T> {

    val onLoadComplete: (T) -> Unit
    val onExceptionAction: (Exception?) -> Unit

    override fun onResourceReady(resource: T, model: Any?, target: Target<T>?, dataSource: DataSource?, isFirstResource: Boolean) =
            onLoadComplete(resource).run { false }

    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean) =
            onExceptionAction(e).run { false }
}