package com.example.lw.myapplication.XuanKe;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lw on 2017/3/29.
 */

public class Post extends Thread{
    private String cookie;
    private String referer;
    private Map<Integer,Boolean> postmap;
    private List<NameValuePair> postdataList = new ArrayList<NameValuePair>();
    private Handler handler;
    private String posturl;
    public Post(String cookie,String referer,Map<Integer,Boolean> postmap,Handler handler,String posturl){
    this.cookie=cookie;
    this.referer=referer;
        this.postmap=postmap;
        this.handler=handler;
        this.posturl=posturl;
    }
    @Override
    public void run() {
        int line=loadhtml.getline();
        List<NameValuePair> postdataList = new ArrayList<NameValuePair>();
        String data="";
        if (postmap!=null){
        for (Integer k : postmap.keySet()) {
            Log.i("postmap",postdataList.toString());
            if (k<=line){
            data= String.format("kcmcGrid$ctl%02d$xk", k + 1);
            postdataList.add(new BasicNameValuePair(data,"on"));}
            else {
                if ((k+1)%line==0){
                    data= String.format("kcmcGrid$ctl%02d$jc",line);
                    postdataList.add(new BasicNameValuePair(data,"on"));
                }else {
                    data= String.format("kcmcGrid$ctl%02d$jc", (k + 1)%line);
                    postdataList.add(new BasicNameValuePair(data,"on"));
                }
            }

        }}

        DefaultHttpClient defaultclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(posturl);
        //禁止重定向
        httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
        httpPost.setHeader("Accept","text/html, application/xhtml+xml, image/jxr, */*");
        httpPost.setHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8,en-US;q=0.5,en;q=0.3");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;");
        httpPost.setHeader("Cookie",cookie);
        httpPost.setHeader("Referer",posturl);

        postdataList.add(new BasicNameValuePair("__EVENTTARGET",""));
        postdataList.add(new BasicNameValuePair("__EVENTARGUMENT",""));
        postdataList.add(new BasicNameValuePair("__LASTFOCUS",""));
        postdataList.add(new BasicNameValuePair("__VIEWSTATE",VIEWSTATE.get__VIEWSTATE()));
        postdataList.add(new BasicNameValuePair("__VIEWSTATEGENERATOR","4E92E216"));
        postdataList.add(new BasicNameValuePair("__EVENTVALIDATION",VIEWSTATE.get_EVENTVALIDATION()));
        postdataList.add(new BasicNameValuePair("ddl_kcxz",""));
        postdataList.add(new BasicNameValuePair("ddl_ywyl","有"));
        postdataList.add(new BasicNameValuePair("ddl_kcgs",""));
        postdataList.add(new BasicNameValuePair("ddl_sksj",""));
        postdataList.add(new BasicNameValuePair("ddl_xqbs","1"));
        postdataList.add(new BasicNameValuePair("TextBox1",""));
        postdataList.add(new BasicNameValuePair("Button1","  提交  "));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postdataList,"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse httpResponse=defaultclient.execute(httpPost);
            HttpEntity entity=httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode()==200){
                String htmldata=EntityUtils.toString(entity,"gbk");
                Document document= Jsoup.parse(htmldata);
                Elements alert = document.select("script[language]");
                String title=alert.get(0).data();
                Message message=new Message();
                message.obj=title;
                handler.sendMessage(message);}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
