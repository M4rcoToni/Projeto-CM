package com.example.alterar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.alterar.chat.MainCHAT;
import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class lerqrcode extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 161;
    static MyThread server;

    private Socket clientSocket;
    static String ip;
    private CodeScanner Scanner;
    private EditText enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lerqrcode);
        getSupportActionBar().hide();
        enviar = findViewById(R.id.textenviar);
        server = new MyThread();
        new Thread(server).start();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        Scanner = new CodeScanner(this, scannerView);
        Scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(lerqrcode.this, result.getText(), Toast.LENGTH_SHORT).show();
                        ip= result.getText();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scanner.startPreview();
            }
        });
    }
    public static String retornaIP(){
        return ip;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Scanner.startPreview();
    }

    @Override
    protected void onPause() {
        Scanner.releaseResources();
        super.onPause();
    }
    int i= 0;

    private class MyThread implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    clientSocket = new Socket(ip, 6791);
                    DataOutputStream paraServidor = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader doServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    if (i < 1){
                        StartChat();
                        i++;paraServidor.writeBytes("teste");
                    }
                    Thread.sleep((long)(Math.random() * 10000));
                    paraServidor.writeBytes(enviar.getText().toString());
                    clientSocket.close();
                    server.finalize();
                    break;
                } catch (Exception e) {
                    //TODO: handle exception
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        }
    }
    public void StartChat(){
            Intent chat = new Intent(getApplicationContext(), MainCHAT.class);
            startActivity(chat);

    }

}