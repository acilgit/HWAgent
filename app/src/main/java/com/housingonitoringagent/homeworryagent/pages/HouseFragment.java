package com.housingonitoringagent.homeworryagent.pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.activity.WebViewActivity;
import com.housingonitoringagent.homeworryagent.beans.HouseRecommendBean;
import com.housingonitoringagent.homeworryagent.beans.HouseRecommendBean.ContentBean.Content;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.views.XRefreshView;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
import com.housingonitoringagent.homeworryagent.utils.uikit.recyclerview.HorizontalDividerItemDecoration;
import com.housingonitoringagent.homeworryagent.views.XAdapter;
import com.housingonitoringagent.homeworryagent.views.tags.FlowTagLayout;
import com.housingonitoringagent.homeworryagent.views.tags.TagAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2014/9/30.
 */

@SuppressLint("ValidFragment")
public class HouseFragment extends Fragment {
    @Bind(R.id.rvMain)
    RecyclerView rvMain;
    @Bind(R.id.refreshView)
    BGARefreshLayout refreshView;

    public static final int TYPE_BUY = 0;
    public static final int TYPE_RENT = 1;

    private int fragmentType = 0;

    private XAdapter<Content> adapter;
    private XRefreshView<Content> refresher;

    @SuppressLint("ValidFragment")
    public HouseFragment(int permitType) {
        this.fragmentType = permitType;
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
        rvMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getThis())
                .colorResId(R.color.divider_line).sizeResId(R.dimen.line_1px)
                .marginResId(R.dimen.item_margin_icon, R.dimen.item_margin_icon).build()
        );

        adapter = new XAdapter<Content>(getThis(), new ArrayList<Content>(), R.layout.item_house) {
            @Override
            public void creatingHolder(final CustomHolder holder, final List<Content> dataList, int viewType) {
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btnSendLink:
                                Intent intent = new Intent();
                                Content bean = dataList.get(holder.getAdapterPosition());
                                bean.setPermitType(fragmentType == TYPE_BUY ? 2 : 1);
                                intent.putExtra(getString(R.string.extra_bean), bean);
                                getThis().setResult(Activity.RESULT_OK, intent);
                                getThis().finish();
                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.btnSendLink).setOnClickListener(clickListener);
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<Content> dataList, int pos) {
                Content bean = dataList.get(pos);
                String unit = fragmentType == TYPE_BUY ? "万" : "元/月";
                holder.setText(R.id.tvHouseName, bean.getTitle())
                        .setText(R.id.tvHouseAddress, bean.getAddress())
                        .setText(R.id.tvHouseDetail, bean.getHouseShape() + "　面积：" + bean.getHouseSize())
                        .setText(R.id.tvHousePrice, StringUtil.formatNumber(bean.getPrice(), "#,##0.##") + unit);
                ((SimpleDraweeView) holder.getView(R.id.sivHouse)).setImageURI(Uri.parse(bean.getCoverPicture()));
                TagAdapter adapter = new TagAdapter();
                ((FlowTagLayout) holder.getView(R.id.ftlTags)).setAdapter(adapter);
                if (bean.getTags()!=null && !TextUtils.isEmpty(bean.getTags())) {
                    String[] labels = bean.getTags().split(",");
                    if (labels.length > 0) {
                        adapter.setDataList(Arrays.asList(labels));
                    }
                }
            }

            @Override
            protected void handleItemViewClick(final CustomHolder holder, final Content item) {
                getThis().start(WebViewActivity.class, new BaseActivity.BaseIntent() {
                    @Override
                    public void setIntent(Intent intent) {
                        String url = fragmentType == TYPE_BUY ? Const.serviceMethod.SECOND_HAND_HOUSE_INFO : Const.serviceMethod.RENTAL_HOUSE_INFO;
                        url += "id=" + item.getId();
                        intent.putExtra(getThis().getString(R.string.extra_url), url);
                    }
                });
                super.handleItemViewClick(holder, item);
            }
        };
        rvMain.setAdapter(adapter);

        refresher = new XRefreshView<>(getThis(), refreshView, true, adapter, new XRefreshView.IRefreshRequest<Content>() {
            @Override
            public String setVolleyParamsReturnUrl(Map<String, String> params) {
                params.put("intermediaryStoreId", User.getIntermediaryStoreId());
                return fragmentType == TYPE_BUY ? Const.serviceMethod.OUTLET_HOUSE_SELL_LIST : Const.serviceMethod.OUTLET_HOUSE_RENT_LIST;
            }

            @Override
            public List<Content> handleJson(JSONObject json, XRefreshView.RefreshState stateForSetLastPage) {
                HouseRecommendBean mainBean = JSON.parseObject(json.toString(), HouseRecommendBean.class);
                stateForSetLastPage.setLastPage(mainBean.getContent().isLastPage());
                return mainBean.getContent().getContent();
            }

            @Override
            public boolean ignoreSameItem(Content newItem, Content listItem) {
                return newItem.getId().equals(listItem.getId());
            }

            @Override
            public int compareTo(Content item0, Content item1) {
                return 0;
            }
        });


    }

    private void initDate() {
        refresher.refreshList();
    }


}
