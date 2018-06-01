package com.github.spazzze.exto.view

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest


class CropCircleTransformation : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int) =
            TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight)!!

    override fun updateDiskCacheKey(messageDigest: MessageDigest) = messageDigest.update(ID.toByteArray(Key.CHARSET))

    companion object {
        private const val VERSION = 1
        private const val ID = "com.github.spazzze.exto.view.CropCircleTransformation.$VERSION"
    }
}