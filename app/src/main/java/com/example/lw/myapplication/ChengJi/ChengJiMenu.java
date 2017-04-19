package com.example.lw.myapplication.ChengJi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lw.myapplication.R;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lw on 2017/4/13.
 */

public class ChengJiMenu extends Activity{
    private String[] xueqi=new String[]{"1","2"};
    private String[] xuenian;
    private List<NameValuePair> postmap=new ArrayList<NameValuePair>();
    private static Handler handler;
    private HorizontalScrollView horizontalScrollView;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chengji_layout);
        init();
        listView= (ListView) findViewById(R.id.listView2);
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.chengjihv);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj instanceof ArrayList){
                    ArrayList<ArrayList<String>> arrayList= (ArrayList<ArrayList<String>>) msg.obj;
                    if (arrayList.size()<=1)
                    {
                        Toast.makeText(ChengJiMenu.this,"骚年你选择的日期不对啊",Toast.LENGTH_SHORT);
                    }
                    new ChengJiText(ChengJiMenu.this,listView,horizontalScrollView,arrayList).run();
                }
            }
        };
    }

    private void init() {
        String temp;
        Calendar calendar= Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        xuenian=new String[11];
        for (int i=year-10;i<=year;i++)
        {
            xuenian[year-i]=String.valueOf(i);
        }
        Button buttonxn= (Button) findViewById(R.id.chnegjixn);
        Button button= (Button) findViewById(R.id.chengjibt);
        button.setOnClickListener(new RadioClickListener("学期",xueqi,ChengJiMenu.this));
        buttonxn.setOnClickListener(new RadioClickListener("学年",xuenian,ChengJiMenu.this));
    }

    public static Handler getHandler(){
        return handler;
    }
}
