package com.example.alarms;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmDialog {
    //Tanımlamalar
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View view;
    private Activity activity;
    private TextView txtHour;
    private TextView txtMinute;
    private EditText txtmsg;
    private CalendarCall call;
    private ListAdapter adapter;
    public static final int DIALOG_ADD=0;
    public static final int DIALOG_DEL_UP=1;
    public static final int DIALOG_POP=2;
    private Alarm alarm;
    AlertDialog alertDialog;


    public AlarmDialog(final Activity activity,int command) {

        //Başlangıç eşitlemeleri
        this.activity = activity;
        dialog = new AlertDialog.Builder(this.activity);
        dialog.setTitle("ALARM İS CALL");

        final AlarmDialog alarmDialog=this;
        adapter=new ListAdapter(activity);

        //dialog oluşturma
        inflater=activity.getLayoutInflater();
        view =inflater.inflate(R.layout.alert_dialog,null);
        dialog.setView(view);
        call=new CalendarCall(activity);

        //atamalar
        txtHour=view.findViewById(R.id.txtAlertHour);
        txtMinute=view.findViewById(R.id.txtAlertMinute);
        txtmsg=view.findViewById(R.id.txtDlgMessage);


        if(command==this.DIALOG_ADD) {
            DialogInterface dialogInterface=dialog.create();
            view.findViewById(R.id.btndlgOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Alarm alarm = new Alarm(0, Integer.valueOf(txtHour.getText().toString()),
                            Integer.valueOf(txtMinute.getText().toString()), txtmsg.getText().toString(), 1, 1);
                    adapter.addAlarm(alarm);
                    dialog.setTitle("ADD ALARM");
                    ListView listView = activity.findViewById(R.id.viewList);
                    adapter.Reload(listView);
                    call.Start(adapter.getLastId());
                    Cancel();

                    //Toast.makeText(activity,"ok",Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(command==this.DIALOG_DEL_UP){
            DialogInterface dialogInterface=dialog.create();
            view.findViewById(R.id.btndlgCancel).setVisibility(View.VISIBLE);

            Button button=view.findViewById(R.id.btndlgOk);
            button.setText("UPDATE");
            dialog.setTitle("REMAKE ALARM");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Alarm alarm=new Alarm(alarmDialog.alarm.getId(),Integer.valueOf(txtHour.getText().toString())
                            ,Integer.valueOf(txtMinute.getText().toString()),txtmsg.getText().toString(),alarmDialog.alarm.getSound()
                            ,alarmDialog.alarm.getVisible());
                    adapter.updateAlarm(alarm);
                    ListView listView=activity.findViewById(R.id.viewList);
                    adapter.Reload(listView);
                    call.Start(adapter.getLastId());
                    Cancel();
                    //alarm güncelleme yapılacak

                }
            });

        }
        if(command==this.DIALOG_POP){
            dialog.setCancelable(false);
            DialogInterface dialogInterface=dialog.create();
            view.findViewById(R.id.btndlgCancel).setVisibility(View.GONE);
            view.findViewById(R.id.btndlgOk).setVisibility(View.VISIBLE);
            view.findViewById(R.id.lytHour).setVisibility(View.GONE);
            view.findViewById(R.id.txtDlgMessage).setVisibility(View.INVISIBLE);
            Button button=view.findViewById(R.id.btndlgOk);
            button.setText("CLOSE ALARM");
            //dialog.setTitle("ALARM İS CALL");
            view.findViewById(R.id.btndlgOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainAlarm.Stop();
                    //Toast.makeText(activity,"dfkfdsofk",Toast.LENGTH_LONG);
                    MainAlarm.Control=false;
                    Cancel();

                }
            });
        }

        view.findViewById(R.id.btndlgCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete kısmı
                adapter.deleteAlarm(alarm.getId());
                ListView listView=activity.findViewById(R.id.viewList);
                adapter.Reload(listView);
                Cancel();

                //Toast.makeText(activity,"cancel",Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.lytHour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Calendar çağrıldı
                call.setCalendar(alarmDialog);
            }
        });
    }

    public void Show(){
        alertDialog =this.getDialog();
        alertDialog.show();
    }
    public void setDialogText(Alarm alarm){
        txtmsg.setText(alarm.getMsg());
        txtHour.setText(alarm.getHour()+"");
        txtMinute.setText(alarm.getMinute()+"");
        this.alarm =alarm;
    }
    public void Cancel(){
        alertDialog.dismiss();
    }
    public void setTxt(String hour){
        view.findViewById(R.id.btndlgOk).setVisibility(View.VISIBLE);
        txtHour.setText(hour.substring(11,13));
        String a=txtHour.getText().toString();


        txtHour.setText(String.valueOf(Integer.valueOf(a)));
        txtMinute.setText(hour.substring(14,16));


        //Toast.makeText(activity,hour,Toast.LENGTH_LONG).show();
    }

    public AlertDialog getDialog(){
        alertDialog =dialog.create();
        return alertDialog;
    }
    public AlertDialog closeDialog(){
        alertDialog.dismiss();
        return alertDialog;
    }
}
