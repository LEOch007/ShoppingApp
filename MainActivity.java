package com.gy.linjliang.shoppingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private MyAdapter myAdapter;
    private List<Info> Infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //加载主布局

        //添加商品类的内容
        Infos = new ArrayList<Info>() {{
            add(new Info("Enchated Forest", "5.00", "作者", "Johanna Basford",R.mipmap.enchatedforest));
            add(new Info("Arla Milk", "59.00", "产地", "德国",R.mipmap.arla));
            add(new Info("Devondale Milk", "79.00", "产地", "澳大利亚",R.mipmap.devondale));
            add(new Info("Kindle Oasis", "2399.00", "版本", "8GB",R.mipmap.kindle));
            add(new Info("waitrose 早餐麦片", "179.00", "重量", "2Kg",R.mipmap.waitrose));
            add(new Info("Mcvitie's 饼干", "14.90", "产地", "英国",R.mipmap.mcvitie));
            add(new Info("Ferrero Rocher", "132.59", "重量", "300g",R.mipmap.ferrero));
            add(new Info("Maltesers", "141.43", "重量", "118g",R.mipmap.maltesers));
            add(new Info("Lindt", "139.43", "重量", "249g",R.mipmap.lindt));
            add(new Info("Borggreve", "28.90", "重量", "640g",R.mipmap.borggreve));
        }};

        mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this)); //垂直布局
        myAdapter = new MyAdapter(this, Infos);
        mRecycleView.setAdapter(myAdapter);//设置适配器

        myAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener()
        {
            //点击事件
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class); //显式调用
                Info temp = Infos.get(position); //第i个Info商品信息
                intent.putExtra("Info", temp);
                startActivity(intent);
            }
            //长按事件
            @Override
            public boolean onItemLongClick(View view, final int position) {
                //简单对话框的设计
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setTitle("移除商品");
                message.setMessage("从购物车中移除" + Infos.get(position).getName()+"？");
                message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Infos.remove(position);
                        myAdapter.notifyDataSetChanged();
                    }
                });
                message.create().show();
                return true;
            }

        });

    }

}
