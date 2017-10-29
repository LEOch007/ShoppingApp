package com.gy.linjliang.shoppingapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lenovo on 2017/10/26.
 */

public class myReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals("static")){
            Info info = (Info)intent.getSerializableExtra("info"); //获取商品信息类
            assert info != null; //debug
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),info.getImageindex());
            //获取系统的通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context); //实例化Notification构造器
            builder.setTicker("一条新信息")   //动态设置Notification的属性
                    .setContentTitle("新商品热卖")
                    .setContentText(info.getName()+"仅售"+info.getPrice()+"!")
                    .setLargeIcon(bm)
                    .setSmallIcon(info.getImageindex())
                    .setAutoCancel(true);
            //跳转至InfoActivity 商品信息界面
            Intent mintent = new Intent(context, InfoActivity.class); //显式调用
            mintent.putExtra("Info", info);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, 0);
            builder.setContentIntent(pendingIntent); //设置点击Intent
            Notification notification = builder.build(); //绑定Notification
            manager.notify(0,notification); //发送通知请求
        }

    }
}
