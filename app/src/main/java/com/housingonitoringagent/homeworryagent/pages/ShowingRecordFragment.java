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
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.activity.ShowingActivity;
import com.housingonitoringagent.homeworryagent.beans.ShowHouseBean;
import com.housingonitoringagent.homeworryagent.beans.ShowHouseBean.ContentBean.Content;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.DateUtil;
import com.housingonitoringagent.homeworryagent.utils.RefreshListUtil;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2014/9/30.
 */

public class ShowingRecordFragment extends Fragment {

    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;

    private XAdapter<Content> adapter;

    private RefreshListUtil<Content> refresher;

    public ShowingRecordFragment() {

    }

    private BaseActivity getThis() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.layout_refresh_list, container, false);
        ButterKnife.bind(this, currentView);
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

        adapter = new XAdapter<Content>(getThis(), new ArrayList<Content>(), R.layout.item_showing) {
            @Override
            public void creatingHolder(final CustomHolder holder, final List<Content> dataList, int viewType) {
                /*View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.llMain:
                                final Content item = dataList.get(holder.getAdapterPosition());
                                if (item.getPermitType() == ShowHouseBean.PERMIT_STATUE_SHOWING || item.getPermitType() == ShowHouseBean.PERMIT_STATUE_WAIT) {
                                    getThis().start(ShowingActivity.class, new BaseActivity.BaseIntent() {
                                        @Override
                                        public void setIntent(Intent intent) {
                                            intent.putExtra(getThis().getString(R.string.extra_bean), item);
                                        }
                                    }, BaseActivity.REQUEST_CODE_GOT_RESULT);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.llMain).setOnClickListener(clickListener);*/
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<Content> dataList, int pos) {
                Content bean = dataList.get(pos);
                long appointmentTime = bean.getCreateTime();
                long startTime = bean.getStartTime();
                String unit = bean.getPermitType() == 0 ? "元" : "万";
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
                    }, BaseActivity.REQUEST_CODE_GOT_RESULT);
                }
            }
        };
        rvMain.setAdapter(adapter);

        refresher = new RefreshListUtil<>(getThis(), refreshView, true, adapter, new RefreshListUtil.IRefreshRequest<Content>() {
            @Override
            public String setVolleyParamsReturnUrl(Map<String, String> params) {
                params.put("permitType", "0");
                return Const.serviceMethod.VISIT_PERMISSSION_LIST;
            }

            @Override
            public List<Content> handleJson(JSONObject json, RefreshListUtil.RefreshState stateForSetLastPage) {
                ShowHouseBean mainBean = JSON.parseObject(json.toString(), ShowHouseBean.class);
                stateForSetLastPage.setLastPage(mainBean.getContent().isLastPage());
                return mainBean.getContent().getContent();
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

    public void initDate() {
        refresher.refreshList();
    }

}
