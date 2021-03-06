package com.github.spazzze.exto.extensions

import androidx.databinding.*

/**
 * @author elena
 * @date 09/02/17
 */

fun <T : BaseObservable> T.onChange(action: T.() -> Unit) = apply {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            action()
        }
    })
}

fun <T, C : ObservableArrayList<in T>> C.onChange(action: C.() -> Unit) = apply {
    addOnListChangedCallback(object : ObservableList.OnListChangedCallback<C>() {
        override fun onItemRangeRemoved(p0: C, p1: Int, p2: Int) = p0.action()
        override fun onItemRangeChanged(p0: C, p1: Int, p2: Int) = p0.action()
        override fun onChanged(p0: C) = p0.action()
        override fun onItemRangeMoved(p0: C, p1: Int, p2: Int, p3: Int) = p0.action()
        override fun onItemRangeInserted(p0: C, p1: Int, p2: Int) = p0.action()
    })
}

fun <K, V, C : ObservableArrayMap<K, V>> C.onChange(action: C.(K?) -> Unit) = apply {
    addOnMapChangedCallback(object : ObservableMap.OnMapChangedCallback<C, K, V>() {
        override fun onMapChanged(sender: C, key: K?) = sender.action(try {
            key
        } catch (e: Exception) {
            null
        })
    })
}

operator fun ObservableInt.inc(): ObservableInt = apply { set(get() + 1) }

operator fun ObservableInt.dec(): ObservableInt = apply { set(get() - 1) }

operator fun ObservableInt.plus(increment: Int) = ObservableInt(get() + increment)

fun ObservableInt.incBy(count: Int): ObservableInt = apply { set(get() + count) }

fun ObservableBoolean.invert() = apply { set(!get()) }