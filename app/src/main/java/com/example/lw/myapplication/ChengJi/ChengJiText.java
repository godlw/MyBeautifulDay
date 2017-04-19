package com.example.lw.myapplication.ChengJi;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lw on 2017/3/22.
 */

public class ChengJiText extends Thread {

    private Context context;
    private ListView listView;
    private HorizontalScrollView horizontalScrollView;
    private ArrayList<ArrayList<String>> lists;
    public ChengJiText(Context context, ListView listView, HorizontalScrollView horizontalScrollView, ArrayList<ArrayList<String>> lists){

        this.context=context;
        this.listView=listView;
        this.horizontalScrollView=horizontalScrollView;
        this.lists=lists;
    }

    @Override
    public void run() {
        if (lists.size()>1) {
            horizontalScrollView.setVisibility(View.VISIBLE);
            ChengJiAdapter chengJiAdapter = new ChengJiAdapter(context, lists);
            listView.setAdapter(chengJiAdapter);
        }
    }
}
