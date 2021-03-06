package com.housingonitoringagent.homeworryagent.utils.easeui;

import android.content.Context;
import android.net.Uri;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.CustomerMsgBean;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class EaseChatRowAdvertisement extends EaseChatRow {
    protected SimpleDraweeView mSample;
    protected TextView mTitle,mShape,mSize,mPrice,mAppointment;
    protected  CustomerMsgBean msg ;
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
        mSample = (SimpleDraweeView) findViewById(R.id.sample);
        mTitle = (TextView) findViewById(R.id.title);
        mShape = (TextView) findViewById(R.id.shape);
        mSize = (TextView) findViewById(R.id.size);
        mPrice = (TextView) findViewById(R.id.Price) ;
        mAppointment = (TextView) findViewById(R.id.appointment);
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
//        Log.e("消息内容",txtBody.getMessage().toString());
        try {
            JSONObject parse = JSON.parseObject(txtBody.getMessage());
            msg = JSON.toJavaObject(parse, CustomerMsgBean.class);
            mSample.setImageURI(Uri.parse(msg.getHouseSellPicture()));
//            mTitle.setText(msg.getTitle()+ "（"+ (msg.getType().equals("1") ? "租" : "售") + "）");
            mTitle.setText(msg.getTitle());
            mShape.setText(msg.getHouseShape());
            mPrice.setText(StringUtil.formatNumber(Double.parseDouble(msg.getPrice()), "#,##0.##") + (msg.getType().equals("1") ? "元" : "万"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBubbleClick() {

    }

}
