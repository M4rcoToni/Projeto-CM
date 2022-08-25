package com.example.alterar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Cliente extends AppCompatActivity {
    private EditText lembrete;
    private TextView titulo;
    private Button define;
    private EditText mensa;
    private Button enviar;
    static MyThread cliente;
    private String ip;
    private Socket clientSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_lembrete);
        lembrete = findViewById(R.id.lembrete);
        define = findViewById(R.id.definir);
        titulo = findViewById(R.id.titulo);

        mensa = findViewById(R.id.mensagem_cliente);
        enviar = findViewById(R.id.enviar);

        getSupportActionBar().hide();
        cliente = new MyThread();
        new Thread(cliente).start();
        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private class MyThread implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    String mesagem = mensa.getText().toString();
                    ip = lembrete.getText().toString();
                    titulo.setText(lembrete.getText());
                    clientSocket = new Socket(ip, 6790);
                    DataOutputStream paraServidor = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader doServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    Thread.sleep((long)(Math.random() * 10000));
                    paraServidor.writeBytes(mesagem+"\n");
                    clientSocket.close();
                } catch (Exception e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }

        }
    }
}