package com.housingonitoringagent.homeworryagent.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.beans.OutletBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.HouseFragment;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * HomeWorry
 * Created by Administrator on 2016/3/2 0002.
 */
public class OutletActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabMain)
    TabLayout tabMain;
    @Bind(R.id.vpMain)
    ViewPager vpMain;
    @Bind(R.id.sivHead)
    SimpleDraweeView sivHead;
    @Bind(R.id.tvCompany)
    TextView tvCompany;
    @Bind(R.id.tvServeVillage)
    TextView tvServeVillage;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvPercent)
    TextView tvPercent;
    @Bind(R.id.tvComment1)
    TextView tvComment1;
    @Bind(R.id.pbComment1)
    ProgressBar pbComment1;
    @Bind(R.id.tvComment2)
    TextView tvComment2;
    @Bind(R.id.pbComment2)
    ProgressBar pbComment2;
    @Bind(R.id.tvComment3)
    TextView tvComment3;
    @Bind(R.id.pbComment3)
    ProgressBar pbComment3;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;

    private XAdapter<String> adapter;
    private HouseFragment fragmentBuy, fragmentRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.outlet);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initDate();
    }

    private void initViews() {
        vpMain.setAdapter(new HousePagerAdapter(getSupportFragmentManager()));
        tabMain.setupWithViewPager(vpMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_neighbour, menu);
        return true;
    }

    private void initDate() {
        setOutletDetail();
    }

    private void setOutletDetail() {
        showProgressDialog(getString(R.string.wait_a_few_times));
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.OUTLET_DETAIL,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        dismissProgressDialog();
                        int result = json.getIntValue("resultCode");
                        String msg = json.getString("message");
                        switch (result) {
                            case 1:
                                OutletBean.ContentBean bean = JSON.toJavaObject(json, OutletBean.class).getContent();
                                int good = 0, normal = 0, bad = 0;
                                int all = bean.getGoodEvaluateNumber() + bean.getNormalEvaluateNumber() + bean.getBadEvaluateNumber();
                                if (all != 0) {
                                    good = bean.getGoodEvaluateNumber() * 100 / all;
                                    normal = bean.getNormalEvaluateNumber() * 100 / all;
                                    bad = bean.getBadEvaluateNumber() * 100 / all;
                                }
                                sivHead.setImageURI(Uri.parse(bean.getPicture()));
                                tvCompany.setText(bean.getIntermediaryCompanyName());
                                tvServeVillage.setText(bean.getIntermediaryStoreName());
                                tvName.setText(bean.getIntermediaryStoreName());
                                tvAddress.setText(bean.getIntermediaryStoreAddress());
                                tvPercent.setText("好评率\n" + good + "%");
                                tvComment1.setText("好评（" + good + "%）");
                                pbComment1.setProgress(good);
                                tvComment2.setText("中评（" + normal + "%）");
                                pbComment2.setProgress(normal);
                                tvComment3.setText("差评（" + bad + "%）");
                                pbComment3.setProgress(bad);
                                tvCount.setText(bean.getVisitNumber() + "次");
                                break;
                            default:
                                QBLToast.show(msg);
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialog();
                        QBLToast.show(R.string.network_exception);

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("storeId", User.getIntermediaryStoreId());
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class HousePagerAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"二手房", "租房"};

        public HousePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            setupTabIcons();

         /*   HashMap<Integer, Fragment> frags =  new HashMap<>();
            if (frags.get(position) != null) {
                return frags.get(position);
            } else {
                return new HouseFragment(position);
            }*/

            switch (position) {
                case 0:
                    if (fragmentBuy == null) {
                        fragmentBuy = new HouseFragment(HouseFragment.TYPE_BUY);
                    }
                    return fragmentBuy;
                case 1:
                    if (fragmentRent == null) {
                        fragmentRent = new HouseFragment(HouseFragment.TYPE_RENT);
                    }
                    return fragmentRent;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
