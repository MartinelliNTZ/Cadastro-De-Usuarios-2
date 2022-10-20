package com.example.cadastrodeusuarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cadastrodeusuarios.helpers.DbHelper;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btNovo;
    private ListView listView;
    private SQLiteDatabase dados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkage();
        criarBD();



    }
    private void linkage(){
        btNovo= findViewById(R.id.btNovo);
        listView= findViewById(R.id.listView);
    }
    public void bt (View v){
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    public void criarBD(){
        try {

            dados = openOrCreateDatabase("usuarios", MODE_PRIVATE,null);
            String sql = "CREATE TABLE IF NOT EXISTS pessoas    (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL); ";

            dados.execSQL(sql);



            Log.i("INFO DB","Sucesso ao criar:   ");

        }catch (Exception e){
            Log.i("INFO DB","ERRO ao criar tabela:   "+e.getMessage());

        }
    }
}