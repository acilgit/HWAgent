package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.HouseFragment;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * HomeWorry
 * Created by Administrator on 2016/3/2 0002.
 */
public class OutletActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabMain)
    TabLayout tabMain;
    @Bind(R.id.vpMain)
    ViewPager vpMain;

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

       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent();
//        if (position == ImageBean.bmp.size()) {
//            intent.setClass(getThis(), PicSelectActivity.class);
//            startActivityForResult(intent, 0);
//        } else {
//            intent.setClass(getThis(), ImageBrowserActivity.class);
//            intent.putExtra("isdel", false);
//            intent.putExtra("position", position);
//            startActivity(intent);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
          /*  case REQUEST_CODE_NEW_COMMENT:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getStringExtra("data") != null) {
                        String json = data.getStringExtra("data");
//                        NeighbourListBean.NeighbourMessagesBean.ContentBean bean = JSON.parseObject(json, NeighbourListBean.NeighbourMessagesBean.ContentBean.class);
//                        adapter.addItem(0, bean);
                    }
                }
                break;*/
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
            List<Fragment> list = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                list.add(new HouseFragment(i));
            }
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
        /*    String title ="";
            Drawable image;
            SpannableString sb = new SpannableString(title + "   ");
            switch (position) {
                case 0:
                    title = "消息";
                    image = getResources().getDrawable(currentPage==0? R.mipmap.icon_msg_1 : R.mipmap.icon_msg_0);
                    break;
                case 1:
                    title = "看房";
                    image = getResources().getDrawable(currentPage==1? R.mipmap.icon_showing_1 : R.mipmap.icon_showing_0);
                    break;
                case 2:
                    title = "我的";
                    image = getResources().getDrawable(currentPage==2? R.mipmap.icon_me_1 : R.mipmap.icon_me_0);
                    break;
                default:
                    image = getResources().getDrawable(R.mipmap.icon_contact_0);
                    break;
            }

            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            // Replace blank spaces with image icon
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, title.length(), title.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        }
    }
}
