package com.example.alarms;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Alarm> alarms;
    private Activity activity;
    private Alarm alarm;
    int flag;
    TextView txtid;



    public CustomListAdapter(List<Alarm> alarms, Activity activity) {
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.alarms = alarms;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int i) {
        return alarms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alarms.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View viewRow;
        alarm=alarms.get(i);
        TextView txtHour;
        TextView txtMessage;
        int id;
        final LinearLayout layout;


        viewRow=layoutInflater.inflate(R.layout.row,null);
        txtid=viewRow.findViewById(R.id.txtid);
        txtHour=viewRow.findViewById(R.id.txthour);
        txtMessage=viewRow.findViewById(R.id.txtMessage);
        layout=viewRow.findViewById(R.id.lytRow);

        txtid.setText((alarm.getId())+"");
        txtHour.setText(alarm.getHour()+":"+alarm.getMinute());
        txtMessage.setText(alarm.getMsg());

        viewRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                txtid=viewRow.findViewById(R.id.txtid);
                ListAdapter adapter=new ListAdapter(activity);
                adapter.setAlarm(adapter.getAlarm(Integer.parseInt(txtid.getText().toString())));

                return true;
            }
        });
        if(alarm.getVisible()==0)
            layout.setBackgroundResource(R.color.black_light);
        viewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtid=viewRow.findViewById(R.id.txtid);
                ListAdapter adapter=new ListAdapter(activity);
                alarm=adapter.getAlarm(Integer.parseInt(txtid.getText().toString()));
                if(alarm.getVisible()==1) {
                    layout.setBackgroundResource(R.color.black_light);
                    alarm.setVisible(0);
                    adapter.updateAlarm(alarm);
                    adapter.cancelAlarm(alarm.getId());
                }
                else if(alarm.getVisible()==0){
                    layout.setBackgroundResource(R.color.black);
                    alarm.setVisible(1);
                    adapter.updateAlarm(alarm);
                    adapter.openAlarm(alarm);
                }
            }
        });

        /*
        final View viewRow;
        viewRow=layoutInflater.inflate(R.layout.row,null);
        alarm=alarms.get(i);
        TextView txtHour;
        TextView txtMessage;

        int id;
        txtid=viewRow.findViewById(R.id.txtid);
        txtHour=viewRow.findViewById(R.id.txthour);
        txtMessage=viewRow.findViewById(R.id.txtMessage);

        txtid.setText((alarm.getId())+"");
        txtHour.setText(alarm.getHour()+":"+alarm.getMinute());
        txtMessage.setText(alarm.getMsg());
        final LinearLayout layout=viewRow.findViewById(R.id.lytRow);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ListAdapter adapter=new ListAdapter(activity);
                adapter.setAlarm(adapter.getAlarm(Integer.valueOf(txtid.getText().toString())));
                return false;
            }
        });

        if(alarm.getVisible()==0)
            layout.setBackgroundResource(R.color.black_light);

        flag=alarms.get(i).getId();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView=viewRow.findViewById(R.id.txtid);
                ListAdapter listAdapter=new ListAdapter(activity);
                alarmFlg=listAdapter.getAlarm(Integer.parseInt(textView.getText().toString()));
                if(alarmFlg.getVisible()==1) {
                    layout.setBackgroundResource(R.color.black_light);
                    alarmFlg.setVisible(0);
                    listAdapter.updateAlarm(alarmFlg);
                    listAdapter.cancelAlarm(alarmFlg.getId());
                }
                else if(alarmFlg.getVisible()==0){
                    layout.setBackgroundResource(R.color.black);
                    alarmFlg.setVisible(1);
                    listAdapter.updateAlarm(alarmFlg);
                    listAdapter.openAlarm(alarmFlg);
                }





            }
        });
        */
        Button button=(Button) viewRow.findViewById(R.id.btndlgOk);
        return viewRow;
    }
}