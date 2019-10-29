package com.github.spazzze.exto.extensions

import androidx.annotation.IntRange
import java.math.BigDecimal
import java.math.RoundingMode


/**
 * @author Space
 * @date 21.03.2017
 */

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T = when {
    condition -> apply { block() }
    else -> this
}

inline fun <T> T.applyIf(condition: T.() -> Boolean, block: T.() -> Unit): T = when {
    condition() -> apply { block() }
    else -> this
}

inline fun runIf(condition: Boolean, block: () -> Unit) = condition.apply { if (this) block() }

inline fun <R> execute(block: () -> R) {
    block()
}

/**
 * Calls the specified function [block] with `this` value as its receiver but returns Unit.
 */
inline fun <T, R> T.execute(block: T.() -> R) {
    block()
}

/**
 * Calls the specified function [block] if condition is true
 * @returns Unit.
 */
inline fun <R> executeIf(condition: Boolean, block: () -> R): Unit = condition.execute { if (this) block() }

inline fun tryThis(actionOnTry: () -> Unit, actionOnCatch: () -> Unit = {}, message: String = "") {
    try {
        actionOnTry()
    } catch (t: Throwable) {
        t.reportToDeveloper("try was not successful $message")
        actionOnCatch()
    }
}

fun Double.round(@IntRange(from = 0, to = 15) decimalsAfterPoint: Int) = BigDecimal.valueOf(this).setScale(decimalsAfterPoint, RoundingMode.HALF_UP)

inline fun <reified T : Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}