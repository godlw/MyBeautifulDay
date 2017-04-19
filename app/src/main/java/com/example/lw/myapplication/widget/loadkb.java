package com.example.lw.myapplication.widget;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.lw.myapplication.DbUnti.Infodb;

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

public class loadkb extends AsyncTask<String, Void, Void> {


	private String htmldata;
	private String cookie;
	private String referer;
    private Context context;
    private ContentValues values;
	public loadkb(String cookie, String referer,Context context) {
		this.cookie=cookie;
		this.referer=referer;
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
                Element element=document.getElementById("Table1");
                String [] attString={"tbody","tr","td"};
                Elements elements=null;
                for (int i=1;i<attString.length;i++) {
                    elements = element.select(attString[i]);
                }
                Infodb infodb=new Infodb(context);
                SQLiteDatabase db=infodb.getDb();
              for (Element l:elements){
                   if (l.text().length()>8) {
                       String insertStr = "insert into t_kb(title) values('" + l.text() + "')";
                       db.execSQL(insertStr);
                   }
               }
                infodb.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
