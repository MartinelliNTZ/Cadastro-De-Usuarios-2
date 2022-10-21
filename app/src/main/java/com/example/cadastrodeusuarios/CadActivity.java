package com.example.cadastrodeusuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CadActivity extends AppCompatActivity {
    private SQLiteDatabase dados;
    private TextInputEditText edtNome;
    private String nome;
    private Button btCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad);
        edtNome = findViewById(R.id.edtNome);
        btCad = findViewById(R.id.btCad);
        btCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadUserDois();
            }
        });

    }

    public void cadUser(){
        nome = edtNome.getText().toString();
        if (nome != null & !nome.isEmpty()) {
            try {
                dados = openOrCreateDatabase("usuarios", MODE_PRIVATE, null);

                String x = nome.getBytes().toString();
                ContentValues cv = new ContentValues();
                cv.put("nome", nome);
                dados.insert("pessoas", null, cv);
                Log.i("INFO DB", "Sucesso ao criar:   ");
                Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();
                finish();

            } catch (Exception e) {
                Log.i("INFO DB", "ERRO ao criar tabela:   " + e.getMessage());
                Toast.makeText(this, "Dados Inválidos", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(this, "Não pode estar vázio", Toast.LENGTH_SHORT).show();
        }

    }
    public void cadUserDois(){
        nome = edtNome.getText().toString();
        if (nome != null & !nome.isEmpty()) {
            try {
                dados = openOrCreateDatabase("usuarios", MODE_PRIVATE, null);
                String sql = "INSERT INTO pessoas (nome) VALUES (?)";
                SQLiteStatement statement = dados.compileStatement(sql);
                statement.bindString(1,nome);
                statement.executeInsert();
                dados.close();
                finish();
                Log.i("INFO DB", "Sucesso ao criar:   ");
                Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();
                finish();

            } catch (Exception e) {
                Log.i("INFO DB", "ERRO ao criar tabela:   " + e.getMessage());
                Toast.makeText(this, "Dados Inválidos", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(this, "Não pode estar vázio", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_de_voltar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcao_salvar) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}















