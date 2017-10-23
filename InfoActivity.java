package com.gy.linjliang.shoppingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/10/23.
 */

public class InfoActivity extends Activity {
    private boolean tag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_info);

        Info p = (Info) getIntent().getSerializableExtra("Info");
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Top);

        //返回键的点击事件
        Button back = (Button) findViewById(R.id.back); //返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //添加商品图片
        ImageView ima = (ImageView) findViewById(R.id.good_image);
        ima.setImageResource(p.getImageindex());

        //添加商品各textview的内容
        TextView name = (TextView) findViewById(R.id.Name); //名字
        name.setText(p.getName());
        TextView price = (TextView) findViewById(R.id.price); //价格
        price.setText(p.getPrice());
        TextView type = (TextView) findViewById(R.id.type); //类型
        type.setText(p.getType());
        TextView infomation = (TextView) findViewById(R.id.infomation); //信息
        infomation.setText(p.getInformation());

        //给listview添加内容
        String[] operations = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息",""};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.more, operations);
        ListView lView = (ListView) findViewById(R.id.lview);
        lView.setAdapter(arrayAdapter);

        /*  星星的切换 */
        final Button star = (Button) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tag) {
                    star.setBackground(getDrawable(R.mipmap.full_star));
                    tag = true;
                } else {
                    star.setBackground(getDrawable(R.mipmap.empty_star));
                    tag = false;
                }
            }
        });
    }
}
