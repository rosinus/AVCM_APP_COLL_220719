package com.vigeo.avcm.myInfo.view

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.webkit.WebSettings.LOAD_NO_CACHE
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.WebDaumAddrBinding
import okhttp3.internal.http.BridgeInterceptor


class MyInfoWebViewDaumAddr : AppCompatActivity() {

    var daumWebView: WebView? = null

    val WebDaumAddrBinding: WebDaumAddrBinding by lazy {
        com.vigeo.avcm.databinding.WebDaumAddrBinding.inflate(layoutInflater)
    }

    var client: WebViewClient = object : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(WebDaumAddrBinding.root)

    }
}