package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
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
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
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
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtils.e("onJsPrompt msg:" + message + " defVal:" + defaultValue);
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
                } else
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
                        + "alter(BackJS); backJS = backJava;";
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


        //经理人
//        @JavascriptInterface
//        public void manager(final String id) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (null != id && !id.isEmpty()) {
//                        start(ManagerActivity.this, new BaseIntent() {
//                            @Override
//                            public void setIntent(Intent intent) {
//                                intent.putExtra(getString(R.string.extra_id), id);
//                            }
//                        });
//                    }
//                }
//            });
//        }
//
//        //投票
//        @JavascriptInterface
//        public void vote(final String id) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (null != id && !id.isEmpty()) {
//                        start(QuartersVoteActivity.this, new BaseIntent() {
//                            @Override
//                            public void setIntent(Intent intent) {
//                                intent.putExtra(getString(R.string.extra_id), id);
//                            }
//                        });
//                    }
//                }
//            });
//
//        }

        //公告
        @JavascriptInterface
        public void notice(final String id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != id && !id.isEmpty()) {
                        start(EstateAnnouncementListActivity.class, new BaseIntent() {
                            @Override
                            public void setIntent(Intent intent) {
                                intent.putExtra(getString(R.string.extra_id), id);
                            }
                        });
                    }
                }
            });
        }


        //打电话
     /*   @JavascriptInterface
        public void call(final String number) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != number && !number.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
                        startActivity(intent);
                    }
                }
            });
        }*/

        //问题
//        @JavascriptInterface
//        public void fault(final String id) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (null != id && !id.isEmpty()) {
//                        start(HouseQuestionActivity.this, new BaseIntent() {
//                            @Override
//                            public void setIntent(Intent intent) {
//                                intent.putExtra(getString(R.string.extra_id), id);
//                            }
//                        });
//                    }
//                }
//            });
//        }
//
//        //投诉
//        @JavascriptInterface
//        public void complaint(final String id) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (null != id && !id.isEmpty()) {
//                        start(QuartersComplaintListActivity.this, new BaseIntent() {
//                            @Override
//                            public void setIntent(Intent intent) {
//                                intent.putExtra(getString(R.string.extra_id), id);
//                            }
//                        });
//                    }
//                }
//            });
//        }

        /**
         未用，通过公告列表调用
         *
         */
        @JavascriptInterface
        public void announcement(final String id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != id && id != "") {
                        start(EstateAnnouncementInfoActivity.class, new BaseIntent() {
                            @Override
                            public void setIntent(Intent intent) {
                                intent.putExtra(getString(R.string.extra_id), id);
                            }
                        });
                    }
                }
            });
        }

        /**
         * 小区评价
         * @param id
         */
        @JavascriptInterface
        public void evaluate(final String id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != id && !id.isEmpty()) {
                        start(CommentActivity.class, new BaseIntent() {
                            @Override
                            public void setIntent(Intent intent) {
                                intent.putExtra(getString(R.string.extra_id), id);
                            }
                        });
                    }
                }
            });
        }

        /**
         * 小区周边
         * @param longitude
         * @param latitude
         */
        @JavascriptInterface
        public void peripheral(final String longitude, final String latitude) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != longitude && !longitude.isEmpty() && null != latitude && !latitude.isEmpty()) {
                        start(SurroundingFacilitiesActivity.class, new BaseIntent() {
                            @Override
                            public void setIntent(Intent intent) {
                                intent.putExtra(getString(R.string.extra_longitude), longitude);
                                intent.putExtra(getString(R.string.extra_latitude), latitude);
                            }
                        });
                    } else {
                        QBLToast.show(getString(R.string.text_no_surrounding_facilityies));
                    }
                }
            });
        }

        /**
         *
         * @param id
         */
        @JavascriptInterface
        public void assessment(final String id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != id && !id.isEmpty()) {
                        start(HousingAssessmentActivity.class, new BaseIntent() {
                            @Override
                            public void setIntent(Intent intent) {
                                intent.putExtra(getString(R.string.extra_id), id);
                            }
                        });
                    }
                }
            });
        }

       /*  @JavascriptInterface
       public void houseRecord(final String houseId, final String agentId, final String permitType) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (User.isLogin()) {
                        if (null != houseId && !houseId .isEmpty() && null != agentId && !agentId.isEmpty() && null != permitType && !permitType.isEmpty()) {
                            StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.APPLY_VISIT_PERMIT_ADD,
                                    new VolleyResponseListener() {
                                        @Override
                                        public void handleJson(JSONObject json) {
                                            super.handleJson(json);
                                            String msg = json.getString("message");
                                            int result = json.getIntValue("resultCode");
                                            switch (result) {
                                                case 1:
                                                    QBLToast.show(msg);
                                                    showRecordDialog();
                                                    break;
                                                default:
                                                    showRecordDialog();
                                                    QBLToast.show(msg);
                                                    break;
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    QBLToast.show(R.string.network_exception);
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = super.getParams();
                                    params.put("permitType", permitType);
                                    params.put("houseId", houseId);
                                    params.put("agentId", agentId);
                                    return params;
                                }
                            };
                            getVolleyRequestQueue().add(request);
                        }
                    }
                }
            });
        }*/


      /*  @JavascriptInterface
        public void chat(final String hxId,final String nickname) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (User.isLogin()) {
                        if (null != hxId && !hxId .isEmpty() && null != nickname && !nickname .isEmpty() ) {
                            Intent intent = new Intent(HouseDetailsActivity.this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_ID, hxId);
                            intent.putExtra("nickname",nickname);
                            startActivity(intent);
                        }
                    }
                }
            });
        }*/


    }
}
