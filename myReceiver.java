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
    private Info info; //商品
    private static int num=0;//使用static静态变量 -> 多次初始化该类也只有一个num变量 不会重新初始化它
    @Override
    public void onReceive(Context context, Intent intent){
        /*  -- 接收静态广播 -- */
        if(intent.getAction().equals("static")){
            info = (Info)intent.getSerializableExtra("info"); //获取商品信息类
            assert info != null; //debug
            //跳转至InfoActivity 商品信息界面
            Intent mintent = new Intent(context, InfoActivity.class); //显式调用
            mintent.putExtra("Info", info);
            //打包Intent 最后一个参数flag = PendingIntent.FLAG_UPDATE_CURRENT表示更新之前PendingIntent中的Intent对象数据
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),info.getImageindex());
            //获取系统的通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context); //实例化Notification构造器
            builder.setContentTitle("新商品热卖") //动态设置Notification的属性
                    .setContentText(info.getName()+"仅售"+info.getPrice()+"!")
                    .setLargeIcon(bm)
                    .setSmallIcon(info.getImageindex())
                    .setTicker("一条新消息")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent); //设置点击Intent
            Notification notification = builder.build(); //绑定Notification
            manager.notify(0,notification); //发送单条通知请求
        }
        /*  -- 接收动态广播 -- */
        else if(intent.getAction().equals("dynamic")){
            num = num + 1;//改变notification的id号
            info = (Info)intent.getSerializableExtra("buy"); //获取商品信息类
            assert info != null; //debug
            //跳转至购物车界面
            Intent mintent = new Intent(context, MainActivity.class); //显式调用
            //打包Intent
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),info.getImageindex());
            //获取系统的通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context); //实例化Notification构造器
            builder.setContentTitle("马上下单") //动态设置Notification的属性
                    .setContentText(info.getName()+"已添加到购物车")
                    .setLargeIcon(bm)
                    .setSmallIcon(info.getImageindex())
                    .setTicker("一条新消息")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent); //设置点击Intent
            Notification notification = builder.build(); //绑定Notification
            manager.notify(num,notification); //多条通知请求
        }
    }
}
