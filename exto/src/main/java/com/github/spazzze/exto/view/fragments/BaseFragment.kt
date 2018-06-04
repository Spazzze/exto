package com.github.spazzze.exto.view.fragments

import android.app.Activity
import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import com.github.spazzze.exto.extensions.execute
import com.github.spazzze.exto.view.fragments.interfaces.IBaseFragment
import com.github.spazzze.exto.view.interfaces.IBaseView

/**
 * @author Space
 * @date 23.03.2017
 */


abstract class BaseFragment<A : IBaseView> : Fragment(), IBaseFragment<A> {

    override val ctx: Context get() = context

    override var parentActivity: A? = null

    @CallSuper
    @Suppress("DEPRECATION", "UNCHECKED_CAST")
    override fun onAttach(activity: Activity?) {
        if (activity is IBaseView) parentActivity = activity as? A
        else throw IllegalArgumentException("Activity has to be instance of IBaseView")
        super.onAttach(activity)
    }

    @CallSuper
    override fun onDetach() {
        parentActivity = null
        super.onDetach()
    }

    override fun showMessage(message: String) {
        parentActivity?.showMessage(message)
    }

    override fun showMessage(@StringRes messageId: Int) {
        parentActivity?.showMessage(messageId)
    }

    override fun showNotification(message: String) {
        parentActivity?.showNotification(message)
    }

    override fun showNotification(@StringRes messageId: Int) {
        parentActivity?.showNotification(messageId)
    }

    override fun showDialogAlert(title: String, message: String, onDismiss: () -> Unit) {
        parentActivity?.showDialogAlert(title, message, onDismiss)
    }

    override fun showDialogAlert(@StringRes titleId: Int, @StringRes messageId: Int, onDismiss: () -> Unit) {
        parentActivity?.showDialogAlert(titleId, messageId, onDismiss)
    }

    override fun showDialogAlert(@StringRes titleId: Int, @StringRes messageId: Int, onPositive: () -> Unit, onCancel: () -> Unit) {
        parentActivity?.showDialogAlert(titleId, messageId, onPositive, onCancel)
    }

    override fun finish() = execute { activity.onBackPressed() }

    override fun showAlert(message: String) = showMessage(message)

    override fun onUiThread(action: IBaseView.() -> Unit) = execute { parentActivity?.onUiThread(action) }
}