package com.housingonitoringagent.homeworryagent.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.LogUtils;
import com.housingonitoringagent.homeworryagent.views.AdvancedWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class WebViewActivity extends BaseActivity implements AdvancedWebView.Listener {
    @Bind(R.id.web)
    WebView webView;
//    AdvancedWebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initContentView();
        url = getIntent().getStringExtra(getString(R.string.extra_url));
        String title = getIntent().getStringExtra(getString(R.string.extra_title));
        if (url.isEmpty()) {
            finish();
            return;
        } else {
            if (title == null || title.isEmpty()) {
                initContentView();
            } else {
                initAgreementContentView();
            }
        }
    }

    private void initAgreementContentView() {
        WebSettings webSettings = webView.getSettings();
        //设置加载进来的页面自适应手机屏幕
        webSettings.setTextZoom(100);
//        webView.setListener(this, this);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);

        toolbar.setTitle(getIntent().getStringExtra(getString(R.string.extra_title)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView.loadUrl(url);
    }

    private void initContentView() {
        toolbar.setVisibility(View.GONE);

        WebSettings webSettings = webView.getSettings();
        //设置加载进来的页面自适应手机屏幕
        webSettings.setTextZoom(100);
//        webView.setListener(this, this);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtils.e("onJsPrompt msg:" + message + " defVal:"+ defaultValue);
                if (defaultValue.equals("Advanced")) {
                    if (message.equals("back")) {
                        if (webView.canGoBack()) {
                            webView.goBack();
                        } else {
                            finish();
                        }
                    } else {
                        LogUtils.e("onJsPrompt message  " + message);
                    }
                    result.confirm("ok1");
                    return true;
                }else
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "interactive");

        webView.loadUrl(url);
                webView.loadUrl("javascript:\n" + "prompt(\"backJs \" + BackJS, \"Advanced\");" + "");
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    private class MyWebViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (url != null) {
//            syncCookie(url);
                String script = "\n function backJava(a){\n" +
//                "   var param1 = JSON.stringify({id:a, name:\"Advanced\"}); " +
//                "   prompt(param1, \"Advanced\"); " +
                        "   prompt(\"back\", \"Advanced\"); " +
                        "   return true; " +
                        "}\n"
                        +  "alter(BackJS); backJS = backJava;"
                        ;
//                webView.loadUrl("javascript:\n" + script + "");
                LogUtils.e(" 5  script: " + script);
                LogUtils.e("  onPageFinished ");
            }
        }
    }


    /* @Override
     public void onResume() {
         super.onResume();
         CookieSyncManager.getInstance().startSync();
     }

     @Override
     public void onPause() {
         super.onPause();
         CookieSyncManager.getInstance().stopSync();
     }

   protected void syncCookie(String url) {
       CookieManager cookieManager = CookieManager.getInstance();
       // 设置Cookie同步登录到Web端
       cookieManager.setAcceptCookie(true);
       cookieManager.setCookie(url, VolleyManager.getCookies());
       CookieSyncManager.getInstance().sync();
   }*/
    private class InJavaScriptLocalObj {
        @JavascriptInterface
        public void back() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                }
            });
        }

    }
}
