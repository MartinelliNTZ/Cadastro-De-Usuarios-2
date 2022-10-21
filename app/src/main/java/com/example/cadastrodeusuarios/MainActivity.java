package com.example.cadastrodeusuarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.cadastrodeusuarios.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btNovo;
    private ListView listView;
    private SQLiteDatabase dados;
    private final String NOME_DATABASE = "usuarios";
    private List<User> listaUsers= new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        listarDados();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkage();
        criarBD();
        listarDados();

        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mudarActivty();
            }
        });
        getSupportActionBar().hide();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{User usuarioSelect = listaUsers.get(i);
                    AlertDialog.Builder aler =new AlertDialog.Builder(MainActivity.this);
                    aler.setMessage("Deseja excluir a tarefa: "+usuarioSelect.getNome()+" ?");
                    aler.setTitle("Excluir itens.");
                    aler.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String[] args= {usuarioSelect.getId().toString()};
                            dados.delete("pessoas","id=?",args);
                            Toast.makeText(MainActivity.this, "Deletado.", Toast.LENGTH_SHORT).show();
                            listarDados();
                        }
                    });
                    aler.setNegativeButton("Não", null);
                    aler.setIcon(R.drawable.ic_delete);
                    aler.create();
                    aler.show();

                }catch (Exception e){
                    Log.i("INFO","Erro: "+e.getMessage());
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User tarefaSelecionada = listaUsers.get(i);
                Intent intent = new Intent(MainActivity.this, CadActivity.class);
                intent.putExtra("userSelecionado", tarefaSelecionada);
                startActivity(intent);

            }
        });



    }
    private void linkage(){
        btNovo= findViewById(R.id.btNovo);
        listView= findViewById(R.id.listView);
    }
    public void mudarActivty (){
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
                @SuppressLint("Range") Long id = meuCursor.getLong(meuCursor.getColumnIndex("id"));
                @SuppressLint("Range") String nome = meuCursor.getString(meuCursor.getColumnIndex("nome"));
                listaUsers.add(new User(id,nome));

                meuCursor.moveToNext();

            }





            Log.i("INFO DB","Sucesso ao listar dados:   ");

        }catch (Exception e){
            Log.i("INFO DB","ERRO ao listar dados :   "+e.getMessage());

        }
    }



}