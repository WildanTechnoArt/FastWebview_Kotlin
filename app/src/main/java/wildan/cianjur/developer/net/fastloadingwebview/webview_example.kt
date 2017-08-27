package wildan.cianjur.developer.net.fastloadingwebview

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import kotlinx.android.synthetic.main.activity_webview_example.*

@Suppress("DEPRECATION", "OverridingDeprecatedMember")
class webview_example : AppCompatActivity() {

    private val website = "http://www.wildantechnoart.net/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_example)
        loading.max = 100
        settings()
        load_website()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settings(){
        val my_webSettings = websiteku.settings
        my_webSettings.javaScriptEnabled = true
        my_webSettings.loadsImagesAutomatically = true
        my_webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        my_webSettings.allowContentAccess = true
        my_webSettings.domStorageEnabled = true
        my_webSettings.useWideViewPort = true
        my_webSettings.setEnableSmoothTransition(true)
        my_webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
    }

    private fun load_website(){
        if(Build.VERSION.SDK_INT >= 19){
            websiteku.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }else{
            websiteku.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        websiteku.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                loading.visibility = View.VISIBLE
                loading.progress = newProgress
                if(newProgress == 100){
                    loading.visibility = View.GONE
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        websiteku.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, URL: String?): Boolean {
                view?.loadUrl(URL)
                loading.visibility = View.VISIBLE
                return true
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                loading.visibility = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loading.visibility = View.GONE
            }
        }

        websiteku.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        websiteku.loadUrl(website)
    }

    override fun onBackPressed() {
        if(websiteku.canGoBack()){
            websiteku.goBack()
        }else{
            super.onBackPressed()
        }
    }
}