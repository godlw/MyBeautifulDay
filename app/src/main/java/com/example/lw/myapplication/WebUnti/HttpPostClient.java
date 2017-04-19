package com.example.lw.myapplication.WebUnti;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpPostClient extends AsyncTask<String, Void,  String>{
	
	private org.apache.http.client.CookieStore cookies;
	private String username;
	private String passwd;
	private String verifation;
	private Handler handler;
	
	public HttpPostClient(Handler handler,org.apache.http.client.CookieStore cookiesString,String username,String passwd,String verifation) {

		this.cookies=cookiesString;
		this.username=username;
		this.passwd=passwd;
		this.verifation=verifation;
		this.handler=handler;
	}

	@Override
		protected String doInBackground(String... params) {
		

			String result="";

			DefaultHttpClient defaultclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(params[0]);
            httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);  
            
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");   
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");  
            defaultclient.setCookieStore((org.apache.http.client.CookieStore) cookies);
            HttpResponse httpResponse = null;


            List<NameValuePair> postdataList = new ArrayList<NameValuePair>();
            postdataList.add(new BasicNameValuePair("__VIEWSTATE","/wEPDwUKMTE3MDU2MjIzN2RkdDoGVaXYFYr92Kk4I9olBz/MjT8="));
            postdataList.add(new BasicNameValuePair("__VIEWSTATEGENERATOR","09394A33"));
            postdataList.add(new BasicNameValuePair("__EVENTVALIDATION","/wEWCwKUj/bTBwLs0bLrBgLs0fbZDALs0Yq1BQK/wuqQDgKAqenNDQLN7c0VAuaMg+INAveMotMNAoznisYGArursYYIw2nAkF5SibxJZMn7INOh/Xr2vBo="));
            postdataList.add(new BasicNameValuePair("TextBox1",username));
            postdataList.add(new BasicNameValuePair("TextBox2",passwd));
            postdataList.add(new BasicNameValuePair("TextBox3",verifation));
            postdataList.add(new BasicNameValuePair("RadioButtonList1","学生"));
            postdataList.add(new BasicNameValuePair("Button1",""));
        


            try {
                httpPost.setEntity(new UrlEncodedFormEntity( postdataList,"GBK"));
                httpResponse = defaultclient.execute(httpPost);
                Log.i("xyz", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
				HttpEntity httpEntity= httpResponse.getEntity();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
			return String.valueOf(httpResponse.getStatusLine().getStatusCode()); 
        
		
	}
		
	@Override
	protected void onPostExecute(String result) {

	     Log.i("SufccessfulCode", result);
	     int Code=new Integer(result);
	     Message msg=new Message();

		if(Code == 302){
			msg.arg1=1;
			}else {
			msg.arg1=2;
			}
		handler.sendMessage(msg);
		}
}
