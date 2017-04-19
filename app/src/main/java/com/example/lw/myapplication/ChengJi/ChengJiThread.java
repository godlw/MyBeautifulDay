package com.example.lw.myapplication.ChengJi;

import android.os.Handler;
import android.os.Message;

import com.example.lw.myapplication.WebUnti.StringtoUnicode;
import com.example.lw.myapplication.WebUnti.useInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 2017/4/14.
 */

public class ChengJiThread extends Thread {
    private List<NameValuePair> list;
    private String cookie;
    private String htmldata;
    private ArrayList<ArrayList<String>> arrayList;
    public ChengJiThread(List<NameValuePair> list,String cookie){
       this.list=list;
        this.cookie=cookie;
        arrayList=new ArrayList<ArrayList<String>>();
    }

    @Override
    public void run() {
        String refer = "http://zf.ynftc.cn/"+ useInfo.getList("成绩查询");
        String name=useInfo.getList("name");
        String nameUnicode= StringtoUnicode.getUnicodeString(name);
        try {
            nameUnicode= URLEncoder.encode(nameUnicode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String posturl=refer.replace(name,nameUnicode);
        try {
            refer= refer.replace(name,URLEncoder.encode(name,"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpGet httpGet=new HttpGet(posturl);
        httpGet.setHeader("cookie",cookie);
        httpGet.setHeader("Referer",refer);
        try {
            HttpResponse httpResponse=new DefaultHttpClient().execute(httpGet);
            HttpEntity httpEntity=httpResponse.getEntity();
           htmldata= EntityUtils.toString(httpEntity);
            Document document= Jsoup.parse(htmldata);
            Element element=document.getElementById("__VIEWSTATE");
            list.add(new BasicNameValuePair("__VIEWSTATE",element.val()));
            element=document.getElementById("__EVENTVALIDATION");
            list.add(new BasicNameValuePair("__EVENTVALIDATION",element.val()));
            list.add(new BasicNameValuePair("__EVENTTARGET",""));
            list.add(new BasicNameValuePair("__EVENTARGUMENT",""));
            list.add(new BasicNameValuePair("__LASTFOCUS",""));
            list.add(new BasicNameValuePair("__VIEWSTATEGENERATOR","B0C8BF1A"));
            list.add(new BasicNameValuePair("ddl_kcxz",""));
            list.add(new BasicNameValuePair("btn_xq","学期成绩"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpPost httpPost = new HttpPost(posturl);
        //禁止重定向
        httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
        httpPost.setHeader("Accept","text/html, application/xhtml+xml, image/jxr, */*");
        httpPost.setHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8,en-US;q=0.5,en;q=0.3");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;");
        httpPost.setHeader("Cookie",cookie);
        httpPost.setHeader("Referer",refer);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list,"gbk"));
            HttpResponse httpResponse=new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode()==200) {
                HttpEntity entity = httpResponse.getEntity();
                htmldata = EntityUtils.toString(entity);
                Item.ClearList();
                Document document = Jsoup.parse(htmldata);
                Element element = document.getElementById("Datagrid1");
                if (element!=null) {
                    Elements elements = element.select("td");
                    int i = 1;
                    ArrayList<String> temp = new ArrayList<String>();
                    for (Element l : elements) {
                        if (i < 15) {
                            temp.add(l.text());
                        } else {
                            temp.add(l.text());
                            arrayList.add(temp);
                            temp = new ArrayList<String>();
                            i = 0;
                        }
                        i++;
                    }
                    Handler handler = ChengJiMenu.getHandler();
                    Message message = new Message();
                    message.obj = arrayList;
                    handler.sendMessage(message);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
