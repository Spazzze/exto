package com.github.spazzze.exto.extensions

import android.databinding.*

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

fun <K, V, C : ObservableArrayMap<K, V>> C.onChange(action: C.() -> Unit) = apply {
    addOnMapChangedCallback(object : ObservableMap.OnMapChangedCallback<C, K, V>() {
        override fun onMapChanged(p0: C, p1: K) = p0.action()
    })
}

operator fun ObservableInt.inc(): ObservableInt = apply { set(get() + 1) }

operator fun ObservableInt.dec(): ObservableInt = apply { set(get() - 1) }

fun ObservableInt.incBy(count: Int): ObservableInt = apply { set(get() + count) }

fun ObservableBoolean.invert() = apply { set(!get()) }