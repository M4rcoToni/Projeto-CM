package com.example.alterar;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
public class tcpliente extends Thread{
    public static void main(String args[]) throws Exception{

        ServerSocket welcomeSocket = new ServerSocket(6791);
        System.out.println("Servidor");
        tcpliente cliente = new tcpliente();
        cliente.start();
        String dados;
        while(true){
            Socket socketConexao = welcomeSocket.accept();
            Thread.sleep((long)(Math.random() * 1000));
            BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));
            DataOutputStream paraCliente = new DataOutputStream(socketConexao.getOutputStream());

             dados = doCliente.readLine();
            if( dados != null)
            System.out.println("Recebido do cliente: "+dados);
                
            }
        }
    public  void run(){
        System.out.println("Cliente");
        Scanner read = new Scanner(System.in);
        while(true){
            try {
                String texto = read.nextLine();
                Socket clientSocket = new Socket("192.168.41.78",6789);///"192.168.41.78"10.0.2.16
                DataOutputStream paraServidor = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader doServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                paraServidor.writeBytes(texto+"\n");
                clientSocket.close();
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }

        }

    }
}

