package com.example.alarms;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarCall {
    private Calendar calendar;
    private Activity activity;
    private Calendar calset;

    private AlarmDialog dialog;
    private String s="56";
    private AlarmManager alarmManager;

    public CalendarCall(Activity activity){
    this.activity=activity;
    }


    String hour;
    public void setCalendar(AlarmDialog dialog){
        calendar= Calendar.getInstance();

        TimePickerDialog timePickerDialog=new TimePickerDialog(activity, onTimeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.setTitle("Alarm Ayarı");
        timePickerDialog.show();
        this.dialog=dialog;




    }
    private TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

            Calendar calnow=Calendar.getInstance();
            calset= (Calendar) calnow.clone();

            calset.set(Calendar.HOUR_OF_DAY,i);
            calset.set(Calendar.MINUTE,i1);
            calset.set(Calendar.SECOND,0);
            calset.set(Calendar.MILLISECOND,0);


            if(calset.compareTo(calnow)<=0)

                calset.add(Calendar.DATE,1);
            hour= calset.getTime().toString();
            dialog.setTxt(hour);
            //Start();
        }
    };
    public void startAlarm(Calendar calendar,int id){
         final int islem=id;

        Intent intent=new Intent(activity.getBaseContext(),MainAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(activity.getBaseContext(),islem,intent,0);
        alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntent);

    }



    public  void Start(int id){
        startAlarm(calset,id);
    }

    public  void setAlarm(Alarm alarm){
        Calendar calnow=Calendar.getInstance();
        calset= (Calendar) calnow.clone();

        calset.set(Calendar.HOUR_OF_DAY,alarm.getHour());
        calset.set(Calendar.MINUTE,alarm.getMinute());
        calset.set(Calendar.SECOND,0);
        calset.set(Calendar.MILLISECOND,0);


        if(calset.compareTo(calnow)<=0)

            calset.add(Calendar.DATE,1);
        startAlarm(calset,alarm.getId());
    }

}
