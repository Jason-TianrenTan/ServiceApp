package com.example.administrator.taskserviceproj;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import java.util.*;
import android.app.*;
import android.os.SystemClock;
import android.text.TextUtils;

public class TaskService extends Service {
    public TaskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String act = getForeground();
        if (!act.equals("com.example.administrator.taskserviceproj")) {
            Intent it = new Intent(this.getApplicationContext(), MainActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("flag","false");
            intent.putExtras(bundle);
            startActivity(it);
        }
        return START_STICKY;
    }
    private void restartApplication() {
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void onCreate() {
        super.onCreate();
        System.out.println("Service created!");
    }
    public String getForeground() {

        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        //获取到当前所有进程

        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();

        if (processInfoList ==null || processInfoList.isEmpty()) {

            return "";

        }

        //遍历进程列表，找到第一个前台进程

        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {

            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {

                return processInfo.processName;

            }

        }

        return "";

    }

}

