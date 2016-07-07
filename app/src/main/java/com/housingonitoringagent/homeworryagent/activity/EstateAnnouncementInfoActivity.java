package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.AnnouncementInfoBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.DateUtil;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class EstateAnnouncementInfoActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.content)
    TextView mContent;
    @Bind(R.id.time)
    TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate_announcement_info);
        ButterKnife.bind(this);
        mToolBar.setTitle(R.string.info_announcement);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initview();
    }

    private void initview() {
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra(getString(R.string.extra_id));
            getAnnouncementInfo(id);
        }
    }

    private void getAnnouncementInfo(final String id) {
        showProgressDialog(getString(R.string.loading));
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.ANNOUNCEMENTINFO,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        dismissProgressDialog();
                        int result = json.getIntValue("resultCode");
                        String msg = json.getString("message");
                        switch (result) {
                            case 1:
                                AnnouncementInfoBean bean = JSON.parseObject(json.toJSONString(), AnnouncementInfoBean.class);
                                mTitle.setText(bean.getContent().getAnnouncement().getTitle());
                                mTime.setText(DateUtil.parseTimeMillis(bean.getContent().getAnnouncement().getPublisherTime()));
                                mContent.setText(Html.fromHtml(bean.getContent().getAnnouncement().getContent()));
                                break;
                            case 0:
                                QBLToast.show(msg);
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                QBLToast.show(R.string.network_exception);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("id", id);
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
