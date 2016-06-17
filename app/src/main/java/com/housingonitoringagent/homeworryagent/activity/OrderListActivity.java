package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean.ContentBean.PsBean.Content;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.LogUtils;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
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
 * HomeWorry
 * Created by Administrator on 2016/3/2 0002.
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;

    //    private XAdapter<NeighbourListBean.NeighbourMessagesBean.ContentBean> neighborAdapter;
    private XAdapter<Content> adapter;
//    private XAdapter neighborAdapter;


    private boolean lastPage;
    private int pageIndex = 0;
    private int pageDefaultSize = 10;

    private static final int REQUEST_CODE_GOT_RESULT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_order_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_neighbour, menu);
        return true;
    }


    private void initViews() {
        rvMain.setLayoutManager(new LinearLayoutManager(getThis()));
        BGARefreshLayoutBuilder.init(getThis(), refreshView, true);
        refreshView.setDelegate(this);

        /*rvMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getThis())
                .colorResId(R.color.divider_line).sizeResId(R.dimen.line_1px)
                .marginResId(R.dimen.item_margin_icon, R.dimen.item_margin_icon).build()
        );*/

        adapter = new XAdapter<Content>(getThis(), new ArrayList<Content>(), R.layout.item_order) {
            @Override
            public void creatingHolder(final CustomHolder holder, final List<Content> dataList, int viewType) {

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btnDeposit:
                                getThis().start(ConfirmGiroActivity.class, new BaseActivity.BaseIntent() {
                                    @Override
                                    public void setIntent(Intent intent) {
                                        intent.putExtra(getString(R.string.extra_bean), dataList.get(holder.getAdapterPosition()));
                                    }
                                });
                                break;
                            case R.id.btnDetail:
                                /*getThis().start(ConfirmGiroActivity.class, new BaseActivity.BaseIntent() {
                                    @Override
                                    public void setIntent(Intent intent) {
                                        intent.putExtra(getString(R.string.extra_bean), dataList.get(holder.getAdapterPosition()));
                                    }
                                });*/
                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.btnDeposit).setOnClickListener(clickListener);
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<Content> dataList, int pos) {
                Content bean = dataList.get(pos);
                holder.setText(R.id.tvOrderNo, bean.getOrderNo())
                        .setText(R.id.tvState, getResources().getStringArray(R.array.admin_statue)[bean.getOrderStatus()])
                        .setText(R.id.tvPartyA, bean.getAUserName())
                        .setText(R.id.tvPartyB, bean.getBUserName())
                        .setText(R.id.tvPartyAgent, bean.getCUserName())
                        .setText(R.id.tvAgentPhone, bean.getCUserMobilephone())
                        .setText(R.id.tvBank, bean.getBankName())
                        .setText(R.id.tvPartyBBankAccount, bean.getBankNumber())
                        .setText(R.id.tvAmountSupervision, StringUtil.formatNumber(bean.getOrderMoney()));
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
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.HOUSE_DEAL_LIST, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                    LogUtils.le(json.toString());
                    HouseDealBean mainBean = JSON.parseObject(json.toString(), HouseDealBean.class);
                    HouseDealBean.ContentBean.PsBean bean = mainBean.getContent().getPs();
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
//                        getThis().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                                adapter.setDataList(newList);
//                        adapter.notifyDataSetChanged();
//                            }
//                        });
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
            getDataByRefresh(1, pageDefaultSize, Const.RefreshType.REFRESH);
            refreshView.endRefreshing();
            QBLToast.show(R.string.text_no_data);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!lastPage) {
            getDataByRefresh(pageIndex + 1, pageDefaultSize, Const.RefreshType.LOAD);
        }
      /*  if (mVillages.size() > 0) {
            if (!lastPage) {
                getDataByRefresh(pageIndex, neighborAdapter.getData().get(mBuildSelectIndex).getId(), villageName, Const.RefreshType.LOAD);
            }
        } else {
            QBLToast.show(R.string.text_no_data);
        }
    else

    {
        refreshView.endRefreshing();
        QBLToast.show(R.string.text_no_village_tip);
    }*/

        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GOT_RESULT:
                if (resultCode == getThis().RESULT_OK) {
                    getDataByRefresh(pageIndex, adapter.getItemCount(), Const.RefreshType.REFRESH);
                 /*   if (data != null) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_post:
//                Intent intent = new Intent(this, HomeNeighbourCommitActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_NEW_COMMENT);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ivPost:
//                Intent intent = new Intent(this, HomeNeighbourCommitActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_NEW_COMMENT);
//                getNeighbors(0, 10, getSelectedVillage(selectedVillageIndex), Const.RefreshType.REFRESH);
//                break;
            default:
                break;
        }
    }


}
