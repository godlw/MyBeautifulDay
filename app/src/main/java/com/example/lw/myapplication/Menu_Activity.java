package com.example.lw.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lw.myapplication.ChengJi.ChengJiMenu;
import com.example.lw.myapplication.XuanKe.ChoseMenu;

/**
 * Created by lw on 2017/4/19.
 */

public class Menu_Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        init();
    }

    private void init() {

        Button xk= (Button) findViewById(R.id.Xk);
        Button cj= (Button) findViewById(R.id.Cj);
        xk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent=intent.setClass(Menu_Activity.this, ChoseMenu.class);
                startActivity(intent);
            }
        });
        cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent=intent.setClass(Menu_Activity.this, ChengJiMenu.class);
                startActivity(intent);
            }
        });
    }
}
