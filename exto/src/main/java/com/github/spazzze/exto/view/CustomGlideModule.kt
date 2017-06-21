package com.github.spazzze.exto.view

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.GlideModule

/**
 * @author Space
 * @date 10.12.2016
 */


class CustomGlideModule : GlideModule {

    override fun applyOptions(context: Context, builder: GlideBuilder) {

        val calculator = MemorySizeCalculator(context)
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customBitmapPoolSize = 2 * defaultBitmapPoolSize
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize))
        builder.setDiskCache(DiskLruCacheFactory(context.cacheDir.path, 1024 * 1024 * 500))
    }

    override fun registerComponents(context: Context, glide: Glide) {
    }
}
