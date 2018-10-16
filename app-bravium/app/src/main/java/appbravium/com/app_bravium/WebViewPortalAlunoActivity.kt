package appbravium.com.app_bravium

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewPortalAlunoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_portal_aluno)

        iniciarWebView()
    }

    private fun iniciarWebView() {
        var url = intent.data

        val myWebView: WebView = findViewById(R.id.webViewPortalAluno)
        myWebView.loadUrl(url.toString())
        myWebView.webViewClient = CallBack()
    }

    class CallBack : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false;
        }
    }
}
