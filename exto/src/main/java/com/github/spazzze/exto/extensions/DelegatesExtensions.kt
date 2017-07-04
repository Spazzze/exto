package com.github.spazzze.exto.extensions

import android.content.Context
import kotlin.properties.Delegates

/**
 * @author Space
 * @date 29.01.2017
 */

@Suppress("UNUSED")
fun <T> Delegates.preference(key: String,
                             default: T,
                             context: Context,
                             prefsName: String? = null) = Preference(key, default, prefsName ?: context.packageName, context)