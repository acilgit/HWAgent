package com.housingonitoringagent.homeworryagent.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;

public class ShowingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing);
    }
}
