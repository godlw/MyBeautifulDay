package com.example.lw.myapplication.XuanKe;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class loadhtml extends AsyncTask<String, Void, Void> {


	private String htmldata;
	private String cookie;
	private String referer;
    private Handler handler;
    private String[] attString;
    private Context context;
    private  static List<String> list=new ArrayList<String>();

	public loadhtml(String cookie, String referer, String[] attString, Handler handler,Context context) {
		this.cookie=cookie;
		this.referer=referer;
        this.attString=attString;
        this.handler=handler;
        this.context=context;
		}

    @Override
    protected Void doInBackground(String... strings) {
        ArrayList<String> title=new ArrayList<String>();
        HttpGet get=new HttpGet(strings[0]);
        get.setHeader("Referer",referer);
        get.setHeader("cookie",cookie);
        try {
            HttpResponse httPResponse=new DefaultHttpClient().execute(get);
            if (httPResponse.getStatusLine().getStatusCode()==200){
                HttpEntity entity=httPResponse.getEntity();
                htmldata= EntityUtils.toString(entity);
                Document document= Jsoup.parse(htmldata);
                Element document1= document.getElementById("kcmcGrid");
                Element document2= document.getElementById("__VIEWSTATE");
                VIEWSTATE.put__VIEWSTATE(document2.val());
                Element document3= document.getElementById("__EVENTVALIDATION");
                VIEWSTATE.put__EVENTVALIDATION(document3.val());
                Elements elements=null;
                for (int i=1;i<attString.length;i++) {
                    elements = document1.select(attString[i]);
                }
                int index=1;
               for(Element l:elements){
                    if (index>=3 &&index<=8 || index==13){
                        list.add(l.text());
                    }else if (index==20){
                        index=0;
                    }
                   index++;
                 }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        Message message=new Message();
        message.obj=list;
        handler.sendMessage(message);

    }
    public static int getline(){
        return list.size()/7;
    }
}
