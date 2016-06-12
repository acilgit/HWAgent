package com.housingonitoringagent.homeworryagent.utils.easeui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class EaseChatRowAdvertisement extends EaseChatRow {
    protected ImageView mSample;
    protected TextView mTitle,mShape,mSize,mPrice,mAppointment;
//    protected  CustomMsgBean msg ;
    public EaseChatRowAdvertisement(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.app_row_received_message : R.layout.app_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        mSample = (ImageView) findViewById(R.id.sample);
        mTitle = (TextView) findViewById(R.id.title);
        mShape = (TextView) findViewById(R.id.shape);
        mSize = (TextView) findViewById(R.id.size);
        mPrice = (TextView) findViewById(R.id.Price);
        mAppointment = (TextView) findViewById(R.id.appointment);
        mAppointment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QBLToast.show("asdadada");
            }
        });
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Log.e("消息内容",txtBody.getMessage().toString());
        JSONObject parse = JSON.parseObject(txtBody.getMessage().toString());
//         msg = JSON.toJavaObject(parse, CustomMsgBean.class);
//        ImageLoader.getInstance().displayImage(msg.getHouseSellPicture(), mSample, ImageLoaderUtil.displayRoundedOptions);
//        mTitle.setText(msg.getTitle());
//        mShape.setText(msg.getHouseShape());
//        mPrice.setText(msg.getPrice());
    }

    @Override
    protected void onBubbleClick() {

    }

}
