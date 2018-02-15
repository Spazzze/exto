package com.github.spazzze.exto.extensions

import android.app.Activity
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.provider.MediaStore
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
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

fun Activity.takePicture(file: File, intentId: Int) = try {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
    startActivityForResult(takePictureIntent, intentId)
} catch (e: Exception) {
    Timber.e(e, "takePhoto intent error: ")
}

fun Activity.chooseFromGallery(@StringRes titleRes: Int, intentId: Int) = try {
    val takeFromGalleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    takeFromGalleryIntent.type = "image/*"
    startActivityForResult(Intent.createChooser(takeFromGalleryIntent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseFromGallery intent error: ")
}