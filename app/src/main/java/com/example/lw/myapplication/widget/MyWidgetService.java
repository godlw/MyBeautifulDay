package com.example.lw.myapplication.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.lw.myapplication.DbUnti.Infodb;
import com.example.lw.myapplication.R;

import java.util.ArrayList;

public class MyWidgetService extends RemoteViewsService {

	private static final boolean DB = true;
	private static final String TAG = "MyWidgetService";

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		log("onGetViewFactory, intent=" + intent);
		return new MyWidgetFactory(getApplicationContext(), intent);
	}

	public static class MyWidgetFactory implements RemoteViewsFactory {

		private Context mContext;

		private String[] mFoods = new String[] {};

		// 构造
		public MyWidgetFactory(Context context, Intent intent) {
			log("MyWidgetFactory");
			mContext = context;
			Infodb infodb=new Infodb(mContext);
			ArrayList arrayList =infodb.getlist("t_kb");
			infodb.close();
			mFoods= (String[]) arrayList.toArray(mFoods);
		}

		@Override
		public int getCount() {
			log("getCount");
			return mFoods.length;
		}

		@Override
		public long getItemId(int position) {
			log("getItemId");
			return position;
		}

		@Override
		public RemoteViews getLoadingView() {
			log("getLoadingView");
			return null;
		}

		@Override
		public RemoteViews getViewAt(int position) {
			log("getViewAt, position=" + position);
			if (position < 0 || position >= getCount()) {
				return null;
			}
			RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.layout_item);
			views.setTextViewText(R.id.textView1, mFoods[position]);
			return views;
		}

		@Override
		public int getViewTypeCount() {
			log("getViewTypeCount");
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			log("hasStableIds");
			return true;
		}

		@Override
		public void onCreate() {
			log("onCreate");
		}

		@Override
		public void onDataSetChanged() {
			log("onDataSetChanged");
		}

		@Override
		public void onDestroy() {
			log("onDestroy");
		}
	}

	private static void log(String log) {
		if (DB)
			Log.d(TAG, log);
	}

}