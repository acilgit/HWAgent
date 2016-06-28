package com.housingonitoringagent.homeworryagent.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
public class SecurityActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rlPassword)
    RelativeLayout rlLoginPassword;
    @Bind(R.id.rlSafeModifyPhone)
    RelativeLayout rlSafeModifyPhone;
    @Bind(R.id.pbSecurity)
    ProgressBar pbSecurity;
    @Bind(R.id.tvSecurityLevel)
    TextView tvSecurityLevel;
    @Bind(R.id.tvSafeModifyPhone)
    TextView tvSafeModifyPhone;
    @Bind(R.id.btnLogout)
    Button btnLogout;

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

        int level = User.getSafeLevel();
        pbSecurity.setProgress(level==0 ? 30 : (level==1 ? 60 : 100));
        tvSecurityLevel.setText(level==0 ? "低" : (level==1 ? "中" : "高"));

        /* init listeners */
        rlLoginPassword.setOnClickListener(this);
//        rlSafeModifyPhone.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSafeModifyPhone:
                break;
            case R.id.rlPassword:
                start(PasswordChangeActivity.class);
                break;
            case R.id.btnLogout:
                new AlertDialog.Builder(getThis()).setCancelable(true).setTitle(getString(R.string.logout))
                        .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User.logOut();
                                if (EMClient.getInstance().isConnected() || EMClient.getInstance().isLoggedInBefore()) {
                                    EMClient.getInstance().logout(true);
                                }
                                start(LoginActivity.class, new BaseIntent() {
                                    @Override
                                    public void setIntent(Intent intent) {
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    }
                                });
                                finish();
                            }
                        }).setNegativeButton(R.string.cancel, null)
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //App.getRefWatcher(this).watch(this);
    }


}
