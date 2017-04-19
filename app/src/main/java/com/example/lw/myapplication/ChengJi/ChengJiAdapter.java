package com.example.lw.myapplication.ChengJi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lw.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChengJiAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<ArrayList<String>> lists;
	private  Holder holder;
	private Map<Integer,Boolean> map=new HashMap<>();

	public ChengJiAdapter(Context context, ArrayList<ArrayList<String>> lists) {
        super();
		this.context = context;
		this.lists = lists;
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
			view = inflater.inflate(R.layout.item_layout, null);
			holder=new Holder();
			holder.textView1 = (TextView) view.findViewById(R.id.title1);
			holder.textView2 = (TextView) view.findViewById(R.id.title2);
			holder.textView3 = (TextView) view.findViewById(R.id.title3);
			holder.textView4 = (TextView) view.findViewById(R.id.title4);
			holder.textView5 = (TextView) view.findViewById(R.id.title5);
			holder.textView6 = (TextView) view.findViewById(R.id.title6);
			holder.textView7 = (TextView) view.findViewById(R.id.title7);
            holder.textView8 = (TextView) view.findViewById(R.id.title8);
            holder.textView9 = (TextView) view.findViewById(R.id.title9);
            holder.textView10 = (TextView) view.findViewById(R.id.title10);
            holder.textView11 = (TextView) view.findViewById(R.id.title11);
            holder.textView12 = (TextView) view.findViewById(R.id.title12);
            holder.textView13 = (TextView) view.findViewById(R.id.title13);
            holder.textView14 = (TextView) view.findViewById(R.id.title14);
            holder.textView15 = (TextView) view.findViewById(R.id.title15);
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
        holder.textView8.setTextColor(Color.BLACK);
        holder.textView9.setTextColor(Color.BLACK);
        holder.textView10.setTextColor(Color.BLACK);
        holder.textView11.setTextColor(Color.BLACK);
        holder.textView12.setTextColor(Color.BLACK);
        holder.textView13.setTextColor(Color.BLACK);
        holder.textView14.setTextColor(Color.BLACK);
        holder.textView15.setTextColor(Color.BLACK);
		holder.textView1.setText(list.get(0));
		holder.textView2.setText(list.get(1));
		holder.textView3.setText(list.get(2));
		holder.textView4.setText(list.get(3));
		holder.textView5.setText(list.get(4));
		holder.textView6.setText(list.get(5));
		holder.textView7.setText(list.get(6));
        holder.textView8.setText(list.get(7));
        holder.textView9.setText(list.get(8));
        holder.textView10.setText(list.get(9));
        holder.textView11.setText(list.get(10));
        holder.textView12.setText(list.get(11));
        holder.textView13.setText(list.get(12));
        holder.textView14.setText(list.get(13));
        holder.textView15.setText(list.get(14));
		if(index == 0){
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
            holder.textView10.setTextColor(Color.WHITE);
            holder.textView11.setTextColor(Color.WHITE);
            holder.textView12.setTextColor(Color.WHITE);
            holder.textView13.setTextColor(Color.WHITE);
            holder.textView14.setTextColor(Color.WHITE);
            holder.textView15.setTextColor(Color.WHITE);
		}else{

			if(index%2 != 0){
				view.setBackgroundColor(Color.argb(250 ,  255 ,  255 ,  255 )); 
			}else{
				view.setBackgroundColor(Color.argb(250 ,  224 ,  243 ,  250 ));    
			}

		}

		return view;
	}
	class Holder {
		TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10,textView11,textView12,textView13,textView14,textView15;
	}

}
