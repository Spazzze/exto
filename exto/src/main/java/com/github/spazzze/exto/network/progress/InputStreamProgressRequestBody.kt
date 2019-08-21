package com.github.spazzze.exto.network.progress

import android.content.ContentResolver
import android.net.Uri
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import java.io.IOException

/**
 * @author Space
 * @date 05.06.2018
 */

class InputStreamProgressRequestBody(responseBody: RequestBody,
                                     listener: RequestProgressListener,
                                     private val contentResolver: ContentResolver,
                                     private val uri: Uri) : MultipartProgressRequestBody(responseBody, listener) {

    override fun contentLength(): Long = -1

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = Okio.buffer(countingSink)
        Okio.source(contentResolver.openInputStream(uri)).use(bufferedSink::writeAll)
    }
}