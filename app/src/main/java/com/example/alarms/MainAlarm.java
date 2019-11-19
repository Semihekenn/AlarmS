package com.example.alarms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class MainAlarm extends BroadcastReceiver {
    private static Ringtone ringtone;
    Activity activity;
    static boolean isopen=false;

    public static boolean Control;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, MainActivity.class);
//Clear all activities and start new task
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//tüm activityleri kapatır ve belirtilen activity i açar
        context.startActivity(intent2);

       // context.startActivity(new Intent(context, MainActivity.class));

        ToastWhite toastWhite;
        //activity=context.getApplicationContext()
        toastWhite=new ToastWhite(context);
        toastWhite.setText("ALARM İS CALL");
        //toastWhite.setImage();
        toastWhite.Show();
        //Toast.makeText(context,"laylaylaaay",Toast.LENGTH_LONG).show();
        //ListAdapter listAdapter=new ListAdapter(activity);
        //listAdapter.Reload((ListView) activity.findViewById(R.id.viewList));
        ///Intent intent1 = new Intent(activity.getApplicationContext(), MainActivity.class);
        //activity.startActivity(intent1);
        //Buraya custom alert dialog tasarımı kurucaksın
        Uri  Musicalarm= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(Musicalarm==null)
            Musicalarm=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ringtone=RingtoneManager.getRingtone(context,Musicalarm);
        ringtone.play();
        //AlarmDialog dialog=new AlarmDialog(activity,AlarmDialog.DIALOG_POP);
        //dialog.Show();
        //activity.finish();
        //AlarmNotification alarmNotification=new AlarmNotification(activity);
        Control=true;
    }
    public static void Stop(){
        ringtone.stop();

    }


}
