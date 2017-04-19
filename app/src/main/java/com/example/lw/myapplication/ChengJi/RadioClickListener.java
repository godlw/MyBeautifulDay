package com.example.lw.myapplication.ChengJi;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

/**
 * Created by lw on 2017/4/14.
 */

public class RadioClickListener implements View.OnClickListener {
    private String title;
    private String[] item;
    private ListView areaListView;
    private Context context;
    private RadioOnClick OnClick;
    public RadioClickListener(String title,String[] item,Context context){
        this.title=title;
        this.item=item;
        this.context=context;
        OnClick=new RadioOnClick(item,-1);

    }

    @Override
    public void onClick(View v) {
        AlertDialog ad=new AlertDialog.Builder(context).setTitle(title).setSingleChoiceItems(item,OnClick.getIndex(),OnClick).create();
        areaListView=ad.getListView();
        ad.show();
    }
}
