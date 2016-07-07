package com.housingonitoringagent.homeworryagent.activity;

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
import com.housingonitoringagent.homeworryagent.adapter.HousingAssessmentAdapter;
import com.housingonitoringagent.homeworryagent.beans.HousingAssessmentBean;
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
 * Created by Administrator on 2016/4/27 0027.
 */
public class HousingAssessmentActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.refreshView)
    BGARefreshLayout mRefresh;
    @Bind(R.id.rvMain)
    RecyclerView ListView;

    private int mNextPage = 1;
    private boolean mMaxPage;
    private String id;
    private HousingAssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing_assessment);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra(getString(R.string.extra_id));
        mToolbar.setTitle(R.string.label_house_evaluate);
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initview();
        mRefresh.beginRefreshing();
    }

    private void initview() {

        ListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        ListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new HousingAssessmentAdapter();
        ListView.setAdapter(adapter);



        BGARefreshLayoutBuilder.init(this, mRefresh, true);
        mRefresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mNextPage = 1;
                getAssessmentList(true);
            }
            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (!mMaxPage) {
                    getAssessmentList(false);
                    return true;
                }
                return false;
            }
        });
    }

    private void getAssessmentList(final boolean refresh) {
        showProgressDialog(getString(R.string.wait_a_few_times));
        final long page = refresh ? 1 : mNextPage;
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.HOUSEEVALUATELIST,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        dismissProgressDialog();
                        if (refresh) {
                            mRefresh.endRefreshing();
                        } else {
                            mRefresh.endLoadingMore();
                        }
                        int result = json.getIntValue("resultCode");
                        String  msg = json.getString("message");
                        switch (result){
                            case 1:
                                HousingAssessmentBean bean = JSON.parseObject(json.toJSONString(), HousingAssessmentBean.class);
                                mNextPage++;
                                mMaxPage = bean.getHouseEvaluateList().isLastPage();
                                if (refresh) {
                                    adapter.setAdapterData(bean.getHouseEvaluateList().getContent());
                                } else {
                                    adapter.addAdapterData(bean.getHouseEvaluateList().getContent());
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
                dismissProgressDialog();
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
