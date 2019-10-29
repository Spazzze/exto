package com.github.spazzze.exto.view.interfaces

import androidx.annotation.StringRes

interface IAlerter : IView {

    fun showAlert(message: String)

    fun showAlert(@StringRes messageId: Int) = showAlert(ctx.getString(messageId))
}