package com.housingonitoringagent.homeworryagent.utils.uikit;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyParams;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by XY on 2016/6/17.
 */
public class RefreshListUtil<T> implements BGARefreshLayout.BGARefreshLayoutDelegate {

//    private static List<RefreshListUtil> refreshListUtils = new ArrayList<>();

    private final BGARefreshLayout refreshView;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    private String url;

    private BaseActivity activity;
    private RefreshState state;
    private XAdapter<T> adapter;

    private IRefreshRequest iRefreshRequest;

    private RefreshListUtil(BaseActivity activity, BGARefreshLayout refreshView, boolean loadMore) {
        BGARefreshLayoutBuilder.init(activity, refreshView, loadMore);
        refreshView.setDelegate(this);
        this.activity = activity;
        this.refreshView = refreshView;
    }

    public RefreshListUtil(BaseActivity activity, BGARefreshLayout refreshView, boolean loadMore, XAdapter<T> adapter, @NonNull IRefreshRequest iRefreshRequest ) {
        BGARefreshLayoutBuilder.init(activity, refreshView, loadMore);
        refreshView.setDelegate(this);
        this.activity = activity;
        this.refreshView = refreshView;
        this.adapter=adapter;
        this.iRefreshRequest = iRefreshRequest;
        this.state = new RefreshState(10);
    }

    public RefreshListUtil(BaseActivity activity, BGARefreshLayout refreshView, boolean loadMore, XAdapter<T> adapter, @NonNull IRefreshRequest iRefreshRequest, int refreshPageSize) {
        BGARefreshLayoutBuilder.init(activity, refreshView, loadMore);
        refreshView.setDelegate(this);
        this.activity = activity;
        this.refreshView = refreshView;
        this.adapter=adapter;
        this.iRefreshRequest = iRefreshRequest;
        this.state = new RefreshState(refreshPageSize);
    }

    private static RefreshListUtil getBGARefresher(RefreshListUtil refreshListUtil, IRefreshRequest iRefreshRequest) {
        getBGARefresher(refreshListUtil, iRefreshRequest, 10);
        return refreshListUtil;
    }

    private static RefreshListUtil getBGARefresher(RefreshListUtil refreshListUtil, IRefreshRequest iRefreshRequest, int refreshPageSize) {
//        if (!refreshListUtils.contains(refreshListUtil)) {
//            refreshListUtils.add(refreshListUtil);
            refreshListUtil.iRefreshRequest = iRefreshRequest;
            refreshListUtil.state = new RefreshState(refreshPageSize);
            return refreshListUtil;
//        }
//        return null;
    }

    /**
     * 获取小区
     *
     * @param pageSize 页面大小
     */
    private void getDataByRefresh(int pageSize) {
        getDataByRefresh(1, pageSize, REFRESH);
    }

    private void getDataByRefresh(int page, int pageSize) {
        getDataByRefresh(page, pageSize, LOAD);
    }

    private void getDataByRefresh(final int page, final int pageSize, final int refreshType) {
        final Map<String, String> addParams = setParamsForUrl();
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, url, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
//                    LogUtils.le(json.toString());
//                    T mainBean = JSON.parseObject(json.toString(), T);
//                    bean = mainBean.getContent().getPs();
                    refreshView.endLoadingMore();
                    final List<T> newList = iRefreshRequest.handleJson(json, state.lastPage);
                    List<T> list = adapter.getDataList();
                    switch (refreshType) {
                        case REFRESH:
                            list.clear();
                            break;
                        default:
                            state.pageIndex++;
                            break;
                    }

                    if (newList.size() > 0) {
                        for (T newItem : newList) {
                            boolean hasSameItem = false;
                            for (T listItem : list) {
                                if (iRefreshRequest.ignoreSameItem(newItem, listItem)) {
                                    hasSameItem = true;
                                    break;
                                }
                            }
                            if (!hasSameItem) list.add(newItem);

                        }
                        Collections.sort(list, new Comparator<T>() {
                            public int compare(T arg0, T arg1) {
                                return iRefreshRequest.compareTo(arg0, arg1);
//                                return arg0.getCreateTimeCompare().compareTo(arg1.getCreateTimeCompare());
                            }
                        });
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setDataList(newList);
                                if (state.lastPage) {
                                    QBLToast.show(R.string.text_no_data);
                                }
                            }
                        });
                    }
                } else {
                    QBLToast.show(message);
                    switch (refreshType) {
                        case REFRESH:
                            refreshView.endRefreshing();
                            break;
                        case LOAD:
                            refreshView.endLoadingMore();
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                QBLToast.show(R.string.network_exception);
                switch (refreshType) {
                    case REFRESH:
                        refreshView.endRefreshing();
                        break;
                    case LOAD:
                        refreshView.endLoadingMore();
                        break;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("page", refreshType == REFRESH ? "1" : String.valueOf(page));
                params.put("pageSize", String.valueOf(pageSize));
                params.putAll(addParams);
                return params;
            }
        };
        activity.getVolleyRequestQueue().add(request);
    }

    public void refreshList() {
        if (adapter.getItemCount() > 0) {
            getDataByRefresh(adapter.getItemCount());
        } else {
            getDataByRefresh(state.pageDefaultSize);
            refreshView.endRefreshing();
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refreshList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!state.lastPage) {
            getDataByRefresh(state.pageIndex + 1, state.pageDefaultSize);
            return true;
        }
        return false;
    }

    protected Map<String, String> setParamsForUrl() {
        Map<String, String> params = new VolleyParams();
        this.url =  iRefreshRequest.setVolleyParamsReturnUrl(params);
        return params;
    }

    public interface IRefreshRequest<T> {
        String setVolleyParamsReturnUrl(Map<String, String> params);
        List<T> handleJson(JSONObject json, boolean setIsLastPage);
        boolean ignoreSameItem(T newItem, T listItem);
        int compareTo(T item0, T item1);
    }


    static class RefreshState implements Serializable {
        boolean lastPage = false;
        int pageIndex = 0;
        int pageDefaultSize = 10;

        public RefreshState(int pageDefaultSize) {
            this.pageDefaultSize = pageDefaultSize;
        }
    }

}
