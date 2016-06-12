package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;


import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.ImageGridFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class ImageGridActivity extends BaseActivity {


    private static final String TAG = "ImageGridActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (BuildConfig.DEBUG) {
//            Utils.enableStrictMode();
//        }
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new ImageGridFragment(), TAG);
            ft.commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    }
