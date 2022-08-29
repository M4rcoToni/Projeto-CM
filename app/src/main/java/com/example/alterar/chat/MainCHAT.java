package com.example.alterar.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alterar.Mensagens;
import com.example.alterar.QRCODE;
import com.example.alterar.R;
import com.example.alterar.R;

import androidx.appcompat.app.ActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainCHAT extends AppCompatActivity {
    static MyThread cliente;
    public static List<Mensagens> listaleft = new ArrayList<Mensagens>();
    private TextView ip;
    FrameLayout lt;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actbar= getSupportActionBar(); //resgato o menu
        actbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //comportamento

        ActionBar.Tab tab1 = actbar.newTab().setText("LEITURA"); //nome
        tab1.setTabListener(new MyTabListener( new Fragment1())); //add evento
        actbar.addTab(tab1);

        ActionBar.Tab tab2 = actbar.newTab().setText("CHAT"); //nome
        tab2.setTabListener(new MyTabListener( new Fragment2())); //add evento
        actbar.addTab(tab2); // add na acction bar

        cliente = new MyThread();
        new Thread(cliente).start();
        ip =findViewById(R.id.ip);
        executeAPI();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return  true;
        }else if( id == R.id.voltar){
            System.exit(0);
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

    public void executeAPI(){
        String url = "https://api.myip.com";
        tarefa t = new tarefa();
        t.execute(url);
    }

    class tarefa extends AsyncTask<String, Void ,String> {
        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);
            JSONObject jmain = null;

            try {
                jmain = new JSONObject(p.toString());
                String info = jmain.getString("ip");
                //Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
                ip.setText(info);

            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer str = new StringBuffer();
            try {
                URL url2 = new URL(strings[0]);
                HttpURLConnection coenxao = (HttpURLConnection) url2.openConnection();
                InputStream input = coenxao.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader leitura = new BufferedReader(reader);
                String s;
                if((s=leitura.readLine())!= null){
                    str.append(s);
                }
                System.out.println("##############"+str.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str.toString();
        }
    }
}