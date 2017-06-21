package com.github.spazzze.exto.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Space
 * @date 29.01.2017
 */

class Preference<T>(val key: String,
                    val default: T,
                    val prefsName: String,
                    val context: Context) : ReadWriteProperty<Any?, T> {

    val prefs: SharedPreferences by lazy { context.getSharedPreferences(prefsName, Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(key, default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putPreference(key, value)

    @Suppress("UNCHECKED_CAST")
    private fun <U> findPreference(name: String, default: U): U = when (default) {
        is Long -> prefs.getLong(name, default) as U
        is String -> prefs.getString(name, default) as U
        is Int -> prefs.getInt(name, default) as U
        is Boolean -> prefs.getBoolean(name, default) as U
        is Float -> prefs.getFloat(name, default) as U
        else -> throw IllegalArgumentException("This type can be saved into Preferences")
    }

    @SuppressLint("CommitPrefEdits")
    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }
}
