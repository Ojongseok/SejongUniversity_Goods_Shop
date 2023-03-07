package com.example.sejonggoodsmallproject.ui.view.order

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {
    private lateinit var browser: WebView

    internal inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            val extra = Bundle()
            val intent = Intent()
            extra.putString("data", data)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.sejonggoodsmallproject.R.layout.activity_web_view)

        browser = findViewById<View>(com.example.sejonggoodsmallproject.R.id.webView) as WebView

        browser.getSettings().setJavaScriptEnabled(true)
        browser.getSettings().setDomStorageEnabled(true)
        browser.addJavascriptInterface(MyJavaScriptInterface(), "Android")

        browser.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError
            ) {
                handler.proceed() // SSL 에러가 발생해도 계속 진행
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        //ssl 인증이 없는 경우 해결을 위한 부분
        browser.setWebChromeClient(object : WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }
        })

        browser.loadUrl("https://ojongseok.github.io/index.html")
    }
}