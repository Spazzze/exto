package com.github.spazzze.exto.view.interfaces

import android.support.annotation.StringRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.Menu

/**
 * @author Space
 * @date 05.02.2017
 */

interface INavigationManager : NavigationView.OnNavigationItemSelectedListener {

    val navMenu: Menu
    val drawerLayout: DrawerLayout

    fun changeNavItemTitle(menuItemId: Int, @StringRes titleId: Int) =
            navMenu.findItem(menuItemId)?.setTitle(titleId)

    fun changeNavItemVisibility(menuItemId: Int, visible: Boolean) =
            navMenu.findItem(menuItemId)?.setVisible(visible)

    fun openDrawer() = drawerLayout.openDrawer(GravityCompat.START)

    fun closeDrawer(): Boolean = drawerLayout.isDrawerOpen(GravityCompat.START).apply {
        if (this) drawerLayout.closeDrawer(GravityCompat.START)
    }
}