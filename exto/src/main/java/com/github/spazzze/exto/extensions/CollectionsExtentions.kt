package com.github.spazzze.exto.extensions

/**
 * @author Space
 * @date 24.01.2017
 */

@Synchronized
fun <T> MutableCollection<T>.replaceAllBy(list: List<T>): Boolean = if (list == this) false else with(this) {
    val isCleared = removeAll { true }
    if (list.isNotEmpty()) addAll(list) else isCleared
}

@Synchronized
fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapToWithReplace(destination: C, transform: (T) -> R): Boolean =
        destination.replaceAllBy(mapTo(mutableListOf()) { transform(it) })

@Synchronized
fun <T, V> MutableMap<T, V>.replaceAllBy(list: List<V>, transform: (V) -> T) = if (list == values) false else {
    clear()
    list.forEach { this[transform(it)] = it }
    true
}

@Synchronized
fun <T, V> MutableMap<T, V>.removeWithAction(key: T, action: (V) -> Unit) = remove(key)?.let(action)
        ?: Unit

inline fun <T> Collection<T>.forEachReversed(action: (T) -> Unit) {
    for (n in size - 1 downTo 0) action(elementAt(n))
}

@Synchronized
fun <T> MutableCollection<T>.replaceFromIndexBy(list: List<T>, startingIndex: Int = 0): Boolean = if (list == this || list.isEmpty()) false else with(this) {
    val firstList = if (startingIndex > 0) toList().slice(0 until startingIndex) else listOf()
    val secondList = if (size > list.size) toList().slice((startingIndex + list.lastIndex) until size) else listOf()
    removeAll { true }
    addAll(firstList + list + secondList)
}