package com.github.spazzze.exto.extensions

import android.app.Activity
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.provider.MediaStore
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import timber.log.Timber
import java.io.File

/**
 * @author Space
 * @date 01.04.2017
 */

fun AppCompatActivity.buildToolbar(toolbar: Toolbar, showArrowBtn: Boolean = true, showTitle: String = "", @MenuRes menuRes: Int = 0) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        title = showTitle
        setDisplayShowTitleEnabled(showTitle.isNotBlank())
        setDisplayHomeAsUpEnabled(showArrowBtn)
    }
    if (menuRes != 0) toolbar.inflateMenu(menuRes)
}

fun Activity.shareImage(uri: Uri) = ShareCompat.IntentBuilder
        .from(this)
        .setType("image/*")
        .setStream(uri)
        .startChooser()

fun Activity.mailTo(url: String): Boolean = try {
    with(MailTo.parse(url)) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        if (to?.trim().isNotNullOrBlank()) intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        if (subject?.trim().isNotNullOrBlank()) intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (body?.trim().isNotNullOrBlank()) intent.putExtra(Intent.EXTRA_TEXT, body)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            return true
        }
        return false
    }
} catch (e: Exception) {
    false
}

fun Activity.callTo(url: String) = try {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
    startActivity(intent)
} catch (e: Exception) {
    Timber.e(e, "callTo intent error: cannot call intent ACTION_DIAL $url")
}

fun Activity.takePicture(file: File, intentId: Int, fragment: Fragment? = null) = try {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply { putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)) }
    fragment?.startActivityForResult(intent, intentId) ?: startActivityForResult(intent, intentId)
} catch (e: Exception) {
    Timber.e(e, "takePhoto intent error: ")
}

fun Activity.takeVideo(file: File, intentId: Int, maximumDurationInSeconds: Int = 0, isHighQuality: Boolean = true, fragment: Fragment? = null) = try {
    with(Intent(MediaStore.ACTION_VIDEO_CAPTURE)) {
        putExtra(MediaStore.EXTRA_DURATION_LIMIT, if (maximumDurationInSeconds > 0) maximumDurationInSeconds else 30)
        putExtra(MediaStore.EXTRA_VIDEO_QUALITY, if (isHighQuality) 1 else 0)
        putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        fragment?.startActivityForResult(this, intentId) ?: startActivityForResult(this, intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "takeVideo intent error: ")
}

fun Activity.takeVideo(intentId: Int, maximumDurationInSeconds: Int = 0, isHighQuality: Boolean = true, fragment: Fragment? = null) = try {
    with(Intent(MediaStore.ACTION_VIDEO_CAPTURE)) {
        putExtra(MediaStore.EXTRA_DURATION_LIMIT, if (maximumDurationInSeconds > 0) maximumDurationInSeconds else 30)
        putExtra(MediaStore.EXTRA_VIDEO_QUALITY, if (isHighQuality) 1 else 0)
        fragment?.startActivityForResult(this, intentId) ?: startActivityForResult(this, intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "takeVideo intent error: ")
}

fun Activity.chooseFromGallery(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply { type = "image/*" }
    fragment?.startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
            ?: startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseFromGallery intent error: ")
}

fun Activity.chooseAudio(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "audio/*" }
    fragment?.startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
            ?: startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseAudio intent error: ")
}

fun Activity.chooseMp3Audio(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "audio/mpeg" }
    fragment?.startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
            ?: startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseMp3Audio intent error: ")
}

fun Activity.chooseVideo(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "video/*" }
    fragment?.startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
            ?: startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseVideo intent error: ")
}

fun Activity.chooseMp4Video(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "video/mp4" }
    fragment?.startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
            ?: startActivityForResult(Intent.createChooser(intent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseMp4Video intent error: ")
}