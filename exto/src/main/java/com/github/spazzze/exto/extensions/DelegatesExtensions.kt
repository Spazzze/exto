package com.github.spazzze.exto.extensions

import android.content.Context
import com.github.spazzze.exto.common.App
import kotlin.properties.Delegates

/**
 * @author Space
 * @date 29.01.2017
 */

@Suppress("UNUSED")
fun <T> Delegates.preference(key: String,
                             default: T,
                             prefsName: String = App.ctx.packageName,
                             context: Context = App.ctx) = Preference(key, default, prefsName, context)