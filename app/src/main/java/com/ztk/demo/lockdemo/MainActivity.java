package com.ztk.demo.lockdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ztk.demo.lockdemo.notification.NotificationUtil;
import com.ztk.demo.lockdemo.service.PlayService;

/**
 * @author ztk
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
        //开启通知栏
        NotificationUtil mNotificationUtils = new NotificationUtil(this);
        mNotificationUtils.showNotification();

    }
}
