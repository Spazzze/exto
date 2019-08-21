package com.github.spazzze.exto.network.progress

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException


/**
 * @author Space
 * @date 05.06.2018
 */

open class MultipartProgressRequestBody(private val responseBody: RequestBody,
                                        private val listener: RequestProgressListener) : RequestBody() {

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun contentLength(): Long = try {
        responseBody.contentLength()
    } catch (e: IOException) {
        e.printStackTrace()
        -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = Okio.buffer(countingSink)
        responseBody.writeTo(bufferedSink)
        bufferedSink.flush()
    }

    inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {

        private var bytesWritten: Long = 0

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            listener.update(bytesWritten, contentLength())
        }
    }
}