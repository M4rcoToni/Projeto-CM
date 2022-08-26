package com.example.alterar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alterar.chat.MainCHAT;

import net.glxn.qrgen.android.QRCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QRCODE extends AppCompatActivity {
    private ImageView qrcode;
    private boolean conecta = true;
    private TextView txt;
    private TextView testefrag;
    static MyThread cliente;
    public static List<Mensagens> lista = new ArrayList<Mensagens>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txt = findViewById(R.id.mostrar);
        testefrag = findViewById(R.id.textfrag);
        cliente = new MyThread();
        new Thread(cliente).start();

        Bitmap myBitmap = QRCode.from(descobreIP()).bitmap();
        qrcode = findViewById(R.id.qrcode);
        qrcode.setImageBitmap(myBitmap);
    }

    public String descobreIP(){
        //< 3 - Pegando IP
        String ip = "";
        try {
            WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Nao da pra por msotrar nenhum ip na tela recebe.setText("192.168.199.12");
        Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_LONG).show();

        return ip;
    }
    int i= 0;
    private class MyThread implements Runnable {
        @Override
        public void run() {
            String men = "";
            ServerSocket welcomeSocket = null;
            try {
                welcomeSocket = new ServerSocket(6791);
                tcpserver server = new tcpserver();
                server.start();

               do {
                    Thread.sleep((long)(Math.random() * 10000));

                    Socket socketConexao = welcomeSocket.accept();

                   BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));

                       men = doCliente.readLine();
                       Mensagens msg = new Mensagens(men);
                       lista.add(msg);
                       txt.setText(""+men);
                   if (i < 1){
                       StartChat();
                       i++;
                   }
                   //while(conecta == true){


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

        return lista;
    }


    public void CrateText(String e){

        String[] textArray = {"One", "Two", "Three", "Four"};
        FrameLayout Layout = new FrameLayout(this);
        setContentView(Layout);
        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray[i]);
            Layout.addView(textView);
        }
    }
}