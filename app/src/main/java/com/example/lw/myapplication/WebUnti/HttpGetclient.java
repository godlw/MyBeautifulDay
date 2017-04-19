package com.example.lw.myapplication.WebUnti;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpGetclient extends AsyncTask<String,Void, Bitmap>  {

 	private Context context;
 	private ImageView imageView;
 	private CookieStore cookies;
 	private Get get;
 	
 	public HttpGetclient(Context context,ImageView imageView) {
 		
 		this.context=context;
 		this.imageView=imageView;
 	}

 	public void setcookies(Get get){
 		this.get=get;
 	}
 		
 	@Override
 	protected Bitmap doInBackground(String... arg0) {
 	
 			Bitmap bitmap = null;

              HttpPost httPost = new HttpPost(arg0[0]);
              DefaultHttpClient client = new DefaultHttpClient();
              httPost.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
              try {
 				HttpResponse response=client.execute(httPost);
 				 cookies=((AbstractHttpClient) client).getCookieStore();
 			        byte[] bytes = new byte[1024];
 			        bytes = EntityUtils.toByteArray( response.getEntity());
 			        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  
 			} catch (ClientProtocolException e) {
 				
 				e.printStackTrace();
 			} catch (IOException e) {
 				
 				e.printStackTrace();
 			}
        
 		return bitmap;
 	}
 	
 	@Override
 	protected void onPostExecute(Bitmap result) {
 		
 		
 		if (result!=null) {
 			imageView.setImageBitmap(result);
			get.cookies(cookies);
 		}else {
 		
 			Toast.makeText(context,"请检查你的网络连接",Toast.LENGTH_SHORT).show();
 		}
 		
 	}
 	
 	public static interface Get{
 		void cookies(CookieStore cookies);
 	}
 }

