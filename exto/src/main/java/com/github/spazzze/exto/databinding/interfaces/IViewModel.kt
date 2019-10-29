package com.github.spazzze.exto.databinding.interfaces

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.github.spazzze.exto.view.interfaces.IView

interface IViewModel : Observable {

    val iView: IView?

    fun handleNetworkException(t: Throwable)

    var propertyChangeRegistry: PropertyChangeRegistry?

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            propertyChangeRegistry?.add(callback)
                    ?: PropertyChangeRegistry().apply { add(callback); propertyChangeRegistry = this }
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) { propertyChangeRegistry?.remove(callback) }
    }

    fun notifyChange() {
        synchronized(this) { propertyChangeRegistry?.notifyCallbacks(this, 0, null) }
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) { propertyChangeRegistry?.notifyCallbacks(this, fieldId, null) }
    }
}