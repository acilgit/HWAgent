package com.housingonitoringagent.homeworryagent.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
public class SecurityActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rlLoginPassword)
    RelativeLayout rlLoginPassword;
    @Bind(R.id.rlSafeModifyPhone)
    RelativeLayout rlSafeModifyPhone;
    @Bind(R.id.pbSecurity)
    ProgressBar pbSecurity;
    @Bind(R.id.tvSecurityLevel)
    TextView tvSecurityLevel;
    @Bind(R.id.tvSafeModifyPhone)
    TextView tvSafeModifyPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securlty);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_security);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* init */
//        tvSecurityLevel.setText(User.get);
        tvSafeModifyPhone.setText(User.getMobilephone());


        /* init listeners */
        rlLoginPassword.setOnClickListener(this);
        rlSafeModifyPhone.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        EMClient.getInstance().logout(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //App.getRefWatcher(this).watch(this);
    }
}
