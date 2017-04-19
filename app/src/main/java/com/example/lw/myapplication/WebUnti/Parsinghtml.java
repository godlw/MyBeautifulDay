package com.example.lw.myapplication.WebUnti;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parsinghtml extends AsyncTask<String, Void, Void> {
	
	private	String geturl;
	private  Map<String, String> htmldata;
	private CookieStore cookieStore;
	private PutData putData;
	private String[] attString;

	

	public Parsinghtml(CookieStore cookieStore,String[] aStrings) {

		htmldata=new HashMap<String ,String>();
		this.cookieStore=cookieStore;
		this.attString=aStrings;
		}

	public void setHtmlData(PutData putData){
		this.putData=putData;
 	}
	
	@Override
	protected Void doInBackground(String... params) {


		HttpPost mHttpPost = new HttpPost(params[0]);
		mHttpPost.setHeader("Referer", params[0]);
		DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
		defaultHttpClient.setCookieStore(cookieStore);
		try {
			HttpResponse httpResponse=defaultHttpClient.execute(mHttpPost);
			Log.i("login",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
			if (httpResponse.getStatusLine().getStatusCode()== 200) {
				
				 HttpEntity mHttpEntity = httpResponse.getEntity();
			     String html = EntityUtils.toString(mHttpEntity);
			     Document document=Jsoup.parse(html);
			     Elements elements = null;
				 Element element =document.getElementById("xhxm");
				String name=element.text();
					name=name.substring(0,name.length()-2);
					htmldata.put("name",name);
			     for (int i = 0; i < attString.length; i++) {
			    	  elements=document.select(attString[i]);
				}
			     for(Element l:elements){
			    	 htmldata.put(l.text(), l.attr("href"));
			     }

			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {

	putData.HashMapdata(htmldata);
	}
 	public static interface PutData{
 		void HashMapdata(Map<String, String> htmldata);
 	}
}
