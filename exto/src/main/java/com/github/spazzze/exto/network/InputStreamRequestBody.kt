package com.github.spazzze.exto.network

import android.content.ContentResolver
import android.net.Uri
import android.support.annotation.Nullable
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import java.io.IOException


/**
 * @author Space
 * @date 20.02.2018
 */

class InputStreamRequestBody(private val contentType: MediaType?,
                             private val contentResolver: ContentResolver,
                             private val uri: Uri) : RequestBody() {

    @Nullable
    override fun contentType(): MediaType? = contentType

    override fun contentLength(): Long = -1

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        Okio.source(contentResolver.openInputStream(uri)).use(sink::writeAll)
    }
}