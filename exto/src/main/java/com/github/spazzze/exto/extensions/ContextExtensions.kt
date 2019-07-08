package com.github.spazzze.exto.extensions

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.VectorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.OpenableColumns
import android.provider.Settings
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import java.io.File


/**
 * @author Space
 * @date 07.03.2017
 */


fun Handler.just(action: () -> Unit, delay: Long) {
    removeCallbacksAndMessages(null)
    postDelayed({ action() }, delay)
}

@Throws(Resources.NotFoundException::class)
fun Context.getDimensionValue(@DimenRes dimenRes: Int) = with(resources) { (getDimension(dimenRes) / displayMetrics.density).toInt() }

fun Context.getDisplayMetrics(): DisplayMetrics = DisplayMetrics()
        .apply { (getSystemService(WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.getMetrics(this) }

fun Context.showAppSettings() {
    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")))
}

@Throws(Resources.NotFoundException::class)
fun Context.getAppBarSize(): Float {
    val styledAttributes = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
    val appBarSize = styledAttributes.getDimension(0, 0f)
    styledAttributes.recycle()
    return appBarSize
}

fun Context.isAppInstalled(packageName: String): Boolean = try {
    packageManager.getApplicationInfo(packageName, 0).enabled
} catch (e: Exception) {
    false
}

fun Context.getThemeResource(resourceId: Int) = TypedValue().run {
    theme.resolveAttribute(resourceId, this, true)
    data
}

fun Context.isNetworkAvailable() = try {
    (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
            ?.activeNetworkInfo?.isConnectedOrConnecting
            ?: false
} catch (e: java.lang.Exception) {
    false
}

fun Context.getPackageInfoOrNull(): PackageInfo? = try {
    packageManager.getPackageInfo(packageName, 0)
} catch (e: PackageManager.NameNotFoundException) {
    e.reportToDeveloper("Context.getPackageInfoOrNull cannot retrieve info")
    null
}

fun Context.getUriFromFile(authority: String, file: File) = FileProvider.getUriForFile(this, authority, file)

fun Context.createDrawable(@DrawableRes drawableRes: Int): Drawable = VectorDrawableCompat.create(resources, drawableRes, null)
        ?: GradientDrawable().apply { shape = GradientDrawable.RECTANGLE; setColor(Color.WHITE) }

fun Context.getFileSizeInBytes(uri: Uri): Long = contentResolver.query(uri, null, null, null, null)?.use {
    val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
    it.moveToFirst()
    it.getLong(sizeIndex)
} ?: 0

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.createBitmapFromDrawable(@DrawableRes drawableId: Int): Bitmap = when (val drawable = ContextCompat.getDrawable(this, drawableId)) {
    is BitmapDrawable -> drawable.bitmap
    is VectorDrawable -> drawable.toBitmap()
    else -> throw IllegalArgumentException("Unsupported drawable type: $drawable")
}