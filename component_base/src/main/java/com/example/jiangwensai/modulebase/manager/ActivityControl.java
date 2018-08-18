package com.example.jiangwensai.modulebase.manager;

import android.app.Activity;

import java.util.Stack;

public class ActivityControl {
    private static ActivityControl instance;
    private Stack<Activity> allActivities;

    private ActivityControl() {
    }

    public static ActivityControl getInstance() {
        if (instance == null) {
            synchronized (ActivityControl.class) {
                if (instance == null) {
                    instance = new ActivityControl();
                }
            }
        }
        return instance;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new Stack<>();
        }
        allActivities.push(act);
    }

    public void removeTopActivity(Activity activity) {
        if (allActivities == null) {
            return;
        }
        if (activity != allActivities.peek()) {
            return;
        }
        allActivities.pop();
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void finishAll() {
        if (allActivities != null) {
            for (Activity act : allActivities) {
                act.finish();
            }
        }
    }

    public void finishAllExcept(Activity activity) {
        if (allActivities != null) {
            for (Activity act : allActivities) {
                if (act != activity) {
                    act.finish();
                }
            }
        }
    }

    public void finishAllUp(Activity activity) {
        if (allActivities == null) {
            return;
        }
        for (Activity act : allActivities) {
            if (act == activity) {
                return;
            }
            act.finish();
        }
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAll();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
