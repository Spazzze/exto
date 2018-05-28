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

inline fun <R> execute(block: () -> R) {
    block()
}

/**
 * Calls the specified function [block] with `this` value as its receiver but returns Unit.
 */
inline fun <T, R> T.execute(block: T.() -> R) {
    block()
}

inline fun tryThis(actionOnTry: () -> Unit, actionOnCatch: () -> Unit = {}, message: String = "") {
    try {
        actionOnTry()
    } catch (t: Throwable) {
        t.reportToDeveloper("try was not successful $message")
        actionOnCatch()
    }
}