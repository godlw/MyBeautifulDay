package com.example.lw.myapplication.XuanKe;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lw on 2017/3/22.
 */

public class Test extends Thread {

    private Context context;
    private ListView listView;
    private HorizontalScrollView horizontalScrollView;
    private ArrayList<ArrayList<String>> lists;
    private ArrayList<String> list;
    private Handler handler;
    public Test(Context context, ListView listView, HorizontalScrollView horizontalScrollView, ArrayList<String> list, Handler handler){

        this.context=context;
        this.listView=listView;
        this.horizontalScrollView=horizontalScrollView;
        this.list=list;
        this.handler=handler;
    }

    @Override
    public void run() {

        horizontalScrollView.setVisibility(View.VISIBLE);
        lists=new ArrayList<ArrayList<String>>();
        ArrayList<String> arrayList=new ArrayList<String>();
            int i=1;
        for (String str:list){
                arrayList.add(str);
                if (i%7==0){
                    lists.add(arrayList);
                    arrayList=new ArrayList<String>();
                    i=0;
                }
                     i++;
            }
        MyAdapter myAdapter=new MyAdapter(context,lists,handler);
        listView.setAdapter(myAdapter);

    }
}
