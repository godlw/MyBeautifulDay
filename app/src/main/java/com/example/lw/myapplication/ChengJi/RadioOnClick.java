package com.example.lw.myapplication.ChengJi;

import android.content.DialogInterface;

import com.example.lw.myapplication.WebUnti.useInfo;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by lw on 2017/4/14.
 */

public class RadioOnClick implements DialogInterface.OnClickListener{
    private int index;
    private String[] item;
    public RadioOnClick(String[] item,int index){
        this.index=index;
        this.item=item;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        setIndex(which);
        index=-1;
       List<NameValuePair> list= Item.isPossible(item[which]);
        if (list!=null){
           String cookie= useInfo.getList("cookieValue");
            new ChengJiThread(list,cookie).start();
        }
        dialog.dismiss();
    }

}
