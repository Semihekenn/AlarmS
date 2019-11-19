package com.example.alarms;

import android.app.Activity;

public class Alarm {
    private int id;
    private int hour;
    private int minute;
    private String msg;
    private int sound;
    private int visible;


    public Alarm(int id, int hour, int minute, String msg, int sound, int visible) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.msg = msg;
        this.sound=sound;
        this.visible = visible;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int Sound) {
        this.sound = sound;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

}
