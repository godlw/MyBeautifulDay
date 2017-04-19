package com.example.lw.myapplication.ChengJi;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 2017/4/16.
 */

public class Item {
    private static List<NameValuePair> list=new ArrayList<NameValuePair>();
    private static String xuenian=null;
    private static String xueqi=null;
    public static List<NameValuePair> isPossible(String item){
        int iteml=Integer.valueOf(item);

            if (iteml>2)
            {
                xuenian=String.valueOf(iteml)+"-"+String.valueOf(iteml+1);
                list.add(new BasicNameValuePair("ddlXN",xuenian));
            }else{

                xueqi=String.valueOf(iteml);
                list.add(new BasicNameValuePair("ddlXQ",xueqi));
            }

        if (xueqi!=null&&xuenian!=null){
            return list;
        }else {
            return null;
        }

    }

    public static void ClearList(){
        list.clear();
    }
}
