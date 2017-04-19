package com.example.lw.myapplication.XuanKe;

/**
 * Created by lw on 2017/4/8.
 */

public class VIEWSTATE {
    private static String thisEVENTVALIDATION="";
    private static String thisVIEWSTATE="";
    public static String get_EVENTVALIDATION(){

        return thisEVENTVALIDATION;
    }
        public static String get__VIEWSTATE(){

        return thisVIEWSTATE;
    }
    public  static void put__VIEWSTATE(String VIEWSTATE){
        thisVIEWSTATE=VIEWSTATE;
    }
    public  static void put__EVENTVALIDATION(String EVENTVALIDATION){
       thisEVENTVALIDATION=EVENTVALIDATION;
    }
}
