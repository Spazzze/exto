package com.github.spazzze.exto.view.interfaces

import android.support.annotation.StringRes

interface IBaseView : IAlerter {

    fun showMessage(message: String)

    fun showMessage(@StringRes messageId: Int)

    fun showNotification(message: String)

    fun showNotification(@StringRes messageId: Int)

    fun showDialogAlert(title: String, message: String, onDismiss: () -> Unit = {})

    fun showDialogAlert(@StringRes titleId: Int, @StringRes messageId: Int, onDismiss: () -> Unit = {})

    fun showDialogAlert(@StringRes titleId: Int, @StringRes messageId: Int, onPositive: () -> Unit, onCancel: () -> Unit = {})

    fun finish()
}