package com.example.alterar.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alterar.Mensagens;
import com.example.alterar.QRCODE;
import com.example.alterar.R;
import com.example.alterar.R;

import androidx.appcompat.app.ActionBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainCHAT extends AppCompatActivity {
    static MyThread cliente;
    public static List<Mensagens> listaleft = new ArrayList<Mensagens>();
    private TextView txtrecebe;
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

        cliente = new MyThread();
        new Thread(cliente).start();
        //txtrecebe = findViewById(R.id.recebetexto);
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
    int i= 0;
    private class MyThread implements Runnable {
        @Override
        public void run() {
            String men = "";
            ServerSocket welcomeSocket = null;
            try {
                //welcomeSocket = new ServerSocket(6791);
                do {
                    Thread.sleep((long)(Math.random() * 10000));

                    Socket socketConexao = welcomeSocket.accept();

                    BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));

                    men = doCliente.readLine();
                    Mensagens msg = new Mensagens(men);
                    listaleft.add(msg);
                    txtrecebe.setText(men);
                    if (i < 1){
                        StartChat();
                        i++;
                    }


                }while (true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void StartChat(){
        Intent chat = new Intent(getApplicationContext(), MainCHAT.class);
        startActivity(chat);

    }

    public static List<Mensagens> Retornadados(){

        return listaleft;
    }
}