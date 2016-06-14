package com.housingonitoringagent.homeworryagent.utils.easeui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.housingonitoringagent.homeworryagent.App;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.activity.ChatActivity;
import com.housingonitoringagent.homeworryagent.activity.ConversationListActivity;
import com.housingonitoringagent.homeworryagent.activity.VideoCallActivity;
import com.housingonitoringagent.homeworryagent.activity.VoiceCallActivity;
import com.housingonitoringagent.homeworryagent.utils.easeui.receiver.CallReceiver;
import com.housingonitoringagent.homeworryagent.utils.easeui.util.EmojiconExampleGroupData;
import com.housingonitoringagent.homeworryagent.utils.easeui.util.RobotUser;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class EaseHelper {

    protected static final String TAG = "EaseHelper";

    private EaseUI easeUI;

    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;

    private Map<String, EaseUser> contactList;

    private Map<String, RobotUser> robotList;

    private static EaseHelper instance = null;

    private boolean isSyncingGroupsWithServer = false;
    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingBlackListWithServer = false;
    private boolean isGroupsSyncedWithServer = false;
    private boolean isContactsSyncedWithServer = false;
    private boolean isBlackListSyncedWithServer = false;

    private boolean alreadyNotified = false;

    public boolean isVoiceCalling;
    public boolean isVideoCalling;

    private String username;

    private Context appContext;

    private CallReceiver callReceiver;

    private EMConnectionListener connectionListener;

    private EaseHelper() {
    }

    public synchronized static EaseHelper getInstance() {
        if (instance == null) {
            instance = new EaseHelper();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context
     *            application context
     */
    public void init(Context context) {
        EMOptions options = initChatOptions();
        //options传null则使用默认的
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;

            //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
            EMClient.getInstance().setDebugMode(false);
            //get easeui instance
            easeUI = EaseUI.getInstance();
            //调用easeui的api设置providers
            setEaseUIProviders();
            //设置全局监听
            setGlobalListeners();
        }
    }


    private EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");

        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        options.setAutoLogin(true);
        //使用gcm和mipush时，把里面的参数替换成自己app申请的
        //设置google推送，需要的GCM的app可以设置此参数
//        options.setGCMNumber("324169311137");
        //在小米手机上当app被kill时使用小米推送进行消息提示，同GCM一样不是必须的
//        options.setMipushConfig("2882303761517426801", "5381742660801");
        //集成华为推送时需要设置
//        options.setHuaweiPushAppId("10492024");

//        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
//        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
//        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());

        return options;
    }

    protected void setEaseUIProviders() {

        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                EaseUser user = new EaseUser(username);
                if (username.equals(EMClient.getInstance().getCurrentUser())) {
                    user.setAvatar(User.getHeadUrl());
                    user.setNick(User.getNickname());
                } else {
                    JSONObject json ;
                    String imUser = User.getIMUser(username);
                    try {
                        if (null != imUser) {
                            json = new JSONObject(imUser);
                            user.setAvatar(json.getString("avatar"));
                            user.setNick(json.getString("nickname"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                return user;
            }
        });
        //需要easeui库显示用户头像和昵称设置此provider
//        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
//
//            @Override
//            public EaseUser getUser(String username) {
//                return getUserInfo(username);
//            }
//        });

        //不设置，则使用easeui默认的
       /* easeUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {

            @Override
            public boolean isSpeakerOpened() {
                return model.getSettingMsgSpeaker();
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return model.getSettingMsgVibrate();
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return model.getSettingMsgSound();
            }

            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                if(message == null){
                    return model.getSettingMsgNotification();
                }
                if(!model.getSettingMsgNotification()){
                    return false;
                }else{
                    //如果允许新消息提示
                    //屏蔽的用户和群组不提示用户
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // 获取设置的不提示新消息的用户或者群组ids
                    if (message.getChatType() == EMMessage.ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = model.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = model.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });*/
        //设置表情provider
        easeUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {

            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
                for(EaseEmojicon emojicon : data.getEmojiconList()){
                    if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                //返回文字表情emoji文本和图片(resource id或者本地路径)的映射map
                return null;
            }
        });

        //不设置，则使用easeui默认的
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //修改标题,这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = EaseUserUtils.getUserInfo(message.getFrom());
                if(user != null){
                    return user.getNick() + ": " + ticker;
                }else{
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //设置点击通知栏跳转事件
                Intent intent = new Intent(appContext, ChatActivity.class);
                intent.putExtra("userId", message.getFrom());
                intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);

                //有电话时优先跳转到通话页面
                /*if(isVideoCalling){
                    intent = new Intent(appContext, VideoCallActivity.class);
                }else if(isVoiceCalling){
                    intent = new Intent(appContext, VoiceCallActivity.class);
                }else{
                    EMMessage.ChatType chatType = message.getChatType();
                    if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                        intent.putExtra("userId", message.getFrom());
                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
                    } else { // 群聊信息
                        // message.getTo()为群聊id
                        intent.putExtra("userId", message.getTo());
                        if(chatType == EMMessage.ChatType.GroupChat){
                            intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                        }else{
                            intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                        }

                    }
                }*/
                return intent;
            }
        });
    }

    /**
     * 设置全局事件监听
     */
    protected void setGlobalListeners(){
//        syncGroupsListeners = new ArrayList<DataSyncListener>();
//        syncContactsListeners = new ArrayList<DataSyncListener>();
//        syncBlackListListeners = new ArrayList<DataSyncListener>();

//        isGroupsSyncedWithServer = model.isGroupsSynced();
//        isContactsSyncedWithServer = model.isContactSynced();
//        isBlackListSyncedWithServer = model.isBacklistSynced();

        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                if (error == EMError.USER_REMOVED) {
                    onCurrentAccountRemoved();
                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onConnectionConflict();
                }
            }

            @Override
            public void onConnected() {

                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
                if(isGroupsSyncedWithServer && isContactsSyncedWithServer){
                    new Thread(){
                        @Override
                        public void run(){
                            EaseHelper.getInstance().notifyForRecevingEvents();
                        }
                    }.start();
                }else{
                  /*  if(!isGroupsSyncedWithServer){
                        asyncFetchGroupsFromServer(null);
                    }

                    if(!isContactsSyncedWithServer){
                        asyncFetchContactsFromServer(null);
                    }

                    if(!isBlackListSyncedWithServer){
                        asyncFetchBlackListFromServer(null);
                    }*/
                }
            }
        };


        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if(callReceiver == null){
            callReceiver = new CallReceiver();
        }

        //注册通话广播接收者
        appContext.registerReceiver(callReceiver, callFilter);
        //注册连接监听
        EMClient.getInstance().addConnectionListener(connectionListener);
        //注册群组和联系人监听
//        registerGroupAndContactListener();
        //注册消息事件监听
        registerEventListener();

    }

    /**
     * 账号在别的设备登录
     */
    protected void onConnectionConflict(){
        Intent intent = new Intent(appContext, ConversationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_CONFLICT, true);
        appContext.startActivity(intent);
    }

    /**
     * 账号被移除
     */
    protected void onCurrentAccountRemoved(){
        Intent intent = new Intent(appContext, ConversationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
        appContext.startActivity(intent);
    }

   /* private EaseUser getUserInfo(String username){
        //获取user信息，demo是从内存的好友列表里获取，
        //实际开发中，可能还需要从服务器获取用户信息,
        //从服务器获取的数据，最好缓存起来，避免频繁的网络请求
        EaseUser user = null;
        if(username.equals(EMClient.getInstance().getCurrentUser()))
            return getUserProfileManager().getCurrentUserInfo();
        user = getContactList().get(username);
        //TODO 获取不在好友列表里的群成员具体信息，即陌生人信息，demo未实现
        if(user == null && getRobotList() != null){
            user = getRobotList().get(username);
        }
        return user;
    }*/
    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    try {

                        String user = message.getStringAttribute("user");
                        if (!user.equals("{}")) {
                            User.setIMUser(message.getFrom(), user);
                        }
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    //应用在后台，不需要刷新UI,通知栏提示新消息
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    } else {

                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "收到透传消息");
                    //获取消息body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action

                    //获取扩展属性 此处省略
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("透传消息：action:%s,message:%s", action,message.toString()));
                    final String str = appContext.getString(com.hyphenate.easeui.R.string.receive_the_passthrough);

                    final String CMD_TOAST_BROADCAST = "hyphenate.demo.cmd.toast";
                    IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

                    if(broadCastReceiver == null){
                        broadCastReceiver = new BroadcastReceiver(){

                            @Override
                            public void onReceive(Context context, Intent intent) {
                                // TODO Auto-generated method stub
                                Toast.makeText(appContext, intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
                            }
                        };

                        //注册广播接收者
                        appContext.registerReceiver(broadCastReceiver,cmdFilter);
                    }

                    Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                    broadcastIntent.putExtra("cmd_value", str+action);
                    appContext.sendBroadcast(broadcastIntent, null);
                }
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    /**
     * 是否登录成功过
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 退出登录
     *
     * @param unbindDeviceToken
     *            是否解绑设备token(使用GCM才有)
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback, final Activity activity) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset(activity);
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                reset(activity);
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * 获取消息通知类
     * @return
     */
    public EaseNotifier getNotifier(){
        return easeUI.getNotifier();
    }

  /*  public Model getModel(){
        return (Model) model;
    }*/

    /**
     * 设置好友user list到内存中
     *
     */
    public void setContactList(Map<String, EaseUser> aContactList) {
        if(aContactList == null){
            if (contactList != null) {
                contactList.clear();
            }
            return;
        }

        contactList = aContactList;
    }

    /**
     * 保存单个user
     */
    public void saveContact(EaseUser user){
        contactList.put(user.getUsername(), user);
//        model.saveContact(user);
    }

    /**
     * 获取好友list
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
//        if (isLoggedIn() && contactList == null) {
//            contactList = model.getContactList();
//        }

        // return a empty non-null object to avoid app crash
        if(contactList == null){
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }

    /**
     * 设置当前用户的环信id
     * @param username
     */
    public void setCurrentUserName(String username){
        this.username = username;
//        model.setCurrentUserName(username);
    }

    /**
     * 获取当前用户的环信id
     */
    public String getCurrentUsernName(){
        if(username == null){
//            username = model.getCurrentUsernName();
        }
        return username;
    }

    public void setRobotList(Map<String, RobotUser> robotList) {
        this.robotList = robotList;
    }

    public Map<String, RobotUser> getRobotList() {
        if (isLoggedIn() && robotList == null) {
//            robotList = model.getRobotList();
        }
        return robotList;
    }

    /**
     * update user list to cach And db
     *
     */
    public void updateContactList(List<EaseUser> contactInfoList) {
        for (EaseUser u : contactInfoList) {
            contactList.put(u.getUsername(), u);
        }
        ArrayList<EaseUser> mList = new ArrayList<EaseUser>();
        mList.addAll(contactList.values());
//        model.saveContactList(mList);
    }

   /* public UserProfileManager getUserProfileManager() {
        if (userProManager == null) {
            userProManager = new UserProfileManager();
        }
        return userProManager;
    }*/

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void notifyForRecevingEvents(){
        if(alreadyNotified){
            return;
        }
        // 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
        alreadyNotified = true;
    }

    synchronized void reset(Activity activity){
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;

        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;

        alreadyNotified = false;
//        isGroupAndContactListenerRegisted = false;

        setContactList(null);
        setRobotList(null);
//        getUserProfileManager().reset();
//       EaseDBManager.getInstance().closeDB();
        User.logOut();
        App.getInstance().restart(activity);
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }
}
