package com.housingonitoringagent.homeworryagent.activity;

import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.ConversationListFragment;
import com.housingonitoringagent.homeworryagent.pages.MeFragment;
import com.housingonitoringagent.homeworryagent.pages.ShowingRecordFragment;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    @Bind(R.id.tabMain)
    TabLayout tabMain;
    @Bind(R.id.vpMain)
    ViewPager vpMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainPagerAdapter mainPagerAdapter;
    private EaseConversationListFragment conversationFragment;
    private ShowingRecordFragment recordFragment;
    private MeFragment meFragment;
    private List<Fragment> fragments;
    private int[] tabIconsNormal = {
            R.mipmap.icon_msg_0,
            R.mipmap.icon_showing_0,
            R.mipmap.icon_me_0
    };
    private int[] tabIconsSelected = {
            R.mipmap.icon_msg_1,
            R.mipmap.icon_showing_1,
            R.mipmap.icon_me_1
    };
    private String[] titles = {
            "消息",
            "看房",
            "我的",
            "通讯录"
    };

    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        vpMain.setAdapter(mainPagerAdapter);
        tabMain.setupWithViewPager(vpMain);
        setupTabIcons();

        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                currentPage = position;
                View view = tab.getCustomView();
                TextView tvTab = (TextView) view.findViewById(R.id.tvTab);
                ImageView ivTab = (ImageView) view.findViewById(R.id.ivTab);
                ivTab.setImageResource(tabIconsSelected[position]);
                tvTab.setTextColor(getResources().getColor(R.color.colorPrimary));
                vpMain.setCurrentItem(position, true);
                setToolbar(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                View view = tab.getCustomView();
                TextView tvTab = (TextView) view.findViewById(R.id.tvTab);
                ImageView ivTab = (ImageView) view.findViewById(R.id.ivTab);
                ivTab.setImageResource(tabIconsNormal[position]);
                tvTab.setText(titles[position]);
                tvTab.setTextColor(getResources().getColor(R.color.text_gray));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        init();
    }

    private void setToolbar(int pos) {
        String title = "";
        int menuId;
        switch (pos) {
            case 0:
                title = getString(R.string.title_messages);
                menuId = R.menu.menu_delete_message;
                break;
            case 1:
                title = getString(R.string.title_showing);
                menuId = R.menu.menu_delete_message;
                break;
            case 2:
                title = getString(R.string.title_me);
                menuId = R.menu.menu_delete_message;
                break;
            default:
                break;
        }
        toolbar.setTitle(title);
//        toolbar.menu
    }

    private void setupTabIcons() {
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
            TextView tvTab = (TextView) view.findViewById(R.id.tvTab);
            ImageView ivTab = (ImageView) view.findViewById(R.id.ivTab);
            tvTab.setText(titles[i]);
            tvTab.setTextColor(getResources().getColor(i==currentPage ? R.color.colorPrimary :R.color.text_gray));
            ivTab.setImageResource(i==currentPage ? tabIconsSelected[i] : tabIconsNormal[i]);

            tabMain.getTabAt(i).setCustomView(view);
        }
    }


    private void init() {

        //聊天人或群id
//        toChatUsername = getIntent().getExtras().getString("userId");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu., menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            setupTabIcons();
            switch (position) {
                case 0:
                    //可以直接new EaseChatFratFragment使用
                    if (conversationFragment == null) {
                        conversationFragment = new ConversationListFragment();
                        //传入参数
                        getIntent().putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                        conversationFragment.setArguments(getIntent().getExtras());
                        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
                            @Override
                            public void onListItemClicked(EMConversation conversation) {

                            }
                        });
                    }
                    return conversationFragment;
//                    break;
                case 1:
                    if (recordFragment == null) {
                        recordFragment = new ShowingRecordFragment();
                    }
                    return recordFragment;
//                    break;
                case 2:
                    if (meFragment == null) {
                        meFragment = new MeFragment();
                    }
                    return meFragment;
//                    break;
                default:
                    return null;
//                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

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
            return "";
        }
    }
}
