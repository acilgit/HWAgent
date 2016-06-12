package com.housingonitoringagent.homeworryagent.utils.easeui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyChronometer extends Chronometer {
    public MyChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChronometer(Context context) {
        super(context);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        //屏幕隐藏时继续计时
        visibility = View.VISIBLE;
        super.onWindowVisibilityChanged(visibility);
    }
}
