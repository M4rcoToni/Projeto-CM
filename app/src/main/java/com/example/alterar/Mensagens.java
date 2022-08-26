package com.example.alterar;

public class Mensagens {
    private String mensagem;

    public Mensagens(String msg){
        super();
        this.mensagem = msg;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {this.mensagem = mensagem;}

    @Override
    public String toString() {
        return ""+mensagem;
    }
}
