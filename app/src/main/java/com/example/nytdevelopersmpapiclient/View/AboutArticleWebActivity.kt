package com.example.nytdevelopersmpapiclient.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.nytdevelopersmpapiclient.R
import kotlinx.android.synthetic.main.activity_about_article_web.*

class AboutArticleWebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_article_web)

        webView.webViewClient = WebViewClient()
        webView.loadUrl(intent.extras.getString("URL",""))

        val webView = webView.settings
        webView.javaScriptEnabled = true
    }

    override fun onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            val intent = Intent(this,MainViewPageActivity::class.java)
            startActivity(intent)
        }
    }
}
