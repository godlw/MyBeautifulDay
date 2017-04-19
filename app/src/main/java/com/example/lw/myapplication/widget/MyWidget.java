package com.example.lw.myapplication.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.lw.myapplication.R;


public class MyWidget extends AppWidgetProvider {

	private static final String TAG = "MyWidget";

	/** package */
	static ComponentName getComponentName(Context context) {
		return new ComponentName(context, MyWidget.class);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.d(TAG, "onUpdate");
		performUpdate(context, appWidgetManager, appWidgetIds, null);
	}

	private void performUpdate(Context context, AppWidgetManager awm, int[] appWidgetIds, long[] changedEvents) {
		for (int appWidgetId : appWidgetIds) {
			Log.d(TAG, "appWidgetId = " + appWidgetId);
			Intent intent = new Intent(context, MyWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
			views.setTextViewText(R.id.widgettitle, "这是你的个人课表");
			views.setRemoteAdapter(R.id.listView1, intent);

			awm.updateAppWidget(appWidgetId, views);
			awm.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView1);
		}
	}

}
