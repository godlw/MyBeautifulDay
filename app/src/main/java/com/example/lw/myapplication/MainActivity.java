package com.example.lw.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lw.myapplication.DbUnti.Infodb;
import com.example.lw.myapplication.WebUnti.HttpGetclient;
import com.example.lw.myapplication.WebUnti.HttpPostClient;
import com.example.lw.myapplication.WebUnti.Parsinghtml;
import com.example.lw.myapplication.WebUnti.useInfo;

import org.apache.http.cookie.Cookie;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private EditText register;
    private ImageView refreshImageView;
    private ImageButton imageButton;
    private Button loginButton;
    private Handler handler;
    private org.apache.http.client.CookieStore cookiesString;
    private String user = "";
    private ContentValues values;
    private String Referer;
    private String isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activty);

        PloginCode();
        LoadingView();

    }

    private void LoadingView() {


        username = (EditText) findViewById(R.id.et_user);
        password = (EditText) findViewById(R.id.et_password);
        register = (EditText) findViewById(R.id.et_verifation);
        refreshImageView = (ImageView) findViewById(R.id.iv_verifation);
        imageButton = (ImageButton) findViewById(R.id.ib_refresh);
        loginButton = (Button) findViewById(R.id.bt_login);
        refresh();
        loadDB();

        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                refresh();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                values=new ContentValues();
                values.put("id","1");
                values.put("username", username.getText().toString());
                values.put("password", password.getText().toString());
                user = username.getText().toString();
                new HttpPostClient(handler,cookiesString, username.getText()
                        .toString(), password.getText().toString(), register
                        .getText().toString())
                        .execute("http://zf.ynftc.cn/default2.aspx");
            }
        });

    }

    private void loadDB() {
        String Usernamestr = null;
        String Passwordstr = null;
        Infodb infodb=new Infodb(this);
        infodb.createTable("t_user(id int  identity(1,1) primary key,username nvarchar(20),password nvarchar(20))");
        infodb.createTable("t_xk(title text)");
        infodb.createTable("t_kb(title text)");
        String selectStr="select username,password from t_user";
        Cursor cursor=infodb.getCursor(selectStr);
        while(cursor.moveToNext()){
            Usernamestr=cursor.getString(cursor.getColumnIndex("username"));
            Passwordstr=cursor.getString(cursor.getColumnIndex("password"));
           }
        if (Passwordstr!=""){
            username.setText(Usernamestr);
            password.setText(Passwordstr);
        }
        infodb.close();
    }

    private void PloginCode() {

        handler = new Handler() {
            @Override

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.arg1) {
                    case 1:
                        Toast.makeText(MainActivity.this, "登录成功",
                                Toast.LENGTH_SHORT).show();
                        loadInfo();
                        insertUser();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "请检查验证码密码或者账号",
                                Toast.LENGTH_SHORT).show();
                        refresh();
                        break;
                }
            }
        };
    }


    protected void insertUser() {
        String usernameStr = "";
        String passwordStr = "";
        String selectStr="select username,password from t_user";
        String updataStr="update t_user set username='"+values.get("username")+"',password='"+values.get("password")+"' where id=1";
        Infodb infodb=new Infodb(this);
        Cursor cursor=infodb.getCursor(selectStr);
        while(cursor.moveToNext()){
            usernameStr=cursor.getString(cursor.getColumnIndex("username"));
            passwordStr=cursor.getString(cursor.getColumnIndex("password"));
         }
        if (usernameStr.length()<5){
            SQLiteDatabase db=infodb.getDb();
            db.insert("t_user",null,values);
            db.close();
        }else{
            SQLiteDatabase db=infodb.getDb();
            db.execSQL(updataStr);
            db.close();
        }

    }


    private void refresh() {

        HttpGetclient httpGetClient = new HttpGetclient(this, refreshImageView);
        httpGetClient.setcookies(new HttpGetclient.Get() {

            @Override
            public void cookies(org.apache.http.client.CookieStore cookies) {
                cookiesString =  cookies;
                Log.i("cookies",cookies.toString());
            }
        });
        httpGetClient.execute("http://zf.ynftc.cn/CheckCode.aspx");

    }



    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(this,"双击退出",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);

            }
            return true;

        }
        return super.onKeyDown(keyCode, event);

    }


    public void loadInfo() {

        String[] attString={"ul[class=nav]","a[href]"};
        Parsinghtml parsingJson = new Parsinghtml(cookiesString,attString);
        parsingJson.setHtmlData(new Parsinghtml.PutData() {
            @Override
            public void HashMapdata(Map<String, String> htmldata) {
                String cookie="";

                List<Cookie> cookies= cookiesString.getCookies();
                for (Cookie ck:cookies){
                    cookie=ck.getName()+"="+ck.getValue();
                }

                useInfo.AddMap(htmldata);
                useInfo.Addlist("cookieValue",cookie);
                useInfo.Addlist("Referer",Referer);
                Intent intent=new Intent();
                intent=intent.setClass(MainActivity.this, Menu_Activity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        parsingJson.execute("http://zf.ynftc.cn/xs_main.aspx?xh=" + user);
        Referer="http://zf.ynftc.cn/xs_main.aspx?xh=" + user;
    }

}
