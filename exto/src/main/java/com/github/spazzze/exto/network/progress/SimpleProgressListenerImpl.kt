package com.github.spazzze.exto.network.progress

import android.databinding.ObservableInt

/**
 * @author Space
 * @date 04.06.2018
 */

class SimpleProgressListenerImpl(private val downloadProgress: ObservableInt) : RequestProgressListener {

    override fun update(bytesRead: Long, contentLength: Long) = downloadProgress.set((100 * bytesRead / contentLength).toInt())
}