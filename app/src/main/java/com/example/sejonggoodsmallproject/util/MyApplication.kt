package com.example.sejonggoodsmallproject.util

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var prefs: TokenSharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = TokenSharedPreferences(applicationContext)
    }
}