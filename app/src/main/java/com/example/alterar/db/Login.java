package com.example.alterar.db;
import java.io.Serializable;

public class Login implements Serializable {
    private int id;
    private String login;
    private String senha;

    public Login(){
        this.id= 0;
        this.login = "";
        this.senha = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String nome) {this.login = nome;}

    public String getSenha() {
        return senha;
    }

    public void setSenha(String curso) {
        this.senha = curso;
    }
}
