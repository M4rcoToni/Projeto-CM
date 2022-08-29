package com.example.alterar.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alterar.Mensagens;
import com.example.alterar.QRCODE;
import com.example.alterar.R;
import com.example.alterar.lerqrcode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
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
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

   /// List<Mensagens> lista = QRCODE.Retornadados();
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
        txtenviar = view.findViewById(R.id.textenviar);
        recebetxt = view.findViewById(R.id.recebtxt);
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
    int porta = lerqrcode.retornaPorta();
    private ListView list;
    String array2;    List<Mensagens> listaright = new ArrayList<Mensagens>();
    String array ;

    private class MyThread implements Runnable {
        @Override
        public void run() {
            do {

                try {
                    clientSocket = new Socket(ip, porta); //alterar para testes com os celulares
                    paraServidor = new DataOutputStream(clientSocket.getOutputStream());
                    Thread.sleep((long)(Math.random() * 10000));

                    BufferedReader doServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    if (enviar.length() >1){
                        paraServidor.writeBytes(enviar);

                        array += enviar+"\n";
                        enviar="";
                        recebetxt.setText(array);
                    }


                    clientSocket.close();
                } catch (Throwable e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }


            }while (true);

        }
    }


}