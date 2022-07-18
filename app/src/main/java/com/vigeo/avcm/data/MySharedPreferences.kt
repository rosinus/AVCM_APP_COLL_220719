package com.vigeo.avcm.data

import android.app.Application
/**
 *  참고 사이트
 *  https://gdbagooni.tistory.com/21
 * */
class MySharedPreferences : Application() {

    companion object {
        lateinit var pref : PreferencesUtil
    }

    override fun onCreate() {
        pref = PreferencesUtil(applicationContext)
        super.onCreate()
    }
}