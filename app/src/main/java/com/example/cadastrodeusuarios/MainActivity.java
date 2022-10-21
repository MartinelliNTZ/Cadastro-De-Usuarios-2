package com.example.cadastrodeusuarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cadastrodeusuarios.helpers.DbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btNovo;
    private ListView listView;
    private SQLiteDatabase dados;
    private final String NOME_DATABASE = "usuarios";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkage();
        criarBD();
        listarDados();



    }
    private void linkage(){
        btNovo= findViewById(R.id.btNovo);
        listView= findViewById(R.id.listView);
    }
    public void bt (View v){
        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadActivity.class));
            }
        });



     /**   Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        ContentValues cv = new ContentValues();
        cv.put("nome","matheus");
        dados.insert("pessoas",null,cv);
        cv.put("nome","maria");
        dados.insert("pessoas",null,cv);
        cv.put("nome","joao");
        dados.insert("pessoas",null,cv);
        cv.put("nome","pedro");
        dados.insert("pessoas",null,cv);
        cv.put("nome","mario");
        dados.insert("pessoas",null,cv);
        cv.put("nome","larissa");
        dados.insert("pessoas",null,cv);
        listarDados();*/
    }
    public void criarBD(){
        try {
            dados = openOrCreateDatabase(NOME_DATABASE, MODE_PRIVATE,null);
            String sql = "CREATE TABLE IF NOT EXISTS pessoas    (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL); ";
            dados.execSQL(sql);
            Log.i("INFO DB","Sucesso ao criar:   ");

        }catch (Exception e){
            Log.i("INFO DB","ERRO ao criar tabela:   "+e.getMessage());

        }
    }
    public void listarDados(){
        try {
            dados = openOrCreateDatabase(NOME_DATABASE,MODE_PRIVATE,null);
            Cursor meuCursor =dados.rawQuery("SELECT id, nome FROM pessoas",null);
            ArrayList<String> resultado = new ArrayList<String>();
            ArrayAdapter  arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    resultado
                    );
            listView.setAdapter(arrayAdapter);

            meuCursor.moveToFirst();
            while(meuCursor != null){
                resultado.add(meuCursor.getString(1));
                meuCursor.moveToNext();

            }





            Log.i("INFO DB","Sucesso ao listar dados:   ");

        }catch (Exception e){
            Log.i("INFO DB","ERRO ao listar dados :   "+e.getMessage());

        }
    }


}