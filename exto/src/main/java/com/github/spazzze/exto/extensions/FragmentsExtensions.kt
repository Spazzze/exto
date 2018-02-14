package com.github.spazzze.exto.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import org.funktionale.option.Option
import org.funktionale.option.toOption
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.Serializable

/**
 * @author Space
 * @date 29.01.2017
 */

fun Fragment.mailTo(url: String): Boolean = try {
    with(MailTo.parse(url)) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        if (to?.trim().isNotNullOrBlank()) intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        if (subject?.trim().isNotNullOrBlank()) intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (body?.trim().isNotNullOrBlank()) intent.putExtra(Intent.EXTRA_TEXT, body)
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(intent)
            return true
        }
        return false
    }
} catch (e: Exception) {
    false
}

fun Fragment.takePicture(file: File, intentId: Int) = try {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
    startActivityForResult(takePictureIntent, intentId)
} catch (e: Exception) {
    Timber.e(e, "takePhoto intent error: ")
}

fun Fragment.chooseFromGallery(@StringRes titleRes: Int, intentId: Int) = try {
    val takeFromGalleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    takeFromGalleryIntent.type = "image/*"
    startActivityForResult(Intent.createChooser(takeFromGalleryIntent, getString(titleRes)), intentId)
} catch (e: Exception) {
    Timber.e(e, "chooseFromGallery intent error: ")
}

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