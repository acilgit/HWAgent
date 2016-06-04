package com.housingonitoringagent.homeworryagent.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.MeFragment;
import com.housingonitoringagent.homeworryagent.pages.RecordFragment;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.adapter.EMAChatManager;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatActivity  extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;

    private EaseChatFragment chatFragment;

    private String toChatUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

//        setSupportActionBar();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString("userId");
        //可以直接new EaseChatFratFragment使用
        chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        init();
    }

    private void init() {
        chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentListener() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {
            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                if (message.getIntAttribute("msgType", 0)==1) {

                }
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                EaseCustomChatRowProvider provider = new EaseCustomChatRowProvider() {
                    @Override
                    public int getCustomChatRowTypeCount() {
                        return 0;
                    }

                    @Override
                    public int getCustomChatRowType(EMMessage message) {
                        int type = message.getIntAttribute("msgType", 0);
                        if (type == 0) {
                            return type;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(message.getBody().toString());
                            if (jsonObject.getInt("type") > 0) {
                                type = jsonObject.getInt("type");
                            } else {
                                EMChatManager manager;
                                EMMessage msg = EMMessage.createTxtSendMessage("abc", "123");
                                msg.setAttribute("msgType", 1);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return type;
                    }

                    @Override
                    public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
                        EaseChatRow row = new EaseChatRow(getThis(), message, position, adapter) {

                            @Override
                            protected void onInflatView() {

                            }

                            @Override
                            protected void onFindViewById() {

                            }

                            @Override
                            protected void onUpdateView() {
                            }

                            @Override
                            protected void onSetUpView() {
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            protected void onBubbleClick() {

                            }
                        };
                        return row;
                    }
                };
                return provider;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
