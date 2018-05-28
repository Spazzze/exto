package com.github.spazzze.exto.view.viewmodels.abs

import android.content.Context
import android.databinding.BaseObservable
import com.github.spazzze.exto.view.interfaces.IAlerter

abstract class BaseViewModel(val alerter: IAlerter?) : BaseObservable() {

    abstract val ctx: Context

    abstract fun handleNetworkException(t: Throwable)
}