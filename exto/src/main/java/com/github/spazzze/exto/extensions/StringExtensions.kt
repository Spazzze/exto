package com.github.spazzze.exto.extensions

/**
 * @author elena
 * @date 01/02/17
 */

fun StringBuffer.addNotEmptyLine(str: String?) = apply {
    str?.let { if (it.isNotBlank()) this.append(it).append("\n") }
}

fun CharSequence?.isNotNullOrBlank(): Boolean = this != null && this.isNotBlank()

fun CharSequence?.match(pattern: String) = this != null && this.trim().matches(pattern.toRegex())

fun String.parseInt(): Int = try {
    Integer.parseInt(this)
} catch (e: Exception) {
    -1
}