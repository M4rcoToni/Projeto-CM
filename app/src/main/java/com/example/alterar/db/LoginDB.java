package com.example.alterar.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LoginDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    private static final String NOME_BANCO = "login.sqlite";
    private static final int VERSION  = 1;

    public LoginDB(Context context){
        super(context, NOME_BANCO, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela login");

        db.execSQL("create table if not exists login(id_ integer primary key autoincrement, login text, senha text);");

        Log.d(TAG, "Tabela Criada");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not used
    }
    public int delete(Login e){
        SQLiteDatabase db =  getWritableDatabase();
        try {
            String iD =  String.valueOf(e.getId());
            String[] whereArgs = new String[]{iD};
            int count =db.delete("login", "id_=?", whereArgs);
            return count;

        }catch (Exception ex){
            System.out.println( ex);
        }finally {
            db.close();
        }
        return 0;
    }

    public long save(Login e){
        long id = e.getId();
        SQLiteDatabase db = getReadableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("login", e.getLogin());
            values.put("senha", e.getSenha());
            if (id != 0){
                //update
                String iD = String.valueOf(e.getId());
                String[] whereArgs = new String[]{iD};
                int count = db.update("login", values, "id_=?",whereArgs);
                return count;
            }else{
                //insert
                id = db.insert("login", "", values);
                return id;
            }
        }catch (Exception ex){
            System.out.println(ex);
        }finally {
            db.close();
        }
        return 0;
    }



    public List<Login> findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try{
            Cursor c = db.rawQuery("SELECT * FROM login", null);
            return toList(c);
        }finally {

        }
        //return null;
    }
    @SuppressLint("Range")
    public List<Login> toList(Cursor c){
        List<Login> logins = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                Login e =  new Login();
                e.setId(c.getInt(c.getColumnIndex("id_")));
                e.setLogin(c.getString(c.getColumnIndex("login")));
                e.setSenha(c.getString(c.getColumnIndex("senha")));
                logins.add(e);
            } while (c.moveToNext());
        }
        return logins;
    }

}
