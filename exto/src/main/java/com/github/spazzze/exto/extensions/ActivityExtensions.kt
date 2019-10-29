package com.github.spazzze.exto.extensions

import android.app.Activity
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
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
        val runIntent = Intent(Intent.ACTION_SENDTO)
        runIntent.data = Uri.parse("mailto:")
        if (to?.trim().isNotNullOrBlank()) runIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        if (subject?.trim().isNotNullOrBlank()) runIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (body?.trim().isNotNullOrBlank()) runIntent.putExtra(Intent.EXTRA_TEXT, body)
        if (runIntent.resolveActivity(packageManager) != null) {
            startActivity(runIntent)
            return true
        }
        return false
    }
} catch (e: Exception) {
    false
}

fun Activity.callTo(url: String) = try {
    with(Intent(Intent.ACTION_DIAL, Uri.parse(url))) { startActivity(this) }
} catch (e: Exception) {
    Timber.e(e, "callTo intent error: cannot call intent ACTION_DIAL $url")
}

fun Activity.takePicture(file: File, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply { putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)) }) {
        fragment?.startActivityForResult(this, intentId)
                ?: startActivityForResult(this, intentId)
    }
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

fun Activity.chooseImageContent(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "chooseImageContent intent error: ")
}

fun Activity.pickImage(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply { type = "image/*" }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "pickImage intent error: ")
}

fun Activity.chooseAudioContent(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_GET_CONTENT).apply { type = "audio/*"; addCategory(Intent.CATEGORY_OPENABLE) }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "chooseAudioContent intent error: ")
}

fun Activity.pickAudio(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).apply { type = "audio/*" }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "pickAudio intent error: ")
}

fun Activity.chooseMp3AudioContent(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_GET_CONTENT).apply { type = "audio/mpeg"; addCategory(Intent.CATEGORY_OPENABLE) }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "chooseMp3AudioContent intent error: ")
}

fun Activity.pickMp3Audio(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).apply { type = "audio/mpeg" }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "pickAudio intent error: ")
}

fun Activity.chooseVideoContent(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_GET_CONTENT).apply { type = "video/*"; addCategory(Intent.CATEGORY_OPENABLE) }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "chooseVideoContent intent error: ")
}

fun Activity.pickVideo(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI).apply { type = "video/*" }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "pickVideo intent error: ")
}

fun Activity.chooseMp4VideoContent(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_GET_CONTENT).apply { type = "video/mp4"; addCategory(Intent.CATEGORY_OPENABLE) }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "chooseMp4VideoContent intent error: ")
}

fun Activity.pickMp4Video(@StringRes titleRes: Int, intentId: Int, fragment: Fragment? = null) = try {
    with(Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI).apply { type = "video/mp4" }) {
        fragment?.startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
                ?: startActivityForResult(Intent.createChooser(this, getString(titleRes)), intentId)
    }
} catch (e: Exception) {
    Timber.e(e, "pickMp4Video intent error: ")
}