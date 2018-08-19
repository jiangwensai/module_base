package com.example.jiangwensai.modulebase.base;

import android.content.Context;

import com.example.jiangwensai.modulebase.base.inter.IPresenter;
import com.example.jiangwensai.modulebase.base.inter.IView;

public abstract class MVPBaseFragment<V extends IView, P extends IPresenter<V>> extends BaseFragment implements IView {
    protected P mPresenter;

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    protected abstract P createPresenter();
}
