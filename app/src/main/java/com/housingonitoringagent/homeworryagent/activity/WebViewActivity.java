package com.housingonitoringagent.homeworryagent.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;

import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.views.AdvancedWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class WebViewActivity extends BaseActivity implements AdvancedWebView.Listener{
    @Bind(R.id.web)
    AdvancedWebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initContentView();
        String url = getIntent().getStringExtra(getString(R.string.extra_url));
        String title = getIntent().getStringExtra(getString(R.string.extra_title));
        if (url.isEmpty()) {
            finish();
            return;
        }
        webView.loadUrl(url);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initContentView() {

        WebSettings webSettings = webView.getSettings();
        //设置加载进来的页面自适应手机屏幕
        webSettings.setTextZoom(100);
        webView.setListener(this, this);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);

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

   /* private class MyWebViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/
}
