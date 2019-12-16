package com.ztk.demo.lockdemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import android.util.Log;


import com.ztk.demo.lockdemo.LockActivity;

import androidx.annotation.Nullable;

public class PlayService extends Service {
    ScreenBroadcastReceiver screenBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenBroadcastReceiver = new ScreenBroadcastReceiver();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        Log.e("onCreate: ", "filter");
        // Attach the broadcast listener
        registerReceiver(screenBroadcastReceiver, filter);

    }

    public class ScreenBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            handleCommandIntent(intent);
        }
    }

    private void handleCommandIntent(Intent intent) {
        final String action = intent.getAction();
        Log.e("action", action);
        if (Intent.ACTION_SCREEN_OFF.equals(action) ){
            Intent lockScreen = new Intent(this, LockActivity.class);
            lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.e("action", "start");
            startActivity(lockScreen);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenBroadcastReceiver);
    }
}
