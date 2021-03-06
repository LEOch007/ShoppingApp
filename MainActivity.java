package com.gy.linjliang.shoppingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private MyAdapter myAdapter;
    private ListView mListView;
    private SimpleAdapter simpleAdapter;
    private List<Info> Infos; //所有商品Info -> RecyclerView
    private List<Info> shopInfos; //购物车中的商品Info -> ListView
    private List<Map<String, Object>> data;
    private FloatingActionButton mfab;
    private boolean tag=false; //点击浮动按钮变化
    //购物车界面的UI控件
    private TextView tubiao;
    private TextView gowuche;
    private TextView jiage;
    private View selfline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //加载主布局

        //添加商品类的内容
        Infos = new ArrayList<Info>() {{
            add(new Info("Enchated Forest", "¥ 5.00", "作者", "Johanna Basford",R.mipmap.enchatedforest));
            add(new Info("Arla Milk", "¥ 59.00", "产地", "德国",R.mipmap.arla));
            add(new Info("Devondale Milk", "¥ 79.00", "产地", "澳大利亚",R.mipmap.devondale));
            add(new Info("Kindle Oasis", "¥ 2399.00", "版本", "8GB",R.mipmap.kindle));
            add(new Info("waitrose 早餐麦片", "¥ 179.00", "重量", "2Kg",R.mipmap.waitrose));
            add(new Info("Mcvitie's 饼干", "¥ 14.90", "产地", "英国",R.mipmap.mcvitie));
            add(new Info("Ferrero Rocher", "¥ 132.59", "重量", "300g",R.mipmap.ferrero));
            add(new Info("Maltesers", "¥ 141.43", "重量", "118g",R.mipmap.maltesers));
            add(new Info("Lindt", "¥ 139.43", "重量", "249g",R.mipmap.lindt));
            add(new Info("Borggreve", "¥ 28.90", "重量", "640g",R.mipmap.borggreve));
        }};

        //前置于RecyclerView之前可加速开启APP广播速度
        Random random = new Random();//产生随机数
        Info rand_info = Infos.get( random.nextInt(Infos.size()) );
        /*     ------   发广播  -------   */
        Intent mintent = new Intent("static");
        mintent.putExtra("info",rand_info);
        sendBroadcast(mintent);

        /*                      ---  RecyclerView ---                */
        mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this)); //垂直布局
        myAdapter = new MyAdapter(this, Infos);
//        mRecycleView.setAdapter(myAdapter);//设置适配器
        /*  网上的库 添加动画  */
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(myAdapter);
        animationAdapter.setDuration(1000);
        mRecycleView.setAdapter(animationAdapter);
        mRecycleView.setItemAnimator(new OvershootInLeftAnimator());

        myAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener()
        {
            //点击事件
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class); //显式调用
                Info temp = Infos.get(position); //第i个Info商品信息
                intent.putExtra("Info", temp);
                startActivityForResult(intent,1);
            }
            //长按事件
            @Override
            public boolean onItemLongClick(View view, final int position) {
                //简单对话框的设计
                Toast.makeText(MainActivity.this, Infos.get(position).getName()+" 商品被删除",Toast.LENGTH_SHORT).show();
                Infos.remove(position);
                myAdapter.notifyDataSetChanged();
                return true;
            }

        });
        /*        ------------------------------------                */

        /*                       --- ListView ---                    */
        mListView = (ListView)findViewById(R.id.list_View);
        shopInfos = new ArrayList<Info>(); //购物车的List
        data = new ArrayList<>(); //以键值对形式加入List data中
        //定义SimpleAdapter
        simpleAdapter = new SimpleAdapter(this, data, R.layout.shopcart,
                new String[]{"cycle", "name","value"}, new int[]{R.id.cycle, R.id.name,R.id.value});
        mListView.setAdapter(simpleAdapter); //为ListView设置adapter
         /*  ListView长按事件  */
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
                //简单对话框的设计
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setTitle("移除商品");
                message.setMessage("从购物车中移除" + shopInfos.get(position).getName()+"？");
                message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shopInfos.remove(position);
                        data.remove(position);
                        simpleAdapter.notifyDataSetChanged();
                    }
                });
                message.create().show();
                return true;
            }
        });
        /*  ListView单击事件 */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class); //显式调用
                Info temp = shopInfos.get(i); //第i个ShopInfo商品信息
                intent.putExtra("Info", temp);
                startActivityForResult(intent,1);
            }
        });
        /*        --------------------------------------                */

        //浮动按钮
        mfab = (FloatingActionButton)findViewById(R.id.fab);
        tubiao = (TextView)findViewById(R.id.tubiao);
        gowuche = (TextView)findViewById(R.id.gowuche);
        jiage = (TextView)findViewById(R.id.jiage);
        selfline = findViewById(R.id.selfline);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tag) {
                    mfab.setImageResource(R.mipmap.mainpage); //切换图像
                    mListView.setVisibility(View.VISIBLE); //设置ListView可见 RecylerView不可见
                    tubiao.setVisibility(View.VISIBLE);
                    gowuche.setVisibility(View.VISIBLE);
                    jiage.setVisibility(View.VISIBLE);
                    selfline.setVisibility(View.VISIBLE);
                    mRecycleView.setVisibility(View.GONE);
                    tag = true;
                }
                else{
                    mfab.setImageResource(R.mipmap.shoplist); //切换图像
                    mListView.setVisibility(View.GONE); //设置ListView不可见 RecylerView可见
                    tubiao.setVisibility(View.GONE);
                    gowuche.setVisibility(View.GONE);
                    jiage.setVisibility(View.GONE);
                    selfline.setVisibility(View.GONE);
                    mRecycleView.setVisibility(View.VISIBLE);
                    tag = false;
                }
            }
        });

        /*     ------   注册订阅者  -------   */
        EventBus.getDefault().register(this); //订阅消息

    }
    //取消订阅
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //订阅方法
    @Subscribe(threadMode = ThreadMode.MAIN) //选择线程模式
    public void onMessageEvent(Info shop_good){
                shopInfos.add(shop_good);
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("cycle",shop_good.getcycle());
                temp.put("name", shop_good.getName());
                temp.put("value", shop_good.getPrice());
                data.add(temp);   //加入数据至购物车
                simpleAdapter.notifyDataSetChanged();
    }
    /*   -----   接收新intent消息  -----  */
    //在同一MainActivity实例里 通过Manifest.xml去设置activity的模式
    @Override
    protected void onNewIntent(Intent intent){
            mfab.setImageResource(R.mipmap.mainpage); //切换图像
            mListView.setVisibility(View.VISIBLE); //设置ListView可见 RecylerView不可见
            tubiao.setVisibility(View.VISIBLE);
            gowuche.setVisibility(View.VISIBLE);
            jiage.setVisibility(View.VISIBLE);
            selfline.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
            tag = true; //相当于已点击
    }
}
