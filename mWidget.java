package com.gy.linjliang.shoppingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {
    //启动APP的MainActivity
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //实例化RemoteView
        RemoteViews updateviews = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        //消息
        Intent I = new Intent(context,MainActivity.class);
        PendingIntent PI = PendingIntent.getActivity(context,0,I,PendingIntent.FLAG_UPDATE_CURRENT);
        updateviews.setOnClickPendingIntent(R.id.widget,PI);//设置点击事件
        //组件
        ComponentName me = new ComponentName(context,mWidget.class);
        appWidgetManager.updateAppWidget(me,updateviews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);//启动APP的MainActivity
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //收APP的静态广播
    @Override
    public void onReceive(Context context,Intent intent){
        super.onReceive(context,intent);
        if(intent.getAction().equals("static")){
            Info info = (Info)intent.getSerializableExtra("info"); //获取商品信息类
            assert info != null; //debug
            ///跳转至InfoActivity 商品信息界面
            Intent mintent = new Intent(context, InfoActivity.class); //显式调用
            mintent.putExtra("Info", info);
            //打包Intent 最后一个参数flag = PendingIntent.FLAG_UPDATE_CURRENT表示更新之前PendingIntent中的Intent对象数据
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, PendingIntent.FLAG_UPDATE_CURRENT);
            //实例化RemoteView
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.m_widget);
            //设置RemoteView的属性
            remoteViews.setImageViewResource(R.id.widget_image, info.getImageindex());//widget显示图片
            remoteViews.setTextViewText(R.id.widget_text, info.getName()+"仅售"+info.getPrice()+"!");//widget显示文字
            remoteViews.setOnClickPendingIntent(R.id.widget,pendingIntent);//widget点击事件
            ComponentName me = new ComponentName(context, mWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context); //获得类的实例
            appWidgetManager.updateAppWidget(me, remoteViews);
        }
    }
}

