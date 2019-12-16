package com.ztk.demo.lockdemo.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import com.ztk.demo.lockdemo.R;

import androidx.core.app.NotificationCompat;

/**
 * @author ztk
 */
public class NotificationUtil {
    private NotificationManager notificationManager;
    private Context mContext;

    private Notification notification;
    public static final int NOTIFICATION_ID = 10003;


    public NotificationUtil(Context mContext) {
        this.mContext = mContext;
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

    }

    /**
     * 展示通知栏
     */
    public void showNotification(){
        String id = "channel_demo";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, mContext.getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("通知栏");
            mChannel.enableLights(false);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mChannel);
            notification = new NotificationCompat.Builder(mContext, id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    .setCustomBigContentView(getContentView(true))
                    .setCustomContentView(getContentView(false))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setTicker("正在播放")
                    .setOngoing(true)
                    .setChannelId(mChannel.getId())
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new NotificationCompat.Builder(mContext, id)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    .setCustomBigContentView(getContentView(true))
                    .setCustomContentView(getContentView(false))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setTicker("正在播放")
                    .setOngoing(true)
                    .build();
        } else {
            notification = new NotificationCompat.Builder(mContext, id)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    .setContent(getContentView(false))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setTicker("正在播放")
                    .setOngoing(true)
                    .build();
        }


        notificationManager.notify(NOTIFICATION_ID, notification);
    }
    private PendingIntent getDefaultIntent(int flags) {
        return PendingIntent.getActivity(mContext, 1, new Intent(), flags);
    }

    /**
     * 获取自定义通知栏view
     * @param showBigView
     * @return
     */
    private RemoteViews getContentView(boolean showBigView) {
        RemoteViews mRemoteViews = new RemoteViews(mContext.getPackageName(), showBigView ? R.layout.view_notify_big : R.layout.view_notify_small);
        return mRemoteViews;
    }

}
