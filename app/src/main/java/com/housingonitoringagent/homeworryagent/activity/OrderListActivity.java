package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean.ContentBean.PsBean.Content;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.views.XRefreshView;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
import com.housingonitoringagent.homeworryagent.views.XAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

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
public class OrderListActivity extends BaseActivity implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;
    @Bind(R.id.spnMain)
    MaterialSpinner spnMain;

    private XAdapter<Content> adapter;
    private XRefreshView<Content> refresher;

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

    private void initViews() {
        spnMain.setItems(getResources().getStringArray(R.array.admin_statue));
        spnMain.setOnItemSelectedListener(this);
        rvMain.setLayoutManager(new LinearLayoutManager(getThis()));

        adapter = new XAdapter<Content>(getThis(), new ArrayList<Content>(), R.layout.item_order) {
            @Override
            public void creatingHolder(final CustomHolder holder, final List<Content> dataList, int viewType) {
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btnDeposit:
                                getThis().start(ConfirmGiroActivity.class, new BaseIntent() {
                                    @Override
                                    public void setIntent(Intent intent) {
                                        intent.putExtra(getString(R.string.extra_bean), dataList.get(holder.getAdapterPosition()));
                                    }
                                }, REQUEST_CODE_GOT_RESULT);
                                break;
                            case R.id.btnDetail:
                                getThis().start(PaymentDetailActivity.class, new BaseIntent() {
                                    @Override
                                    public void setIntent(Intent intent) {
                                        intent.putExtra(getString(R.string.extra_bean), dataList.get(holder.getAdapterPosition()));
                                    }
                                }, REQUEST_CODE_GOT_RESULT);
                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.btnDeposit).setOnClickListener(clickListener);
                holder.getView(R.id.btnDetail).setOnClickListener(clickListener);
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<Content> dataList, int pos) {
                Content bean = dataList.get(pos);

                String strBIdCard = null;
                String strBankNum = null;
                try {
                    strBIdCard = bean.getBIdCard().substring(0, 4) + "****" + bean.getBIdCard().substring(bean.getBIdCard().length() - 3);
                    strBankNum = bean.getBankNumber().substring(0, 4) + "****" + bean.getBankNumber().substring(bean.getBIdCard().length() - 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.setText(R.id.tvOrderNo, bean.getOrderNo())
                        .setText(R.id.tvState, getResources().getStringArray(R.array.admin_statue)[bean.getOrderStatus()])
                        .setText(R.id.tvPartyA, bean.getAUserName())
                        .setText(R.id.tvPartyB, bean.getBUserName())
                        .setText(R.id.tvPartyBId, strBIdCard)
                        .setText(R.id.tvPartyAgent, User.getUsername())
                        .setText(R.id.tvPartyAgentPhone, User.getMobilephone())
                        .setText(R.id.tvBank, bean.getBankName())
                        .setText(R.id.tvPartyBBankAccount, strBankNum)
                        .setText(R.id.tvAmountSupervision, StringUtil.formatNumber(bean.getOrderMoney()));
                /* todo: OrderStatus 显示 申请转款 */
                holder.getView(R.id.btnDeposit).setVisibility(bean.getOrderStatus()== 3 ? View.VISIBLE : View.GONE);
            }

               @Override
            protected List<Content> setFilterForAdapter(List<Content> mainList) {
                List<Content> list = new ArrayList<>();
                int intFilter = spnMain.getSelectedIndex();
                if (intFilter == 0) {
                    return super.setFilterForAdapter(mainList);
                } else {
                    for (Content item : mainList) {
                        if (item.getOrderStatus() == intFilter) {
                            list.add(item);
                        }
                    }
                }
                return list;
            }
        };
        rvMain.setAdapter(adapter);

        refresher = new XRefreshView<>(getThis(), refreshView, true, adapter, new XRefreshView.IRefreshRequest<Content>() {
            @Override
            public String setVolleyParamsReturnUrl(Map<String, String> params) {
                return Const.serviceMethod.HOUSE_DEAL_LIST;
            }

            @Override
            public List<Content> handleJson(JSONObject json, XRefreshView.RefreshState stateForSetLastPage) {
                HouseDealBean mainBean = JSON.parseObject(json.toString(), HouseDealBean.class);
                stateForSetLastPage.setLastPage(mainBean.getContent().getPs().isLastPage());
                return mainBean.getContent().getPs().getContent();
            }

            @Override
            public boolean ignoreSameItem(Content newItem, Content listItem) {
                return newItem.getId().equals(listItem.getId());
            }

            @Override
            public int compareTo(Content item0, Content item1) {
                return item1.getCreateTimeCompare().compareTo(item0.getCreateTimeCompare());
            }
        });
    }

    private void initDate() {
        refresher.refreshList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GOT_RESULT:
                if (resultCode == getThis().RESULT_OK) {
                    refresher.refreshList();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnApply:
//                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        adapter.resetDataList();
    }
}
