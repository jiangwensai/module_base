package com.example.jiangwensai.modulebase.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseFragmentActivity<F extends BaseFragment> extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(getFragmentLayoutId(), createFragment(), getTag())
                .commit();
    }

    protected abstract String getTag();

    protected abstract F createFragment();

    protected abstract int getFragmentLayoutId();
}
