package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.easeui.ChatRowVoiceCall;
import com.housingonitoringagent.homeworryagent.utils.easeui.Constant;
import com.housingonitoringagent.homeworryagent.utils.easeui.EaseChatRowAdvertisement;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    //避免和基类定义的常量可能发生的冲突，常量从11开始定义
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

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
        toChatUsername = getIntent().getStringExtra(getString(R.string.extra_user_id));
        String nick = EaseUserUtils.getUserInfo(toChatUsername).getNick();
        //可以直接new EaseChatFratFragment使用
        chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());

//        chatFragment.getView().findViewById(R.id.title_bar).setVisibility(View.GONE);


        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        init();
        toolbar.setTitle(nick == null? toChatUsername : nick);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentListener() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {
                JSONObject json = new JSONObject();
                try {
                    json.put("avatar", User.getHeadUrl());
                    json.put("nickname", User.getNickname());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                message.setAttribute("user", json.toString());
            }

            @Override
            public void onEnterToChatDetails() {
//                EMChatManager manager;
//                EMMessage msg = EMMessage.createTxtSendMessage("abc", "123");
//                msg.setAttribute("msgType", 1);

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
//                if (message.getIntAttribute("msgType", 0)==1) {
//                    onClickItem();
//                    return true;
//                }
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
                        if (message.getType() == EMMessage.Type.TXT) {
                            //语音通话类型
                            if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                            } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                                //视频通话
                                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                            } else {
                                int type = message.getIntAttribute("msgType", 0);
                                return type;
                            }
                        }
                        return 0;
                    }

                    @Override
                    public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {

                        if (message.getType() == EMMessage.Type.TXT) {
                            // 语音通话,  视频通话
                            if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                                    message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                                return new ChatRowVoiceCall(getThis(), message, position, adapter);
                            } else if (message.getIntAttribute("msgType", 0) == 5 || message.getIntAttribute("msgType", 0) == 6) {
                                EaseChatRow chatRow = new EaseChatRowAdvertisement(getThis(), message, position, adapter);
                                return chatRow;
                            }
                        }
                        return null;
                    }
                };
                return provider;
//                return null;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outlet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_outlet:
                start(OutletActivity.class, new BaseIntent() {
                    @Override
                    public void setIntent(Intent intent) {
                        intent.putExtra("", "");
                    }
                });
                break;
            default:
                break;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickItem() {

    }
}
