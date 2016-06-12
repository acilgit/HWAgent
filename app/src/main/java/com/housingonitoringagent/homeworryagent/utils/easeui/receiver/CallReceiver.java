package com.housingonitoringagent.homeworryagent.utils.easeui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.housingonitoringagent.homeworryagent.activity.VideoCallActivity;
import com.housingonitoringagent.homeworryagent.activity.VoiceCallActivity;
import com.housingonitoringagent.homeworryagent.utils.easeui.EaseHelper;
import com.hyphenate.util.EMLog;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!EaseHelper.getInstance().isLoggedIn())
            return;
        //拨打方username
        String from = intent.getStringExtra("from");
        //call type
        String type = intent.getStringExtra("type");
        if("video".equals(type)){ //视频通话
            context.startActivity(new Intent(context, VideoCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }else{ //音频通话
            context.startActivity(new Intent(context, VoiceCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        EMLog.d("CallReceiver", "app received a incoming call");
    }
}
