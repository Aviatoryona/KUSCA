package com.aviator.kusca;

import android.support.v7.widget.LinearLayoutManager;

import com.aviator.kusca.superrec.BaseActivity;

public class ListActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vertical_sample;
    }

    @Override
    protected boolean isSwipeToDismissEnabled() {
        return true;
    }

    @Override
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}
