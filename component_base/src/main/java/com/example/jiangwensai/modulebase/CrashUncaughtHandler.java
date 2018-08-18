package com.example.jiangwensai.modulebase;

import android.content.Context;

public class CrashUncaughtHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashUncaughtHandler.class.getSimpleName();
    private static CrashUncaughtHandler INSTANCE = new CrashUncaughtHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    private CrashUncaughtHandler() {
    }


    public static CrashUncaughtHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
        handleException(ex);
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private void handleException(Throwable ex) {
        if (ex == null) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        }.start();
    }
}
