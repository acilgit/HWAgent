package com.housingonitoringagent.homeworryagent.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.housingonitoringagent.homeworryagent.R;

/**
 * Created by Administrator on 2016/2/22 0022.
 * 这个Activity用于重新加载应用到登录页面，不在Activity管理内。
 */
public class ReLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        String title = getIntent().getStringExtra(getString(R.string.extra_title));
        String message = getIntent().getStringExtra(getString(R.string.extra_message));
        if ((title == null || title.isEmpty()) && (message == null || message.isEmpty())) {
            ReLoginActivity.this.startActivity(new Intent(ReLoginActivity.this, LoginActivity.class));
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false)
                    .setPositiveButton(getString(R.string.text_relogin), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ReLoginActivity.this.startActivity(new Intent(ReLoginActivity.this, LoginActivity.class));
                            dialog.dismiss();
                            finish();
                        }
                    }).setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            if (title != null && !title.isEmpty()) builder.setTitle(title);
            if (message != null && !message.isEmpty()) builder.setMessage(message);
            builder.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
