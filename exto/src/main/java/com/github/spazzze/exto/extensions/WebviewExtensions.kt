package com.github.spazzze.exto.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * @author Space
 * @date 21.03.2017
 */

@SuppressLint("SetJavaScriptEnabled")
fun WebView.init(webViewClient: WebViewClient? = null, webChromeClient: WebChromeClient? = null, noCache: Boolean = true) {
    with(settings) {
        cacheMode = if (noCache) WebSettings.LOAD_NO_CACHE else WebSettings.LOAD_CACHE_ELSE_NETWORK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        javaScriptEnabled = true
        javaScriptCanOpenWindowsAutomatically = true
        mediaPlaybackRequiresUserGesture = false
        builtInZoomControls = true
        displayZoomControls = false
        useWideViewPort = true
        loadWithOverviewMode = true
        allowFileAccessFromFileURLs = true
        allowUniversalAccessFromFileURLs = true
    }
    clearCache(noCache)
    webViewClient?.let { setWebViewClient(it) }
    webChromeClient?.let { setWebChromeClient(it) }
}

fun WebView.loadCustomErrorPage(@StringRes stringId: Int, @ColorRes bgColorResId: Int = android.R.color.white) {
    stopLoading()
    val color = try {
        "#" + Integer.toHexString(ContextCompat.getColor(context, bgColorResId))
    } catch (e: Exception) {
        "#ffffff"
    }
    val htmlData = "<html><body bgcolor=\"$color\"><div align=\"center\">${context.getString(stringId)}</div></body>"
    loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
    invalidate()
}