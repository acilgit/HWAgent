package com.housingonitoringagent.homeworryagent.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.utils.easeui.Constant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseBaseActivity;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class ContextMenuActivity extends EaseBaseActivity {
    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EMMessage message = getIntent().getParcelableExtra("message");

        int type = message.getType().ordinal();
        if (type == EMMessage.Type.TXT.ordinal()) {
            if(message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false) ||
                    message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                setContentView(R.layout.em_context_menu_for_location);
            }else if(message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                setContentView(R.layout.em_context_menu_for_image);
            }
        } else if (type == EMMessage.Type.LOCATION.ordinal()) {
            setContentView(R.layout.em_context_menu_for_location);
        } else if (type == EMMessage.Type.IMAGE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_image);
        } else if (type == EMMessage.Type.VOICE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_voice);
        } else if (type == EMMessage.Type.VIDEO.ordinal()) {
            setContentView(R.layout.em_context_menu_for_video);
        } else if (type == EMMessage.Type.FILE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_location);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void copy(View view){
        setResult(RESULT_CODE_COPY);
        finish();
    }
    public void delete(View view){
        setResult(RESULT_CODE_DELETE);
        finish();
    }
}
