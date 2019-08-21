package com.github.spazzze.exto.network.progress

import android.support.annotation.Keep
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * @author Space
 * @date 01.11.2016
 */

@Keep
class ProgressResponseBody(private val responseBody: ResponseBody,
                           private val progressListener: RequestProgressListener) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun contentLength(): Long = try {
        responseBody.contentLength()
    } catch (e: IOException) {
        e.printStackTrace()
        -1
    }

    override fun source(): BufferedSource = bufferedSource
            ?: Okio.buffer(source(responseBody.source())).apply { bufferedSource = this }

    private fun source(source: Source) = object : ForwardingSource(source) {
        internal var totalBytesRead = 0L

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            totalBytesRead += if (bytesRead.toInt() != -1) bytesRead else 0
            progressListener.update(totalBytesRead, responseBody.contentLength())
            return bytesRead
        }
    }
}