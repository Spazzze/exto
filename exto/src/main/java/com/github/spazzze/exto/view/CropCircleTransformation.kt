package com.github.spazzze.exto.view

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource

class CropCircleTransformation private constructor(private val bitmapPool: BitmapPool) : Transformation<Bitmap> {

    private var borderWidth: Float = 0.0f

    @ColorInt private var color = Color.WHITE

    constructor(context: Context) : this(Glide.get(context).bitmapPool)

    constructor(context: Context, borderWidth: Float, color: Int) : this(Glide.get(context).bitmapPool) {
        this.borderWidth = borderWidth
        this.color = color
    }

    override fun transform(resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
        val source = resource.get()
        val size = Math.min(source.width, source.height)
        val r = size / 2f

        val width = (source.width - size) / 2f
        val height = (source.height - size) / 2f

        val bitmap = bitmapPool.get(size, size, Bitmap.Config.ARGB_8888) ?: Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        if (width != 0f || height != 0f) {
            val matrix = Matrix()
            matrix.setTranslate((-width), (-height))
            shader.setLocalMatrix(matrix)
        }
        paint.shader = shader
        paint.isAntiAlias = true
        if (borderWidth != 0f) canvas.drawCircle(r, r, r, borderPaint)
        canvas.drawCircle(r, r, r - borderWidth, paint)

        return BitmapResource.obtain(bitmap, bitmapPool)
    }

    private val borderPaint: Paint
        get() {
            val border = Paint()
            border.color = color
            border.isAntiAlias = true
            return border
        }

    override fun getId(): String = this.javaClass.name
}
