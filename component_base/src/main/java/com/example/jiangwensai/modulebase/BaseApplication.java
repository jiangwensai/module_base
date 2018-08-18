package com.example.jiangwensai.modulebase;

import android.app.Application;

/**
 * Created by jiangwensai on 18/8/11.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashUncaughtHandler.getInstance().init(this);
    }
}
