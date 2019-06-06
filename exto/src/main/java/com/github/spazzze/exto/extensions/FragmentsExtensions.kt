package com.github.spazzze.exto.extensions

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import org.funktionale.option.Option
import org.funktionale.option.toOption
import java.io.File
import java.io.IOException
import java.io.Serializable


/**
 * @author Space
 * @date 29.01.2017
 */

fun Fragment.mailTo(url: String) = activity?.mailTo(url) ?: false

fun Fragment.callTo(url: String) = activity?.callTo(url)

fun Fragment.takePicture(file: File, intentId: Int) = activity?.takePicture(file, intentId, this)

fun Fragment.takeVideo(file: File, intentId: Int, maximumDurationInSeconds: Int = 0, isHighQuality: Boolean = true) =
        activity?.takeVideo(file, intentId, maximumDurationInSeconds, isHighQuality, this)

fun Fragment.takeVideo(intentId: Int, maximumDurationInSeconds: Int = 0, isHighQuality: Boolean = true) =
        activity?.takeVideo(intentId, maximumDurationInSeconds, isHighQuality, this)

fun Fragment.chooseImageContent(@StringRes titleRes: Int, intentId: Int) = activity?.chooseImageContent(titleRes, intentId, this)

fun Fragment.pickImage(@StringRes titleRes: Int, intentId: Int) = activity?.pickImage(titleRes, intentId, this)

fun Fragment.chooseAudioContent(@StringRes titleRes: Int, intentId: Int) = activity?.chooseAudioContent(titleRes, intentId, this)

fun Fragment.pickAudio(@StringRes titleRes: Int, intentId: Int) = activity?.pickAudio(titleRes, intentId, this)

fun Fragment.chooseMp3AudioContent(@StringRes titleRes: Int, intentId: Int) = activity?.chooseMp3AudioContent(titleRes, intentId, this)

fun Fragment.pickMp3Audio(@StringRes titleRes: Int, intentId: Int) = activity?.pickMp3Audio(titleRes, intentId, this)

fun Fragment.chooseVideoContent(@StringRes titleRes: Int, intentId: Int) = activity?.chooseVideoContent(titleRes, intentId, this)

fun Fragment.pickVideo(@StringRes titleRes: Int, intentId: Int) = activity?.pickVideo(titleRes, intentId, this)

fun Fragment.chooseMp4VideoContent(@StringRes titleRes: Int, intentId: Int) = activity?.chooseMp4VideoContent(titleRes, intentId, this)

fun Fragment.pickMp4Video(@StringRes titleRes: Int, intentId: Int) = activity?.pickMp4Video(titleRes, intentId, this)

inline fun <T : Fragment> FragmentManager.replaceFragment(containerId: Int, addToBackStack: Boolean,
                                                          tag: String?, createNewFragment: () -> T) =
        when (findFragmentByTag(tag)) {
            null -> add(createNewFragment(), containerId, addToBackStack, tag)
            else -> if (popBackStackImmediate(tag, 0)) true else clearBackStack()
        }

@SuppressLint("CommitTransaction")
fun <T : Fragment> FragmentManager.add(fragment: T, containerId: Int, addToBackStack: Boolean, tag: String?): Boolean =
        with(beginTransaction()) {
            replace(containerId, fragment, tag)
            if (addToBackStack) addToBackStack(tag)
            commitAllowingStateLoss()
            return true
        }

fun FragmentManager.clearBackStack(): Boolean {
    while (backStackEntryCount != 0) {
        try {
            popBackStackImmediate()
        } catch (e: Exception) {
            popBackStack()
        }
    }
    return true
}

fun FragmentActivity.isBackStackEmpty() = supportFragmentManager.backStackEntryCount < 1

@Throws(IOException::class)
inline fun <reified T : Any> Fragment.readArgs(key: String, default: T): T = arguments?.run {
    when (default) {
        is Boolean -> getBoolean(key, default)
        is Byte -> getByte(key, default)
        is Char -> getChar(key, default)
        is Short -> getShort(key, default)
        is Int -> getInt(key, default)
        is Long -> getLong(key, default)
        is Float -> getFloat(key, default)
        is Double -> getDouble(key, default)
        is String -> getString(key, default)
        is CharSequence -> getCharSequence(key, default)
        is Parcelable -> getParcelable(key) ?: default
        is Serializable -> getSerializable(key) ?: default
        is BooleanArray -> getBooleanArray(key) ?: default
        is ByteArray -> getByteArray(key) ?: default
        is CharArray -> getCharArray(key) ?: default
        is DoubleArray -> getDoubleArray(key) ?: default
        is FloatArray -> getFloatArray(key) ?: default
        is IntArray -> getIntArray(key) ?: default
        is LongArray -> getLongArray(key) ?: default
        is Array<*> -> {
            when {
                default.isArrayOf<Parcelable>() -> getParcelableArray(key) ?: default
                default.isArrayOf<CharSequence>() -> getCharSequenceArray(key) ?: default
                default.isArrayOf<String>() -> getStringArray(key) ?: default
                else -> throw IOException("Unsupported bundle component (${default.javaClass})")
            }
        }
        is ShortArray -> getShortArray(key) ?: default
        is Bundle -> getBundle(key) ?: default
        else -> throw IOException("Unsupported bundle component (${default.javaClass})")
    }
} as? T ?: default

@Throws(IOException::class)
inline fun <reified T : Any> Fragment.readArgs(key: String, default: T, handleBadArgs: () -> Unit): T = arguments?.run {
    when (default) {
        is Boolean -> getBoolean(key, default)
        is Byte -> getByte(key, default)
        is Char -> getChar(key, default)
        is Short -> getShort(key, default)
        is Int -> getInt(key, default)
        is Long -> getLong(key, default)
        is Float -> getFloat(key, default)
        is Double -> getDouble(key, default)
        is String -> getString(key, default)
        is CharSequence -> getCharSequence(key, default)
        is Parcelable -> getParcelable(key) ?: default
        is Serializable -> getSerializable(key) ?: default
        is BooleanArray -> getBooleanArray(key) ?: default
        is ByteArray -> getByteArray(key) ?: default
        is CharArray -> getCharArray(key) ?: default
        is DoubleArray -> getDoubleArray(key) ?: default
        is FloatArray -> getFloatArray(key) ?: default
        is IntArray -> getIntArray(key) ?: default
        is LongArray -> getLongArray(key) ?: default
        is Array<*> -> {
            when {
                default.isArrayOf<Parcelable>() -> getParcelableArray(key) ?: default
                default.isArrayOf<CharSequence>() -> getCharSequenceArray(key) ?: default
                default.isArrayOf<String>() -> getStringArray(key) ?: default
                else -> handleBadArgs()
            }
        }
        is ShortArray -> getShortArray(key) ?: default
        is Bundle -> getBundle(key) ?: default
        else -> handleBadArgs()
    }
} as? T ?: default.apply { if (this == default) handleBadArgs() }

inline fun <reified T : Parcelable> Fragment.readArgs(key: String, handleBadArgs: () -> Unit): Option<T> =
        arguments?.getParcelable<T>(key)?.toOption() ?: Option.None.apply { handleBadArgs() }