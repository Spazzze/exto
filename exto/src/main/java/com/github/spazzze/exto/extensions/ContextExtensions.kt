package com.github.spazzze.exto.extensions

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.support.annotation.DimenRes
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager


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

fun Context.isNetworkAvailable() = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
        ?.activeNetworkInfo?.isConnectedOrConnecting
        ?: false