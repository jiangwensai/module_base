package com.example.jiangwensai.modulebase.base.inter;

public interface IPresenter<V extends IView> {
    void attachView(V view);

    void detachView();
}
