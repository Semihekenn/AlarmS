package com.example.alarms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Connecter {
    private SQLiteDatabase database;
    private Activity activity;
    private ToastWhite white;
    private Alarm alarm;


    public Connecter(Activity activity) {
        this.activity=activity;
        white=new ToastWhite(activity);
        this.database =database=activity.openOrCreateDatabase("Alarms", Context.MODE_PRIVATE,null);
        String cmd="CREATE TABLE IF NOT EXISTS Alarm(id integer  PRIMARY KEY AUTOINCREMENT , " +
                "hour integer(2), minute integer(2), message VARCHAR, sound integer(2),visible integer(1))";
        database.execSQL(cmd);
        //cmd="INSERT INTO Alarm(id, hour, minute, message, sound, visible) VALUES (0, 1, 2, 'kayıt tamamlandı', 1, 1)";
        database.execSQL(cmd);
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void Makealarm(Alarm alarm){
        // toast.setView(R.layout.toast_white);
        try {
            String cmd="INSERT INTO Alarm(hour, minute, message, sound, visible) VALUES ("+alarm.getHour()+", " + ""+alarm.getMinute()+", '"+alarm.getMsg()+"', "+alarm.getSound()+", 1)";
            database.execSQL(cmd);
            white.setText("Alarm İs Set");
            white.Show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public List<Alarm> getList(){
        List<Alarm>alarms=new ArrayList<Alarm>();
        Cursor cursor=getDatabase().rawQuery("SELECT * FROM Alarm",null);
        cursor.moveToFirst();

        boolean boole=true;
        int flag;
        while (boole){
            try {

                Alarm alarm=new Alarm(cursor.getInt(cursor.getColumnIndex("id")),cursor.getInt(cursor.getColumnIndex("hour")),
                        cursor.getInt(cursor.getColumnIndex("minute")),cursor.getString(cursor.getColumnIndex("message")),
                        cursor.getInt(cursor.getColumnIndex("sound")),cursor.getInt(cursor.getColumnIndex("visible")));
                alarms.add(alarm);
                cursor.moveToNext();

            }catch (Exception e) {
                e.printStackTrace();
                boole = false;
            }
        }
        return alarms;
    }
    public void Update(Alarm alarm){
        //"hour integer(2), minute integer(2), message VARCHAR, sound integer(2),visible integer(1))"
        getDatabase().execSQL("UPDATE Alarm SET hour="+alarm.getHour()+", minute="+alarm.getMinute()+", message='"+alarm.getMsg()+"',sound="+alarm.getSound()+",visible="+alarm.getVisible()+" WHERE id="+alarm.getId()+" ");
        white.setText("ALARM İS REMAKE");
        white.Show();
    }
    public void Delete(int id){
        getDatabase().execSQL("DELETE FROM Alarm WHERE id="+id+" ");
        white.setText("ALARM İS DELETE");
        white.Show();
    }
    public int getLastId(){
        Cursor cursor=getDatabase().rawQuery("SELECT id FROM Alarm",null);
        cursor.moveToLast();
        return cursor.getInt(cursor.getColumnIndex("id"));
    }
    public Alarm getAlarm(int id){
        Cursor cursor=getDatabase().rawQuery("SELECT * FROM Alarm WHERE id="+id+" ",null);
        cursor.moveToFirst();
        Alarm alarm=new Alarm(cursor.getInt(cursor.getColumnIndex("id")),cursor.getInt(cursor.getColumnIndex("hour")),
                cursor.getInt(cursor.getColumnIndex("minute")),cursor.getString(cursor.getColumnIndex("message")),
                cursor.getInt(cursor.getColumnIndex("sound")),cursor.getInt(cursor.getColumnIndex("visible")));
        return alarm;
    }
    public Alarm getAlarmForTime(int hour,int minute){
        Cursor cursor=getDatabase().rawQuery("SELECT * FROM Alarm WHERE hour="+hour+" AND minute="+minute+" ",null);
        cursor.moveToFirst();
        return new Alarm(cursor.getInt(cursor.getColumnIndex("id")),cursor.getInt(cursor.getColumnIndex("hour")),
                cursor.getInt(cursor.getColumnIndex("minute")),cursor.getString(cursor.getColumnIndex("message")),
                cursor.getInt(cursor.getColumnIndex("sound")),cursor.getInt(cursor.getColumnIndex("visible")));
    }

}
