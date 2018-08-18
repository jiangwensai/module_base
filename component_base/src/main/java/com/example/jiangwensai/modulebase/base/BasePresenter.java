package com.example.jiangwensai.modulebase.base;

import android.content.Context;

import com.example.jiangwensai.modulebase.base.inter.IModel;
import com.example.jiangwensai.modulebase.base.inter.IPresenter;
import com.example.jiangwensai.modulebase.base.inter.IView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {
    private V mView;
    private M mModel;
    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(V view) {
        this.mView = view;
        if (mModel == null) {
            mModel = createModel();
        }
    }

    public void detachView() {
        clearPool();
        mModel = null;
        mView = null;
    }


    public Context getContext() {
        return mView.getContext();
    }

    public V getView() {
        return mView;
    }

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void clearPool() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public M getModel() {
        return mModel;
    }

    protected abstract M createModel();
}
