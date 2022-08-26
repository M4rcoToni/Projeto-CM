package com.example.alterar.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alterar.R;
import com.example.alterar.R;

import androidx.appcompat.app.ActionBar;



public class MainCHAT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actbar= getSupportActionBar(); //resgato o menu
        actbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //comportamento

        ActionBar.Tab tab1 = actbar.newTab().setText("CHAT"); //nome
        tab1.setTabListener(new MyTabListener( new Fragment1())); //add evento
        actbar.addTab(tab1);

        ActionBar.Tab tab2 = actbar.newTab().setText("CAM"); //nome
        tab2.setTabListener(new MyTabListener( new Fragment2())); //add evento
        actbar.addTab(tab2); // add na acction bar

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return  true;
        }else if( id == R.id.voltar){
            //inicar perfil
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemshare = menu.findItem(R.id.voltar);
        return true;

    }
}