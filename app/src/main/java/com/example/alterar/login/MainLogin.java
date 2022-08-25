package com.example.alterar.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alterar.MainActivity;
import com.example.alterar.R;
import com.example.alterar.db.Login;
import com.example.alterar.db.LoginDB;

import java.io.Serializable;
import java.util.List;

public class MainLogin extends AppCompatActivity  implements View.OnClickListener{
    private EditText edlogin;
    private EditText edsenha;
    private Button btnlogin;
    private Button btncrialogin;
    private LoginDB db;
    private String login;
    private String senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edlogin = findViewById(R.id.login);
        edsenha = findViewById(R.id.senha);
        getSupportActionBar().hide();
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);
        btncrialogin = findViewById(R.id.crirlogin);
        btncrialogin.setOnClickListener(this);
        db = new LoginDB(this);
    }

    @Override
    public void onClick(View v) {
        if(edlogin.getText().toString() != "" && edsenha.getText().toString() != ""){
            if(v.getId()==R.id.crirlogin){
                Login e = new Login();
                e.setLogin(edlogin.getText().toString().trim());
                e.setSenha(edsenha.getText().toString().trim());
                db.save(e);
                Toast.makeText(this, "Criado com Sucesso!!!", Toast.LENGTH_LONG).show();
                //codigo para guardar
            }
            else if(v.getId()==R.id.btnlogin) {
                //codigo para buscar
                List<Login> elista = db.findAll();
                login = edlogin.getText().toString();
                senha = edsenha.getText().toString();
                for (int i = 0; i < elista.size(); i++) {
                    if (login.equals(elista.get(i).getLogin()) && senha.equals(elista.get(i).getSenha())){
                        Intent e  = new Intent(getApplicationContext(), MainActivity.class);
                        e.putExtra("objlist", (Serializable) elista);
                        startActivity(e);
                    }
                    System.out.println("ID: " + elista.get(i).getId() + "  Nome: " + elista.get(i).getLogin() + "  Curso: " + elista.get(i).getSenha());
                }


            }
        }else{
            Toast.makeText(this, "Preencha os dados!!!", Toast.LENGTH_LONG).show();

        }
    }
}