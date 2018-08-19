package com.example.jiangwensai.modulebase.base;

public abstract class BaseFragmentActivity<F extends BaseFragment> extends BaseActivity {
    @Override
    protected void initView() {
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
