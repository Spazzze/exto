package com.github.spazzze.exto.extensions

/**
 * @author Space
 * @date 24.01.2017
 */

@Synchronized
fun <T> MutableCollection<T>.replaceAllBy(list: List<T>) =
        if (list != this) {
            clear()
            if (list.isNotEmpty()) addAll(list) else true
        } else false

@Synchronized
inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapToWithReplace(destination: C, transform: (T) -> R): Boolean =
        destination.replaceAllBy(this.mapTo(ArrayList()) { transform(it) })

@Synchronized
fun <T, V> MutableMap<T, V>.replaceAllBy(list: List<V>, transform: (V) -> T) {
    if (list != this.values) {
        clear()
        list.forEach { this.put(transform(it), it) }
    }
}

@Synchronized
fun <T, V> MutableMap<T, V>.removeWithAction(key: T, action: (V) -> Unit) = remove(key)?.let(action)
        ?: Unit

inline fun <T> Collection<T>.forEachReversed(action: (T) -> Unit) {
    for (n in size - 1 downTo 0) action(elementAt(n))
}