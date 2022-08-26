package com.example.alterar.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alterar.Mensagens;
import com.example.alterar.QRCODE;
import com.example.alterar.lerqrcode;
import com.example.alterar.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;


public class Fragment1 extends Fragment implements View.OnClickListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }


    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }


    List<Mensagens> lista = QRCODE.Retornadados();
    private TextView txt;
    private TextView recebetxt;
    private TextView txtenviar;
    private Button btnenviar;
    private String enviar;

    private MyThread cliente;
    private Socket clientSocket;
    private DataOutputStream paraServidor;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt = view.findViewById(R.id.textfrag);
        txtenviar = view.findViewById(R.id.textenviar);
        recebetxt = view.findViewById(R.id.recebetexto);
        btnenviar = view.findViewById(R.id.enviar);
        btnenviar.setOnClickListener(this);
        cliente = new MyThread();
        new Thread(cliente).start();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.enviar){

            enviar =(txtenviar.getText().toString());
            txtenviar.setText("");
            //codigo para guardar
        }
    }
String ip = lerqrcode.retornaIP();
    private class MyThread implements Runnable {
        @Override
        public void run() {
            do {

                try {
                    clientSocket = new Socket("192.168.00.107", 6789); //alterar para testes com os celulares
                    paraServidor = new DataOutputStream(clientSocket.getOutputStream());
                    Thread.sleep((long)(Math.random() * 10000));

                    BufferedReader doServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    paraServidor.writeBytes(enviar);
                    clientSocket.close();
                    cliente.finalize();
                    break;
                } catch (Throwable e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
                lista = QRCODE.Retornadados();

                txt.setText(""+lista.toString());
            }while (true);

        }
    }


}