package com.example.alarms;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Calendar calendar;
    private final static int islem = 1;
    private CalendarCall calendarCall;
    static Activity activity;
    private ListView listView;
    private ListAdapter listAdapter;
    boolean flag;
    //static boolean Control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        MainAlarm.isopen=true;



        //REACT js kütüphanesi
        activity=this;
        listAdapter=new ListAdapter(this);
        listView = findViewById(R.id.viewList);
        listAdapter.Reload(listView);
        Button button =findViewById(R.id.btnMenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmDialog alarmDialog=new AlarmDialog(activity,AlarmDialog.DIALOG_ADD);
                alarmDialog.Show();
                //yeni activity açılıp ekleme işlemi yapılıcak
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for(int i=0;i<listAdapter.getAlarmList().size();i++){
                    Alarm alarmFlg=listAdapter.getAlarmList().get(i);
                    if(alarmFlg.getVisible()==1) {

                        alarmFlg.setVisible(0);
                        listAdapter.updateAlarm(alarmFlg);
                        listAdapter.cancelAlarm(alarmFlg.getId());
                    }
                    else if(alarmFlg.getVisible()==0){

                        alarmFlg.setVisible(1);
                        listAdapter.updateAlarm(alarmFlg);
                        listAdapter.openAlarm(alarmFlg);
                    }
                }
                listAdapter.Reload(listView);
                return true;//false hem onclick hem onlongclick çalışır
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainAlarm.isopen=false;
    }
}
