package alarmnews

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

import com.semiheken.alarms.R

class AlarmNotification(private val activity: Activity) {
    init {
        val intent = Intent(activity.applicationContext,)
        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 1, intent, 0)
        val manager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DISCRIPTION"
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(activity.applicationContext, "YOUR_CHANNEL_ID")
        val listAdapter = ListAdapter(activity)
        val alarm = listAdapter.alarmForTime
        builder.setContentTitle(alarm.hour.toString() + ":" + alarm.minute + "")
        builder.setContentText(alarm.msg)
        builder.priority = Notification.PRIORITY_MAX
        builder.setSmallIcon(R.drawable.notification)
        builder.setTicker("AlarmS is call")
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        manager.notify(0, builder.build())


    }
}