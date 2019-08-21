package com.github.spazzze.exto.utils.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Process
import android.support.v4.app.ActivityCompat
import android.support.v4.app.AppOpsManagerCompat
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker
import android.support.v4.util.SimpleArrayMap
import com.github.spazzze.exto.utils.permissions.abs.IPermissionsChecker

/**
 * @author Space
 * @date 14.02.2018
 */

object PermissionUtils : IPermissionsChecker {

    private val MIN_SDK_PERMISSIONS = SimpleArrayMap<String, Int>(8)

    init {
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", Integer.valueOf(14))
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", Integer.valueOf(20))
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", Integer.valueOf(16))
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", Integer.valueOf(16))
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", Integer.valueOf(9))
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", Integer.valueOf(16))
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", Integer.valueOf(23))
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", Integer.valueOf(23))
    }

    override fun verifyPermissions(vararg grantResults: Int): Boolean = when {
        grantResults.isEmpty() -> false
        else -> (0 until grantResults.size)
                .map { grantResults[it] }
                .none { it != PackageManager.PERMISSION_GRANTED }
    }

    override fun hasSelfPermissions(context: Context, vararg permissions: String): Boolean = (0 until permissions.size)
            .map { permissions[it] }
            .none { permissionExists(it) && !hasSelfPermission(context, it) }

    override fun shouldShowRequestPermissionRationale(activity: Activity, vararg permissions: String): Boolean = (0 until permissions.size)
            .any { ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[it]) }

    override fun shouldShowRequestPermissionRationale(fragment: Fragment, vararg permissions: String): Boolean = (0 until permissions.size)
            .map { permissions[it] }
            .any { fragment.shouldShowRequestPermissionRationale(it) }

    private fun permissionExists(permission: String): Boolean = VERSION.SDK_INT >= MIN_SDK_PERMISSIONS.get(permission) ?: 0

    private fun hasSelfPermission(context: Context, permission: String): Boolean =
            if (VERSION.SDK_INT >= 23 && "Xiaomi".equals(Build.MANUFACTURER, ignoreCase = true)) {
                hasSelfPermissionForXiaomi(context, permission)
            } else {
                try {
                    PermissionChecker.checkSelfPermission(context, permission) == 0
                } catch (var3: RuntimeException) {
                    false
                }

            }

    private fun hasSelfPermissionForXiaomi(context: Context, permission: String): Boolean = AppOpsManagerCompat.permissionToOp(permission)?.let {
        AppOpsManagerCompat.noteOp(context, it, Process.myUid(), context.packageName) == 0 && PermissionChecker.checkSelfPermission(context, permission) == 0
    } ?: true
}