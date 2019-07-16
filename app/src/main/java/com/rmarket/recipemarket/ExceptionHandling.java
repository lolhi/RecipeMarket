package com.rmarket.recipemarket;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


public class ExceptionHandling {
    private Exception e;
    private Context context;
    private String msg;

    public ExceptionHandling(Exception e, Context context, String msg) {
        this.e = e;
        this.context = context;
        this.msg = msg;
    }

    public void StartingExceptionDialog(){
        e.printStackTrace();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).moveTaskToBack(true);
                        ((Activity)context).finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.setTitle("에러발생");
        alert.setIcon(R.mipmap.ic_launcher_recipemarket);
        alert.show();
    }
}