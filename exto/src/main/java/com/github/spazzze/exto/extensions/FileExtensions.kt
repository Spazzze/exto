package com.github.spazzze.exto.extensions

import java.io.File
import java.io.IOException

/**
 * @author elena
 * @date 10/03/17
 */

@Throws(IOException::class)
fun File.create(): File = apply {
    File(parent).mkdirs()
    if (exists()) delete()
    createNewFile()
}