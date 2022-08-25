package com.example.alterar;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Button ler;
    private Button mostrar;
    static TextView titulo;
    static TextView recebe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //< 2 - Politicas de Uso
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //>

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

        //

        mostrar = findViewById(R.id.mostrar);
        ler = findViewById(R.id.ler);
        titulo = findViewById(R.id.titulo);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(getApplicationContext(), QRCODE.class);
                startActivity(mostrar);
            }
        });

        ler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ler = new Intent(getApplicationContext(), lerqrcode.class);
                startActivity(ler);
            }
        });

    }



}