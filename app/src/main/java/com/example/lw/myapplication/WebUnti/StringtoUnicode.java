package com.example.lw.myapplication.WebUnti;

/**
 * Created by lw on 2017/4/5.
 */

public class StringtoUnicode {

    public static String getUnicodeString(String string){

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }
}
