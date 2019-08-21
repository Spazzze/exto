package com.github.spazzze.exto.utils.permissions.abs

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

/**
 * @author Space
 * @date 14.02.2018
 */

interface IPermissionsChecker {

    fun verifyPermissions(vararg grantResults: Int): Boolean

    fun hasSelfPermissions(context: Context, vararg permissions: String): Boolean

    fun shouldShowRequestPermissionRationale(activity: Activity, vararg permissions: String): Boolean

    fun shouldShowRequestPermissionRationale(fragment: Fragment, vararg permissions: String): Boolean
}