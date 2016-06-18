package com.housingonitoringagent.homeworryagent.utils;

/**
 * Created by XY on 2016/6/18.
 */

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
import com.housingonitoringagent.homeworryagent.utils.uikit.BGARefreshLayoutBuilder;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
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

    public RefreshListUtil(BaseActivity activity, BGARefreshLayout refreshView, boolean loadMore, XAdapter<T> adapter, @NonNull IRefreshRequest iRefreshRequest) {
        BGARefreshLayoutBuilder.init(activity, refreshView, loadMore);
        refreshView.setDelegate(this);
        this.activity = activity;
        this.refreshView = refreshView;
        this.adapter = adapter;
        this.iRefreshRequest = iRefreshRequest;
        this.state = new RefreshState(10);
    }

    public RefreshListUtil(BaseActivity activity, BGARefreshLayout refreshView, boolean loadMore, XAdapter<T> adapter, @NonNull IRefreshRequest iRefreshRequest, int refreshPageSize) {
        BGARefreshLayoutBuilder.init(activity, refreshView, loadMore);
        refreshView.setDelegate(this);
        this.activity = activity;
        this.refreshView = refreshView;
        this.adapter = adapter;
        this.iRefreshRequest = iRefreshRequest;
        this.state = new RefreshState(refreshPageSize);
    }

   /* private static RefreshListUtil getBGARefresher(RefreshListUtil refreshListUtil, IRefreshRequest iRefreshRequest) {
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
    }*/

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
                activity.dismissProgressDialog();
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                    List<T> newList = iRefreshRequest.handleJson(json, state);
                    final List<T> list = adapter.getDataList();
                    switch (refreshType) {
                        case REFRESH:
                            refreshView.endRefreshing();
                            if (state.pageIndex == 0) state.pageIndex++;
                            list.clear();
                            break;
                        default:
                            refreshView.endLoadingMore();
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
                            }
                        });
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setDataList(list);
                            }
                        });
                    }
                } else {
                    activity.dismissProgressDialog();
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
                activity.dismissProgressDialog();
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

    /**
     * 刷新列表
     */
    public void refreshList() {
        if (adapter.getItemCount() > 0) {
            getDataByRefresh(adapter.getItemCount());
        } else {
            activity.showProgressDialog(activity.getString(R.string.wait_a_few_times));
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
        QBLToast.show(R.string.text_no_more_data);
        return false;
    }

    protected Map<String, String> setParamsForUrl() {
        Map<String, String> params = new VolleyParams();
        this.url = iRefreshRequest.setVolleyParamsReturnUrl(params);
        return params;
    }

    public interface IRefreshRequest<T> {
        /**
         * 把对应要返回的url要设置的params设置好，Return url;
         * @param params
         * @return
         */
        String setVolleyParamsReturnUrl(Map<String, String> params);

        /**
         * 处理Json 把JSON中的lastPage通过state.setLastPage() 传进去，
         * 最后把JSON中的List return
         * @param json
         * @param stateForSetLastPage
         * @return
         */
        List<T> handleJson(JSONObject json, RefreshState stateForSetLastPage);

        /**
         * 去重，可以通过 return newItem.getId().equals(listItem.getId()); 返回结果，
         * 如果不去重就直接返回false;
         * @param newItem
         * @param listItem
         * @return
         */
        boolean ignoreSameItem(T newItem, T listItem);

        /**
         * 排列表中的数据，返回值：
         * -1 从大到小
         *  1 从小到大
         *  0 相同
         *  也可以通过： 如 long 的实例   Long.compareTo()来比较，item0:item1的话，默认从小到大
         * @param item0
         * @param item1
         * @return
         */
        int compareTo(T item0, T item1);
    }


    public static class RefreshState implements Serializable {
        boolean lastPage = false;
        int pageIndex = 0;
        int pageDefaultSize = 10;

        public RefreshState(int pageDefaultSize) {
            this.pageDefaultSize = pageDefaultSize;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }
    }
}
