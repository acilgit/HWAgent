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
import com.alibaba.fastjson.JSONObject;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean.ContentBean.PsBean.Content;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
import com.housingonitoringagent.homeworryagent.utils.uikit.RefreshListUtil;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * HomeWorry
 * Created by Administrator on 2016/3/2 0002.
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener/*, BGARefreshLayout.BGARefreshLayoutDelegate*/ {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;

    private XAdapter<Content> adapter;

    private RefreshListUtil<Content> refresher;
    //    private int pageDefaultSize = 10;
//    private boolean lastPage;
//    private int pageIndex = 0;

//    private SaveState state = new SaveState();
//    class SaveState implements Serializable {
//    }

    private static final int REQUEST_CODE_GOT_RESULT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
//            state = (SaveState) savedInstanceState.getSerializable("state");
        }
        toolbar.setTitle(R.string.title_order_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initDate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("state", state);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_neighbour, menu);
        return true;
    }

    private void initViews() {
        rvMain.setLayoutManager(new LinearLayoutManager(getThis()));

//        BGARefreshLayoutBuilder.init(getThis(), refreshView, true);
//        refreshView.setDelegate(this);

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
        refresher = new RefreshListUtil<>(getThis(), refreshView, true, adapter, new RefreshListUtil.IRefreshRequest<Content>() {
            @Override
            public String setVolleyParamsReturnUrl(Map<String, String> params) {
                params.put("permitType", "0");
                return Const.serviceMethod.HOUSE_DEAL_LIST;
            }

            @Override
            public List<Content> handleJson(JSONObject json, boolean setIsLastPage) {
                HouseDealBean mainBean = JSON.parseObject(json.toString(), HouseDealBean.class);
                HouseDealBean.ContentBean.PsBean bean = mainBean.getContent().getPs();
                setIsLastPage = bean.isLastPage();
                return bean.getContent();
            }

            @Override
            public boolean ignoreSameItem(Content newItem, Content listItem) {
                return newItem.getId().equals(listItem.getId());
            }

            @Override
            public int compareTo(Content item0, Content item1) {
                return item0.getCreateTimeCompare().compareTo(item1.getCreateTimeCompare());
            }
        });
    }

    private void initDate() {
        refresher.refreshList();
    }


    /**
     * 获取小区
     *
     */
  /*  private void getDataByRefresh(int pageSize) {
        getDataByRefresh(1, pageSize, Const.RefreshType.REFRESH);
    }

    private void getDataByRefresh(int page, int pageSize) {
        getDataByRefresh(page, pageSize, Const.RefreshType.LOAD);
    }

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
                    refreshView.endLoadingMore();
                    state.lastPage = bean.isLastPage();
                    List<Content> list;
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
                            list = new ArrayList<>();
                            break;
                        default:
                            list = adapter.getDataList();
                            state.pageIndex++;
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
                            if (!add) list.add(item);

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
                            }
                        });
                    }
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
                params.put("page", refreshType == Const.RefreshType.REFRESH ? "1" : String.valueOf(page));
                params.put("permitType", "0");
                params.put("pageSize", String.valueOf(pageSize));
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (adapter.getItemCount() > 0) {
            getDataByRefresh(adapter.getItemCount());
        } else {
            getDataByRefresh(state.pageDefaultSize);
            refreshView.endRefreshing();
            QBLToast.show(R.string.text_no_data);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!state.lastPage) {
            getDataByRefresh(state.pageIndex + 1, state.pageDefaultSize);
            return true;
        }
        return false;
    }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GOT_RESULT:
                if (resultCode == getThis().RESULT_OK) {
                    refresher.refreshList();
//                    getDataByRefresh(adapter.getItemCount());
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
