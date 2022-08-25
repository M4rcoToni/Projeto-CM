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
/**
 *
 * @author victormalves
 */
public class tcpserver extends Thread{
    public static void main(String args[]) throws Exception{
        ServerSocket welcomeSocket = new ServerSocket(6790);
        System.out.println("Servidor");
        tcpserver server = new tcpserver();
        server.start();
        while(true){
            Socket socketConexao = welcomeSocket.accept();
            Thread.sleep((long)(Math.random() * 10000));
            System.out.println("Conexao realizada");
            BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));
            DataOutputStream paraCliente = new DataOutputStream(socketConexao.getOutputStream());
            String dados = doCliente.readLine();
            System.out.println("Recebido do cliente: "+dados);
        }
    }
    public  void run(){
        System.out.println("Cliente");
        Scanner read = new Scanner(System.in);
        while(true){
            try {
                String texto = read.nextLine();
                Socket clientSocket = new Socket("192.168.40.129",6790);
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
