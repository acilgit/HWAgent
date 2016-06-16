package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.ChatFragment;
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

public class ChatActivity extends BaseActivity {

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


    private static final int REQUEST_CODE_SEND_LINK = 101;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    public static final int MESSAGE_TYPE_CUSTOMER = 6;
    private static final int MESSAGE_TYPE_CUSTOMER_SEND = 5;
    private static final int MESSAGE_TYPE_CUSTOMER_RECEIVED = 6;

    private ChatFragment chatFragment;

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
        chatFragment = new ChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());

//        chatFragment.getView().findViewById(R.id.title_bar).setVisibility(View.GONE);


        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        init();
        toolbar.setTitle(nick == null ? toChatUsername : nick);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentListener() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

                if (chatFragment.isRobot()) {
                    //设置消息扩展属性
                    message.setAttribute("em_robot_message", true);
                }
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
                startActivityForResult((new Intent(getThis(), ContextMenuActivity.class)).putExtra("message", message),
                        REQUEST_CODE_CONTEXT_MENU);
            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                switch (itemId) {
                    case ChatFragment.ITEM_VIDEO: //视频
                        chatFragment.selectVideoFromLocal();
//                        Intent intent = new Intent(getThis(), ImageGridActivity.class);
//                        startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                        break;
                    case ChatFragment.ITEM_FILE: //一般文件
                        //demo这里是通过系统api选择文件，实际app中最好是做成qq那种选择发送文件
                        chatFragment.selectFileFromLocal();
                        break;
//                    case ITEM_VOICE_CALL: //音频通话
//                        startVoiceCall();
//                        break;
//                    case ITEM_VIDEO_CALL: //视频通话
//                        startVideoCall();
//                        break;
                    default:
                        break;
                }
                //不覆盖已有的点击事件
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                EaseCustomChatRowProvider provider = new EaseCustomChatRowProvider() {
                    @Override
                    public int getCustomChatRowTypeCount() {
                        return 6;
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
                                if (type==MESSAGE_TYPE_CUSTOMER) {
                                    if (message.getFrom().equals(User.getUserId())) {
                                        return MESSAGE_TYPE_CUSTOMER_SEND;
                                    } else {
                                        return MESSAGE_TYPE_CUSTOMER_RECEIVED;
                                    }
                                }
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
                }, REQUEST_CODE_SEND_LINK);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SEND_LINK:
                    JSONObject json = new JSONObject();
                    try {
                        json.put("type", "houseSells");
                        json.put("id", "86dae364-79c4-45bc-b467-55095fb69f1c");
                        json.put("houseSellPicture", "http://192.168.1.222:9000/upload/image/20160601/1464781717490034364.jpg");
                        json.put("title", "精装2房");
                        json.put("houseShape", "二室二厅");
                        json.put("address", "元美路口");
                        json.put("price", "150.0");
                        json.put("storeId", "16a3aec5-63ef-4c65-a99d-ef36802e6e55");
                        json.put("agentId", "9b870b8c-1eb2-4599-a1a8-b1faa34ea80d");
                        chatFragment.sendCustomerMessage(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
