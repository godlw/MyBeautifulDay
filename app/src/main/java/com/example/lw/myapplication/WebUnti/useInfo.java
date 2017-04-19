package com.example.lw.myapplication.WebUnti;

import java.util.HashMap;
import java.util.Map;

public class useInfo {
	private static Map<String, String> usermap=new HashMap<String, String>();
	
	public static Map<String, String> getMap(){
		return usermap;
	}
	
	 public static void AddMap(Map<String, String> map) {
	        usermap.putAll(map);
	    }
	public static void Addlist(String key,String value){
			usermap.put(key,value);
	}
	public static String getList(String key){
			return usermap.get(key);
	}
}
