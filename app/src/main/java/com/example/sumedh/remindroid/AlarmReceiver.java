package com.example.sumedh.remindroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationHelper notificationHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        // When our Alarm time is triggered , this method will be executed
        // We're invoking a service in this method which shows Notification to the User
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification(intent.getStringExtra("title"),intent.getStringExtra("desc"));
            notificationHelper.getManager().notify(1,nb.build());
        }
        else
        {
            Intent i = new Intent(context, NotificationService.class);
            i.putExtra("title",intent.getStringExtra("title"));
            i.putExtra("desc",intent.getStringExtra("desc"));
            context.startService(i);
        }
    }
}
