package com.github.spazzze.exto.view.activities

import android.app.AlertDialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.spazzze.exto.view.interfaces.IBaseView

abstract class BaseActivity : AppCompatActivity(), IBaseView {

    final override val ctx: Context get() = this

    final override val currentActivity get() = this

    override fun showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun showMessage(@StringRes messageId: Int) = showMessage(getString(messageId))

    override fun showNotification(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showNotification(@StringRes messageId: Int) = showNotification(getString(messageId))

    override fun showDialogAlert(title: String, message: String, onDismiss: () -> Unit) {
        if (message.isBlank()) return
        with(AlertDialog.Builder(this)) {
            if (title.isNotBlank()) setTitle(title)
            this.setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss() }
                    .setOnDismissListener { onDismiss() }
                    .create()
                    .show()
        }
    }

    override fun showDialogAlert(@StringRes titleId: Int, @StringRes messageId: Int, onDismiss: () -> Unit) =
            AlertDialog.Builder(this)
                    .setTitle(titleId)
                    .setMessage(messageId)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss() }
                    .setOnDismissListener { onDismiss() }
                    .create()
                    .show()

    override fun showDialogAlert(@StringRes titleId: Int, @StringRes messageId: Int, onPositive: () -> Unit, onCancel: () -> Unit) =
            AlertDialog.Builder(this)
                    .setTitle(titleId)
                    .setMessage(messageId)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok) { _, _ -> onPositive() }
                    .setNegativeButton(android.R.string.cancel) { d, _ -> d.cancel() }
                    .setOnCancelListener { onCancel() }
                    .create()
                    .show()

    override fun showAlert(message: String) = showMessage(message)

    override fun onUiThread(action: IBaseView.() -> Unit) = runOnUiThread { action() }
}