package com.github.spazzze.exto.extensions

import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * @author Space
 * @date 21.03.2017
 */

fun WebView.init(webViewClient: WebViewClient? = null, webChromeClient: WebChromeClient? = null, noCache: Boolean = true) {
    if (noCache) settings.cacheMode = WebSettings.LOAD_NO_CACHE
    else settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    clearCache(noCache)
    settings.javaScriptEnabled = true
    settings.javaScriptCanOpenWindowsAutomatically = true
    settings.mediaPlaybackRequiresUserGesture = false
    settings.builtInZoomControls = true
    settings.displayZoomControls = false
    settings.useWideViewPort = true
    settings.loadWithOverviewMode = true
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