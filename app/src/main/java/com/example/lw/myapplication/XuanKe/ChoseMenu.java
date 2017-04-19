package com.example.lw.myapplication.XuanKe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.example.lw.myapplication.DbUnti.Infodb;
import com.example.lw.myapplication.R;
import com.example.lw.myapplication.WebUnti.useInfo;
import com.example.lw.myapplication.WebUnti.StringtoUnicode;
import com.example.lw.myapplication.widget.loadkb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class ChoseMenu extends Activity {
	private String url;
	private Map<String, String> urlMap;
	private ListView listView;
	private HorizontalScrollView horizontalScrollView;
	private String cookie;
	private String Referer;
	private ArrayList<String> list;
	private Handler handler,isOK;
	private Context context;
	private Button button;
	private Map<Integer,Boolean> postmap;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			loadData(savedInstanceState);
			context=this;
		}
//获取上一个activty传入的数据
	private void loadData(Bundle savedInstanceState) {
		savedInstanceState=getIntent().getExtras();
		urlMap=useInfo.getMap();
		cookie= urlMap.get("cookieValue");
		Referer= urlMap.get("Referer");
		loadingview();
	}

	private void loadingview() {
		isLoding();
		String[] attString={"table[class=datelist]","tr[class]","td"};
		Button button= (Button) findViewById(R.id.post);
		listView= (ListView) findViewById(R.id.listview);
		horizontalScrollView= (HorizontalScrollView) findViewById(R.id.layout);
		button= (Button) findViewById(R.id.post);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String refer = "http://zf.ynftc.cn/"+ urlMap.get("全校公共选课");
				String name=urlMap.get("name");
				String nameUnicode= StringtoUnicode.getUnicodeString(name);
				try {
					nameUnicode=URLEncoder.encode(nameUnicode,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String posturl=refer.replace(name,nameUnicode);
				try {
					refer= refer.replace(name,URLEncoder.encode(name,"gbk"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//Log.i("post",posturl+"---"+name+"---"+refer);
				new Post(cookie,refer,postmap,handler,posturl).start();
			}
		});
		Infodb infodb=new Infodb(this);
		list= infodb.getlist("t_kb");
		infodb.close();
		if (list.size()<2){
			new loadkb(cookie,Referer,this).execute("http://zf.ynftc.cn/"+urlMap.get("学生个人课表"));
		}
		new loadhtml(cookie,Referer,attString,handler,this).execute("http://zf.ynftc.cn/"+urlMap.get("全校公共选课"));
	}
	private void isLoding() {

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj instanceof ArrayList){
					list= (ArrayList<String>) msg.obj;
					new Test(context,listView,horizontalScrollView,list,handler).run();
				}else if (msg.obj instanceof String){
					String str= (String) msg.obj;
					str=str.substring(7,str.length()-3);
					if (str.length()>30)
					{
						str="小子,请先勾选左侧的选课和预定教材";
					}
					new AlertDialog.Builder(ChoseMenu.this).setTitle("O_O告诉你个小秘密")
							.setMessage(str)
							.setPositiveButton("朕知道了", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {

								}
							}).show();

				}else{
					postmap= (Map<Integer, Boolean>) msg.obj;
				}
			}
		};
	}


}
