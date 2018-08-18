package com.example.jiangwensai.modulebase.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.jiangwensai.modulebase.base.inter.IPresenter;
import com.example.jiangwensai.modulebase.base.inter.IView;

public abstract class MVPBaseActivity<V extends IView, P extends IPresenter<V>> extends BaseActivity implements IView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    protected abstract P createPresenter();
}
