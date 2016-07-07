package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.adapter.EstateAnnouncementAdapter;
import com.housingonitoringagent.homeworryagent.beans.AnnouncementListBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.BGARefreshLayoutBuilder;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.housingonitoringagent.homeworryagent.utils.uikit.recyclerview.HorizontalDividerItemDecoration;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class EstateAnnouncementListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.rvMain)
    RecyclerView mQuestionView;
    @Bind(R.id.refreshView)
    BGARefreshLayout mRefresh;


    //下一页数
    int mNextPage = 0;
    private boolean mMaxPage;
    private String id;
    EstateAnnouncementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate_announcement_list);
        ButterKnife.bind(this);
        mToolBar.setTitle(R.string.quarters_announcement);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initview();
    }

    private void initview() {
        mQuestionView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mQuestionView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        BGARefreshLayoutBuilder.init(this, mRefresh, true);
        mRefresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mNextPage = 1;
                getAnnouncementList(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (!mMaxPage) {
                    getAnnouncementList(false);
                    return true;
                }
                return false;
            }
        });
        adapter = new EstateAnnouncementAdapter(this);
        mQuestionView.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(getString(R.string.extra_id));
            getAnnouncementList(true);
        }

    }

    private void getAnnouncementList(final boolean refresh) {
        final long page = refresh ? 1 : mNextPage;
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.ANNOUNCEMENTLIST,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        if (refresh) {
                            mRefresh.endRefreshing();
                        } else {
                            mRefresh.endLoadingMore();
                        }

                        int result = json.getIntValue("resultCode");
                        String msg = json.getString("message");
                        switch (result) {
                            case 1:
                                AnnouncementListBean bean = JSON.parseObject(json.toJSONString(), AnnouncementListBean.class);
                                mNextPage++;
                                mMaxPage = bean.getContent().getAnnouncementList().isLastPage();
                                if (refresh) {
                                    adapter.setAdapterData(bean.getContent().getAnnouncementList().getContent());
                                } else {
                                    adapter.addAdapterData(bean.getContent().getAnnouncementList().getContent());
                                }
                                break;
                            case 0:
                                QBLToast.show(msg);
                                break;
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (refresh) {
                    mRefresh.endRefreshing();
                } else {
                    mRefresh.endLoadingMore();
                }
                QBLToast.show(R.string.network_exception);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("id", id);
                params.put("page", String.valueOf(page));
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
