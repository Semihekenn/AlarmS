package com.example.alarms;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmNotification {
            Activity activity;
    public    AlarmNotification(Activity activity ) {
        this.activity = activity;

        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 1, intent, 0);

        NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder( activity.getApplicationContext(), "YOUR_CHANNEL_ID");
        ListAdapter listAdapter=new ListAdapter(activity);
        Alarm alarm= listAdapter.getAlarmForTime();
        builder.setContentTitle(alarm.getHour()+":"+alarm.getMinute()+"");
        builder.setContentText(alarm.getMsg());
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setSmallIcon(R.drawable.notification);
        builder.setTicker("AlarmS is call");
        builder.setAutoCancel(true);
        //builder.addAction(R.drawable.alarm, "Stop", pendingIntent);
        builder.setContentIntent(pendingIntent);
        manager.notify(0,builder.build());


        //Toast.makeText(activity,"dsfsdjsddfs",Toast.LENGTH_LONG).show();
    }
}
