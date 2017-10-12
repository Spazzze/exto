package com.github.spazzze.exto.extensions

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.support.annotation.DimenRes
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

fun Context.getPackageInfo(): PackageInfo = try {
    packageManager.getPackageInfo(packageName, 0)
} catch (e: PackageManager.NameNotFoundException) {
    throw RuntimeException(e)
}

fun Context.getUriFromFile(authority: String, file: File) = FileProvider.getUriForFile(this, authority, file)