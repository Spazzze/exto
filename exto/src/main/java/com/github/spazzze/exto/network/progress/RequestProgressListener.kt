package com.github.spazzze.exto.network.progress

/**
 * @author Space
 * @date 05.06.2018
 */

interface RequestProgressListener {
    fun update(bytesRead: Long, contentLength: Long)
}