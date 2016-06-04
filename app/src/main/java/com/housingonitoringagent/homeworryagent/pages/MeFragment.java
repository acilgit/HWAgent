package com.housingonitoringagent.homeworryagent.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.activity.OrderListActivity;
import com.housingonitoringagent.homeworryagent.activity.OutletActivity;
import com.housingonitoringagent.homeworryagent.activity.SecurityActivity;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2014/9/30.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.rlOrderList)
    RelativeLayout rlOrderList;
    @Bind(R.id.rlSecurityCenter)
    RelativeLayout rlSecurityCenter;
    @Bind(R.id.sivHead)
    SimpleDraweeView sivHead;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvDetail)
    TextView tvDetail;

    public MeFragment() {
    }

    private BaseActivity getThis() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, currentView);
        return currentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rlOrderList.setOnClickListener(this);
        rlSecurityCenter.setOnClickListener(this);

        sivHead.setImageURI(Uri.parse(User.getHeadUrl()));
        tvName.setText(User.getUsername());
        tvDetail.setText(User.getUserSexName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlOrderList:
                getThis().start(OutletActivity.class);
//                getThis().start(OrderListActivity.class);
                break;
            case R.id.rlSecurityCenter:
                getThis().start(SecurityActivity.class);
                break;
            default:
                break;
        }
    }
}
