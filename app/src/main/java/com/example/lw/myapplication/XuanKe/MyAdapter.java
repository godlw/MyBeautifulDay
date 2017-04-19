package com.example.lw.myapplication.XuanKe;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.lw.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<ArrayList<String>> lists;
	private  Holder holder;
	private Map<Integer,Boolean> map=new HashMap<>();
	private Handler handler;
	public MyAdapter(Context context, ArrayList<ArrayList<String>> lists, Handler handler) {
		super();
		this.context = context;
		this.lists = lists;
		this.handler=handler;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub

		ArrayList<String> list = lists.get(index);
		if(view == null){
			view = inflater.inflate(R.layout.list_item, null);
			holder=new Holder();
			holder.checkBox1= (CheckBox) view.findViewById(R.id.ck1);
			holder.checkBox2= (CheckBox) view.findViewById(R.id.ck2);
			holder.textView1= (TextView) view.findViewById(R.id.text1);
			holder.textView2 = (TextView) view.findViewById(R.id.text2);
			holder.textView3 = (TextView) view.findViewById(R.id.text3);
			holder.textView4 = (TextView) view.findViewById(R.id.text4);
			holder. textView5 = (TextView) view.findViewById(R.id.text5);
			holder. textView6 = (TextView) view.findViewById(R.id.text6);
			holder.textView7 = (TextView) view.findViewById(R.id.text7);
			holder.textView8= (TextView) view.findViewById(R.id.tv1);
			holder.textView9= (TextView) view.findViewById(R.id.tv2);
			view.setTag(holder);
		}else{
			holder= (Holder) view.getTag();
		}

		view.setBackgroundColor(Color.WHITE);
		holder.textView1.setTextColor(Color.BLACK);
		holder.textView2.setTextColor(Color.BLACK);
		holder.textView3.setTextColor(Color.BLACK);
		holder.textView4.setTextColor(Color.BLACK);
		holder.textView5.setTextColor(Color.BLACK);
		holder.textView6.setTextColor(Color.BLACK);
		holder.textView7.setTextColor(Color.BLACK);
		holder.textView1.setText(list.get(0));
		holder.textView2.setText(list.get(1));
		holder.textView3.setText(list.get(2));
		holder.textView4.setText(list.get(3));
		holder.textView5.setText(list.get(4));
		holder.textView6.setText(list.get(5));
		holder.textView7.setText(list.get(6));
		if(index == 0){
			holder.textView8.setVisibility(View.VISIBLE);
			holder.textView9.setVisibility(View.VISIBLE);
			holder.checkBox1.setVisibility(View.GONE);
			holder.checkBox2.setVisibility(View.GONE);
			view.setBackgroundResource(R.color.head_bg);
			holder.textView1.setTextColor(Color.WHITE);
			holder.textView2.setTextColor(Color.WHITE);
			holder.textView3.setTextColor(Color.WHITE);
			holder.textView4.setTextColor(Color.WHITE);
			holder.textView5.setTextColor(Color.WHITE);
			holder.textView6.setTextColor(Color.WHITE);
			holder.textView7.setTextColor(Color.WHITE);
			holder.textView8.setTextColor(Color.WHITE);
			holder.textView9.setTextColor(Color.WHITE);

		}else{
			holder.checkBox1.setOnCheckedChangeListener(null);

			if(index%2 != 0){
				holder.checkBox1.setVisibility(View.VISIBLE);
				holder.checkBox2.setVisibility(View.VISIBLE);
				holder.textView8.setVisibility(View.GONE);
				holder.textView9.setVisibility(View.GONE);
				view.setBackgroundColor(Color.argb(250 ,  255 ,  255 ,  255 )); 
			}else{
				holder.checkBox1.setVisibility(View.VISIBLE);
				holder.checkBox2.setVisibility(View.VISIBLE);
				holder.textView8.setVisibility(View.GONE);
				holder.textView9.setVisibility(View.GONE);
				view.setBackgroundColor(Color.argb(250 ,  224 ,  243 ,  250 ));    
			}


		}
		holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b){
					Message message=new Message();
					map.put(index,true);
					message.obj=map;
					handler.sendMessage(message);
				}else{
					Message message=new Message();
					map.remove(index);
					message.obj=map;
					handler.sendMessage(message);
				}
			}
		});
		if(map!=null&&map.containsKey(index)){
			holder.checkBox1.setChecked(true);
		}else {
			holder.checkBox1.setChecked(false);
		}
		holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int line=loadhtml.getline();
				line+=index;
				if (isChecked){
					Message message=new Message();
					message.obj=map;
					handler.sendMessage(message);
					map.put(line,true);
				}else{
					Message message=new Message();
					message.obj=map;
					handler.sendMessage(message);
					map.remove(line);
				}
			}
		});
		if (map!=null&&map.containsKey(index+loadhtml.getline())){
			holder.checkBox2.setChecked(true);
		}else {
			holder.checkBox2.setChecked(false);
		}
		return view;
	}
	class Holder {
		TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9;
		CheckBox checkBox1,checkBox2;
	}

}
