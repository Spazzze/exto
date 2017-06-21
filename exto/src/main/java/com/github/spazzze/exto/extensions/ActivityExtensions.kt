package com.github.spazzze.exto.extensions

import android.app.Activity
import android.net.Uri
import android.support.annotation.MenuRes
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

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