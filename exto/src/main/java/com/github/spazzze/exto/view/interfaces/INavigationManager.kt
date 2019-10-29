package com.github.spazzze.exto.view.interfaces

import android.view.Menu
import androidx.annotation.StringRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

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