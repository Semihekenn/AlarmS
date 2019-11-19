package com.example.alarms;


import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToastWhite {
    private Toast toast;
    private LayoutInflater inflater;
    private TextView txt;
    private View view;


    public ToastWhite(Context Main) {

        LayoutInflater inflater = (LayoutInflater) Main.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        toast=new Toast(Main);
        view= inflater.inflate(R.layout.toast_white,null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,-700);
    }
    public void Show(){
        toast.show();
    }
    public void setText(String str){
        txt=view.findViewById(R.id.textToast);
        txt.setText(str);
    }
    public void setImage(){
        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.alarm);
    }
}
