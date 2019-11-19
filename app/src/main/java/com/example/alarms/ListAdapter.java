package com.example.alarms;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class  ListAdapter {
    private Connecter connecter;


    private Activity activity;

    public ListAdapter(Activity activity){
        this.activity=activity;
        connecter=new Connecter(activity);

    }

    public List<Alarm> getAlarmList(){
       return connecter.getList();
    }
    public void addAlarm(Alarm alarm){

        connecter.Makealarm(alarm);
    }
    public void Reload(ListView listView){

        listView.setAdapter(null);


        ListAdapter listAdapter = new ListAdapter(activity);
        List<Alarm> alarms = listAdapter.getAlarmList();
        CustomListAdapter customListAdapter = new CustomListAdapter(alarms, activity);
        listView.setAdapter(customListAdapter);

        if(MainAlarm.Control==true){
            AlarmDialog alarmDialog =new AlarmDialog(activity,AlarmDialog.DIALOG_POP);
            alarmDialog.Show();
            AlarmNotification alarmNotification=new AlarmNotification(activity);

        }

    }/*
    public Alarm getAlarm(int id){
       Cursor cursor= connecter.getDatabase().rawQuery("Select * From Alarm WHERE id="+id+" ",null);
       cursor.moveToFirst();
        Alarm alarm=new Alarm(cursor.getInt(cursor.getColumnIndex("id")),cursor.getInt(cursor.getColumnIndex("hour")),
                cursor.getInt(cursor.getColumnIndex("minute")),cursor.getString(cursor.getColumnIndex("message")),
                cursor.getInt(cursor.getColumnIndex("sound")),cursor.getInt(cursor.getColumnIndex("visible")));;
        return alarm;
    }*/

    public  void setAlarm(Alarm alarm){
        AlarmDialog alarmDialog=new AlarmDialog(activity,AlarmDialog.DIALOG_DEL_UP);
        alarmDialog.setDialogText(alarm);
        //Toast.makeText(activity,alarm.getMsg(),Toast.LENGTH_LONG).show();
        alarmDialog.Show();

    }
    public void updateAlarm(Alarm alarm){
        connecter.Update(alarm);

    }

    public  void  deleteAlarm(int id){
        final int islem=id;
       // Toast.makeText(activity,calls.get(0).s,Toast.LENGTH_LONG).show();
        Intent intent=new Intent(activity.getBaseContext(),MainAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(activity.getBaseContext(),islem,intent,0);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        connecter.Delete(id);
    }
    public int getLastId(){
        return connecter.getLastId();
    }
    public  Alarm getAlarm(int id){
        return connecter.getAlarm(id);
    }
    public void cancelAlarm(int id){
        int islem=id;
        Intent intent=new Intent(activity.getBaseContext(),MainAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(activity.getBaseContext(),islem,intent,0);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
    public void openAlarm(Alarm alarm){
        CalendarCall calendarCall=new CalendarCall(activity);
        calendarCall.setAlarm(alarm);
    }
    public Alarm getAlarmForTime(){
        String string= Calendar.getInstance().getTime().toString();
        return connecter.getAlarmForTime(Integer.valueOf(string.substring(11,13)),Integer.valueOf(string.substring(14,16)));
    }







}
