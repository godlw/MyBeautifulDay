package com.example.lw.myapplication.DbUnti;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Infodb {

	private SQLiteDatabase db;
	public Infodb(Context context) {
        db=context.openOrCreateDatabase("userinfo.db", Context.MODE_PRIVATE, null);
		}


	public void createTable(String tablenStr){
		String createstr="create table if not exists "+tablenStr;
		db.execSQL(createstr);
	}

	public SQLiteDatabase getDb(){
		return db;
			}

	public void close(){
		db.close();
		}
    public Cursor getCursor(String Selectstr){
        Cursor cursor=db.rawQuery(Selectstr,null);
        return cursor;
    }
    public void insert(String value){
        String insertStr="insert into t_xk(title) values('"+ value+"')";
        db.execSQL(insertStr);
     }

    public ArrayList<String> getlist(String tablename){
		ArrayList<String> list=new ArrayList<String>();
        String selectStr="select *from "+tablename;
     Cursor cursor=db.rawQuery(selectStr,null);
        while (cursor.moveToNext()){

           list.add(cursor.getString(cursor.getColumnIndex("title")));
        }

		return list;
    }


}
