package com.example.alterar.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.alterar.R;

import androidx.appcompat.app.ActionBar;


public class MainCHAT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        ActionBar actbar= getSupportActionBar(); //resgato o menu
        actbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //comportamento

        ActionBar.Tab tab1 = actbar.newTab().setText("CAM"); //nome
        tab1.setTabListener(new MyTabListener( new Fragment1())); //add evento
        actbar.addTab(tab1);

        ActionBar.Tab tab2 = actbar.newTab().setText("CHAT"); //nome
        tab2.setTabListener(new MyTabListener( new Fragment2())); //add evento
        actbar.addTab(tab2); // add na acction bar

    }
}