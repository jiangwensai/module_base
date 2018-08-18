package com.example.jiangwensai.modulebase.base;

import android.app.Application;

import com.example.jiangwensai.modulebase.CrashUncaughtHandler;

/**
 * Created by jiangwensai on 18/8/11.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashUncaughtHandler.getInstance().init(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
