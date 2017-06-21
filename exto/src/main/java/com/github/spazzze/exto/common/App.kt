package com.github.spazzze.exto.common

import android.app.Application
import android.content.Context

/**
 * @author Space
 * @date 21.06.2017
 */

class App : Application() {

    override fun onCreate() {
        ctx = applicationContext
        super.onCreate()
    }

    companion object {
        lateinit var ctx: Context
    }
}