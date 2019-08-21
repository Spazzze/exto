package com.github.spazzze.exto.extensions

import android.annotation.TargetApi
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Bitmap.*
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


/**
 * @author Space
 * @date 24.01.2017
 */

fun Bitmap.scale(factor: Int): Bitmap = createScaledBitmap(this, width / factor, height / factor, false)

fun Bitmap.blur(rs: RenderScript, radius: Int) = apply {
    val input = Allocation.createFromBitmap(rs, this)
    val output = Allocation.createTyped(rs, input.type)
    val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    with(script) {
        setRadius(radius.toFloat())
        setInput(input)
        forEach(output)
    }
    output.copyTo(this)
}

fun Bitmap.save(path: String, quality: Int = 70): File {
    val output = File(path)
    output.delete()
    output.createNewFile()
    val fos = FileOutputStream(output)
    this.compress(CompressFormat.JPEG, quality, fos)
    fos.close()
    return output
}

fun Bitmap.flip(): Bitmap {
    val matrix = Matrix()
    matrix.preScale(-1.0F, 1.0F)
    return createBitmap(this, 0, 0, this.width, this.height, matrix, false)
}

fun Bitmap.rotate(degree: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree)
    return createBitmap(this, 0, 0, width, height, matrix, false)
}

fun Bitmap.resize(biggestSidePixelSize: Int) = (width.toFloat() / height.toFloat()).let {
    when (it > 1f) {
        true -> createScaledBitmap(this, biggestSidePixelSize, (biggestSidePixelSize / it).toInt(), true)
        else -> createScaledBitmap(this, (biggestSidePixelSize * it).toInt(), biggestSidePixelSize, true)
    }
}!!

fun Bitmap.compressInto(format: CompressFormat, quality: Int, file: File) {
    FileOutputStream(file).use { compress(format, quality, it) }
}

@Throws(FileNotFoundException::class)
fun ContentResolver.getResizedBitmap(imageUri: Uri, maxImagePixelSize: Int): Bitmap? {
    val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    openInputStream(imageUri).use { BitmapFactory.decodeStream(it, null, options) }
    options.apply {
        inJustDecodeBounds = false
        if (maxImagePixelSize > 0) inSampleSize = Math.max(outWidth, outHeight) / maxImagePixelSize
    }
    return openInputStream(imageUri).use { BitmapFactory.decodeStream(it, null, options) }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun VectorDrawable.toBitmap(): Bitmap = with(createBitmap(intrinsicWidth, intrinsicHeight, Config.ARGB_8888)) {
    val canvas = Canvas(this)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    this@with
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun VectorDrawable.toBitmap(biggestSidePixelSize: Int): Bitmap = with(intrinsicWidth.toFloat() / intrinsicHeight.toFloat()) {
    when {
        this > 1f -> createBitmap(biggestSidePixelSize, (biggestSidePixelSize / this).toInt(), Config.ARGB_8888)
        else -> createBitmap((biggestSidePixelSize * this).toInt(), biggestSidePixelSize, Config.ARGB_8888)
    }
}.also {
    val canvas = Canvas(it)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
}