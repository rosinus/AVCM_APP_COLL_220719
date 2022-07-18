package com.vigeo.avcm.data

import android.content.Context

/**
 *  참고 사이트
 *  https://gdbagooni.tistory.com/21
 * */
class PreferencesUtil(context:Context) {
    private val pref = context.getSharedPreferences("user", 0)

    fun getString(key:String, value:String) : String {
        return pref.getString(key,value).toString()
    }

    fun setString(key:String, value:String){
        pref.edit().putString(key,value).apply()
    }

    //default value
    fun getBoolean(key:String, value:Boolean):Boolean{
        return pref.getBoolean(key, value)
    }

    fun setBoolean(key:String, value:Boolean){
        pref.edit().putBoolean(key,value).apply()
    }

    fun commit(){
        pref.edit().commit()
    }

    fun clear(){
        pref.edit().clear()
    }
}