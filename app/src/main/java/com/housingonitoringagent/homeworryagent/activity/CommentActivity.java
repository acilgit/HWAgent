package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.housingonitoringagent.homeworryagent.adapter.CommentAdapter;
import com.housingonitoringagent.homeworryagent.beans.CommentListBean;
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
 * Created by Administrator on 2016/4/14 0014.
 */
public class CommentActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.refresh)
    BGARefreshLayout mRefresh;
    @Bind(R.id.list)
    RecyclerView mCommentList;
    @Bind(R.id.send)
    TextView mSend;
    @Bind(R.id.edit)
    EditText mEditComment;

    private String mArticleId;
    private int mNextPage = 1;
    private boolean mMaxPage;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_comment);
        Intent intent = getIntent();
        if (intent != null) {
            mArticleId = intent.getStringExtra(getString(R.string.extra_id));
        }
        ButterKnife.bind(this);
        mToolBar.setTitle(R.string.estate_comment);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        setListener();
        mRefresh.beginRefreshing();
    }

    private void setListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.this.onClick(v);
            }
        };

        mSend.setOnClickListener(onClickListener);
        mRefresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mNextPage = 1;
                getCommentList(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (!mMaxPage) {
                    getCommentList(false);
                    return true;
                }
                return false;
            }
        });
        adapter = new CommentAdapter();
        mCommentList.setAdapter(adapter);
    }

    private void getCommentList(final boolean refresh) {
        final long page = refresh ? 1 : mNextPage;
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.EVALUATIONLIST,
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
                                CommentListBean bean = JSON.parseObject(json.toJSONString(), CommentListBean.class);
                                mNextPage++;
                                mMaxPage = bean.getContent().getEvaluationList().isLastPage();
                                if (refresh) {
                                    adapter.setAdapterData(bean.getContent().getEvaluationList().getContent());
                                } else {
                                    adapter.addAdapterData(bean.getContent().getEvaluationList().getContent());
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
                params.put("id", mArticleId);
                params.put("page", String.valueOf(page));
                return params;
            }
        };
        getVolleyRequestQueue().add(request);

    }

    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
//                if (!User.tryLogin(this)) {
//                    break;
//                }
                String comment = mEditComment.getText().toString();
                if (TextUtils.isEmpty(comment.trim()) || comment.getBytes().length > 255) {
                    QBLToast.show(R.string.article_comment_format_hint);
                } else {
                    postComment(comment);
                    mSend.setEnabled(false);
                }
                break;
        }
    }

    private void postComment(final String comment) {
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.ADDEVALUATION, new VolleyResponseListener() {
            @Override
            public void handleJson(JSONObject json) {
                super.handleJson(json);
                int result = json.getIntValue("resultCode");
                switch (result) {
                    case 1:
                        onPostCommentComplete();
                        break;
                    default:
                        mSend.setEnabled(true);
                        QBLToast.show(R.string.network_exception);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSend.setEnabled(true);
                QBLToast.show(R.string.network_exception);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("id", mArticleId);
                params.put("content", comment);
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

    private void initView() {
        BGARefreshLayoutBuilder.init(this, mRefresh, true);
        mCommentList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mCommentList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mSend.setEnabled(mEditComment.length() > 0);
        mEditComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSend.setEnabled(s.length() > 0);
            }
        });
    }

    private void onPostCommentComplete() {
        mEditComment.setText(null);
        // 隐藏输入法
        hideSoftInput();
        // 滚至列表顶部
        mCommentList.scrollToPosition(0);
        getCommentList(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
