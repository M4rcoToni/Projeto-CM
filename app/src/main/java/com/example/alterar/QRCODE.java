package com.example.alterar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alterar.chat.MainCHAT;

import net.glxn.qrgen.android.QRCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class QRCODE extends AppCompatActivity {
    private ImageView qrcode;
    boolean conecta;
    private TextView txt;
    private TextView testefrag;
    static MyThread cliente;
    String dados ;

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

    private class MyThread implements Runnable {
        @Override
        public void run() {
            ServerSocket welcomeSocket = null;
            try {
                welcomeSocket = new ServerSocket(6791);
                tcpserver server = new tcpserver();
                server.start();
               do {
                    Thread.sleep((long)(Math.random() * 10000));

                    Socket socketConexao = welcomeSocket.accept();
                   BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));
                   dados = doCliente.readLine();
                   txt.setText(dados);
                    conecta = true;



                    testefrag.setText(dados);
                }while (true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (conecta){
                Intent chat = new Intent(getApplicationContext(), MainCHAT.class);
                startActivity(chat);
            }

        }
    }
}