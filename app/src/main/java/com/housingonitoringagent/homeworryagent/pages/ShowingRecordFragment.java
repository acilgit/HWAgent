package com.housingonitoringagent.homeworryagent.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.activity.ShowingActivity;
import com.housingonitoringagent.homeworryagent.beans.ShowHouseBean;
import com.housingonitoringagent.homeworryagent.beans.ShowHouseBean.ContentBean.Content;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.DateUtil;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.BGARefreshLayoutBuilder;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2014/9/30.
 */

public class ShowingRecordFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;

    private XAdapter<Content> adapter;
    private boolean lastPage;
    private int pageIndex = 0;
    private int pageDefaultSize = 10;

//    private SaveState state = new SaveState();
//    class SaveState implements Serializable {
//    }

    private static final int REQUEST_CODE_GOT_RESULT = 100;

    public ShowingRecordFragment() {

    }

    private BaseActivity getThis() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.layout_refresh_list, container, false);
        ButterKnife.bind(this, currentView);
        if (savedInstanceState != null) {
//            state = (SaveState) savedInstanceState.getSerializable("state");
        }
        initViews();
        initDate();
        return currentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("state", state);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViews() {
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        BGARefreshLayoutBuilder.init(getActivity(), refreshView, true);
        refreshView.setDelegate(this);

        /*rvMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getThis())
                .colorResId(R.color.divider_line).sizeResId(R.dimen.line_1px)
                .marginResId(R.dimen.item_margin_icon, R.dimen.item_margin_icon).build()
        );*/

        adapter = new XAdapter<Content>(getThis(), new ArrayList<Content>(), R.layout.item_showing) {
            @Override
            public void creatingHolder(CustomHolder holder, List<Content> dataList, int viewType) {

            }

            @Override
            public void bindingHolder(CustomHolder holder, List<Content> dataList, int pos) {
                Content bean = dataList.get(pos);
                long appointmentTime = bean.getCreateTime();
                long startTime = bean.getStartTime();
                String unit = bean.getPermitType() == 0 ? "元" : "万";
//                String endTime = DateUtil.formatDateToString(bean.get());
                holder.setText(R.id.tvState, bean.getPermitStateString())
                        .setText(R.id.tvOrderType, bean.getPermitTypeString())
                        .setText(R.id.tvName, bean.getApplyUserName())
                        .setText(R.id.tvPhone, bean.getApplyUserMobilephone())
                        .setText(R.id.tvHouseName, bean.getVillageName())
                        .setText(R.id.tvHouseDetail, bean.getHouseShape())
                        .setText(R.id.tvHousePrice, bean.getPrice() + unit)
                        .setText(R.id.tvAppointmentTime, appointmentTime == 0 ? getThis().getString(R.string.wait_commit) : DateUtil.formatDateToString(appointmentTime))
                        .setText(R.id.tvStartTime, startTime == 0 ? getThis().getString(R.string.wait_commit) : DateUtil.formatDateToString(startTime));
                ((SimpleDraweeView) holder.getView(R.id.sivHead)).setImageURI(Uri.parse(bean.getAvatar()));
                ((SimpleDraweeView) holder.getView(R.id.sivHouse)).setImageURI(Uri.parse(bean.getHouseCoverPicture()));
            }

            @Override
            protected void handleItemViewClick(CustomHolder holder, final Content item) {
                super.handleItemViewClick(holder, item);
                if (item.getPermitType() == ShowHouseBean.PERMIT_STATUE_SHOWING || item.getPermitType() == ShowHouseBean.PERMIT_STATUE_WAIT) {
                    getThis().start(ShowingActivity.class, new BaseActivity.BaseIntent() {
                        @Override
                        public void setIntent(Intent intent) {
                            intent.putExtra(getThis().getString(R.string.extra_bean), item);
                        }
                    }, REQUEST_CODE_GOT_RESULT);
                }
            }
        };
        rvMain.setAdapter(adapter);

    }

    private void initDate() {
//        try {
//            NeighbourListBean bean = JSON.parseObject(User.getNeighbours().toString(), NeighbourListBean.class);
//            lastPage = bean.getNeighbourMessages().isLastPage();
//            villages = bean.getVillageNames();
//            neighborAdapter.setDataList(bean.getNeighbourMessages().getContent());
//        } catch (Exception e) {
//            e.printStackTrace();
        getDataByRefresh(pageIndex, pageDefaultSize, Const.RefreshType.REFRESH);
//        }
    }

    /**
     * 获取小区
     *
     * @param page        页码
     * @param refreshType 状态
     */
    private void getDataByRefresh(final int page, final int pageSize, final int refreshType) {
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.VISIT_PERMISSSION_LIST, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                    ShowHouseBean mainBean = JSON.parseObject(json.toString(), ShowHouseBean.class);
                    ShowHouseBean.ContentBean bean = mainBean.getContent();
                    lastPage = bean.isLastPage();
                    List<Content> list;
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
                            list = new ArrayList<>();
                            refreshView.endRefreshing();
//                            pageIndex = 0;
                            break;
                        default:
                            list = adapter.getDataList();
                            refreshView.endLoadingMore();
                            pageIndex++;
                            break;
                    }

                    if (bean.getContent().size() > 0) {
                        for (Content item : bean.getContent()) {
                            boolean add = false;
                            for (Content listItem : list) {
                                if (item.getId().equals(listItem.getId())) {
                                    add = true;
                                    break;
                                }
                            }
                            if (!add) {
                                list.add(item);
                            }
                        }
                        Collections.sort(bean.getContent(), new Comparator<Content>() {
                            public int compare(Content arg0, Content arg1) {
                                return arg0.getCreateTimeCompare().compareTo(arg1.getCreateTimeCompare());
                            }
                        });
                        final List<Content> newList = list;
                        getThis().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setDataList(newList);
//                        adapter.notifyDataSetChanged();
                            }
                        });
                    }
//                    adapter.setDataList(list);
                } else {
                    QBLToast.show(message);
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
                            refreshView.endRefreshing();
                            break;
                        case Const.RefreshType.LOAD:
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
                    case Const.RefreshType.REFRESH:
                        refreshView.endRefreshing();
                        break;
                    case Const.RefreshType.LOAD:
                        refreshView.endLoadingMore();
                        break;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("page", refreshType == Const.RefreshType.REFRESH ? "1" : String.valueOf(page + 1));
                params.put("permitType", "0");
                params.put("pageSize", String.valueOf(pageSize));
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        getDataByRefresh(++pageIndex, 10, villages.get(selectedVillageIndex), Const.RefreshType.REFRESH);
        if (adapter.getItemCount() > 0) {
            getDataByRefresh(pageIndex, adapter.getItemCount(), Const.RefreshType.REFRESH);
        } else {
            refreshView.endRefreshing();
            QBLToast.show(R.string.text_no_data);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!lastPage) {
            getDataByRefresh(pageIndex + 1, pageDefaultSize, Const.RefreshType.LOAD);
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GOT_RESULT:
                if (resultCode == getActivity().RESULT_OK) {
                    /*if (data != null) {
                        try {
                            Content bean = (Content) data.getSerializableExtra(getThis().getString(R.string.extra_bean));
                            for (int i = 0; i < adapter.getItemCount(); i++) {
                                if (adapter.getItem(i).getId().equals(bean.getId())) {
                                    adapter.updateItem(0, bean);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }*/

                }
                break;
            default:
                break;
        }
    }

}
