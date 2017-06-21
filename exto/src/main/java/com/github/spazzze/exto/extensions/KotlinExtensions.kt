package com.github.spazzze.exto.extensions

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

inline fun <R> execute(block: () -> R): Unit {
    block()
}