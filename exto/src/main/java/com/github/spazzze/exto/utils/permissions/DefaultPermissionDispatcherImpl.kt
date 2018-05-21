package com.github.spazzze.exto.utils.permissions

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.support.v4.app.Fragment
import com.github.spazzze.exto.extensions.runIf
import com.github.spazzze.exto.utils.permissions.abs.IExecutablePermissionRequest
import com.github.spazzze.exto.utils.permissions.abs.IPermissionRequest
import java.lang.ref.WeakReference

/**
 * @author Space
 * @date 12.02.2018
 */

open class DefaultPermissionDispatcherImpl(private val permissionsArray: Array<String>,
                                           private val requestCode: Int,
                                           private val onShowRationale: (request: IPermissionRequest) -> Unit,
                                           private val onDeniedAction: () -> Unit,
                                           private val onNeverAskAction: () -> Unit = onDeniedAction) {

    private var pendingRequest: IExecutablePermissionRequest? = null

    @TargetApi(Build.VERSION_CODES.M)
    fun executeWithCheck(target: Activity, action: () -> Unit) {
        if (PermissionUtils.hasSelfPermissions(target, *permissionsArray)) {
            action()
            return
        }
        with(PendingActivityPermissionRequest(target, permissionsArray, requestCode, action, onDeniedAction)) {
            pendingRequest = this
            when {
                PermissionUtils.shouldShowRequestPermissionRationale(target, *permissionsArray) -> onShowRationale(this)
                else -> target.requestPermissions(permissionsArray, requestCode)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun executeWithCheck(target: Fragment, action: () -> Unit) {
        if (PermissionUtils.hasSelfPermissions(target.activity, *permissionsArray)) {
            action()
            return
        }
        with(PendingFragmentPermissionRequest(target, permissionsArray, requestCode, action, onDeniedAction)) {
            pendingRequest = this
            when {
                PermissionUtils.shouldShowRequestPermissionRationale(target, *permissionsArray) -> onShowRationale(this)
                else -> target.requestPermissions(permissionsArray, requestCode)
            }
        }
    }

    fun onRequestPermissionsResult(target: Activity, code: Int, grantResults: IntArray) = runIf(code == requestCode) {
        when {
            PermissionUtils.verifyPermissions(*grantResults) -> pendingRequest?.execute()
            !PermissionUtils.shouldShowRequestPermissionRationale(target, *permissionsArray) -> onNeverAskAction()
            else -> onDeniedAction()
        }
        pendingRequest = null
    }

    fun onRequestPermissionsResult(target: Fragment, code: Int, grantResults: IntArray) = runIf(code == requestCode) {
        when {
            PermissionUtils.verifyPermissions(*grantResults) -> pendingRequest?.execute()
            !PermissionUtils.shouldShowRequestPermissionRationale(target, *permissionsArray) -> onNeverAskAction()
            else -> onDeniedAction()
        }
        pendingRequest = null
    }

    private class PendingActivityPermissionRequest(target: Activity,
                                                   private val permissionsArray: Array<String>,
                                                   private val requestCode: Int,
                                                   private val onSuccessAction: () -> Unit,
                                                   private val onDeniedAction: () -> Unit) : IExecutablePermissionRequest {

        private val weakTarget: WeakReference<Activity> = WeakReference(target)

        @TargetApi(Build.VERSION_CODES.M)
        override fun proceed() = weakTarget.get()?.requestPermissions(permissionsArray, requestCode)
                ?: Unit

        override fun cancel() = onDeniedAction()

        override fun execute() = onSuccessAction()
    }

    private class PendingFragmentPermissionRequest(target: Fragment,
                                                   private val permissionsArray: Array<String>,
                                                   private val requestCode: Int,
                                                   private val onSuccessAction: () -> Unit,
                                                   private val onDeniedAction: () -> Unit) : IExecutablePermissionRequest {

        private val weakTarget: WeakReference<Fragment> = WeakReference(target)

        @TargetApi(Build.VERSION_CODES.M)
        override fun proceed() = weakTarget.get()?.requestPermissions(permissionsArray, requestCode)
                ?: Unit

        override fun cancel() = onDeniedAction()

        override fun execute() = onSuccessAction()
    }
}