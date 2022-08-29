package com.example.alterar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alterar.chat.MainCHAT;

import net.glxn.qrgen.android.QRCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QRCODE extends AppCompatActivity implements View.OnClickListener {
    private ImageView qrcode;
    private boolean conecta = true;
    private TextView txt;
    private TextView txtporta;
    private TextView testefrag;
    private Button criaserver;
    static MyThread server;
    private int porta;
    public static List<Mensagens> listaright = new ArrayList<Mensagens>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txt = findViewById(R.id.mostrar);
        testefrag = findViewById(R.id.textfrag);
        txtporta = findViewById(R.id.textporta);
        criaserver = findViewById(R.id.criarserver);
        criaserver.setOnClickListener(this);
        Bitmap myBitmap = QRCode.from(descobreIP()).bitmap();
        qrcode = findViewById(R.id.qrcode);
        qrcode.setImageBitmap(myBitmap);
    }
    @Override
    public void onClick(View v) {
        if(txtporta.getText().toString().equals("") ){
            Toast.makeText(this, "Preencha os dados!!!", Toast.LENGTH_LONG).show();
        }else {
            porta = Integer.parseInt(txtporta.getText().toString());
            server = new MyThread();
            new Thread(server).start();
            Toast.makeText(getApplicationContext(), "Server Criado!!", Toast.LENGTH_LONG).show();
        }
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
    String array;

    private class MyThread implements Runnable {
        @Override
        public void run() {
            String men = "";
            ServerSocket welcomeSocket = null;
            try {
                welcomeSocket = new ServerSocket(porta);
               do {
                    Thread.sleep((long)(Math.random() * 10000));

                    Socket socketConexao = welcomeSocket.accept();

                   BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));

                       men = doCliente.readLine();
                       Mensagens msg = new Mensagens(men);
                       listaright.add(msg);
                       txt.setText(""+men);
                   if (i < 1){
                       StartChat();
                       i++;
                   }
                   //while(conecta == true){ oi


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

    public static String Retornadados(){
        String array = "";
        for (Mensagens e : listaright){
            array += "\n"+e.getMensagem();
        }
        return array;
    }


}