package com.example.trabalho3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetalhesProducaoActivity extends AppCompatActivity {
    CurriculoDBHelper dbHelper;
    SQLiteDatabase db;
    ProducaoAdapter adapter;
    AtividadeAdapter atividadeAdapter;
    String IDProducao;
    Cursor cursor;


    final String[] visaoProducoes = {
            CurriculoContract.Producao._ID,
            CurriculoContract.Producao.COLUMN_TITULO,
            CurriculoContract.Producao.COLUMN_DESCRICAO,
            CurriculoContract.Producao.COLUMN_INICIO,
            CurriculoContract.Producao.COLUMN_CATEGORIA,
    };
    final String[] visaoAtividade = {
            CurriculoContract.Atividade._ID,
            CurriculoContract.Atividade.COLUMN_HORAS,
            CurriculoContract.Atividade.COLUMN_DESCRICAO,
    };

    public static final int NOVA_ATIVIDADE = 3;
    public static final int DETALHES_ATIVIDADE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_producao);


        dbHelper = new CurriculoDBHelper(this);
        db = dbHelper.getReadableDatabase();

        IDProducao= getIntent().getStringExtra("IDProducao");


        final EditText titulo = findViewById(R.id.editAltTituloProd);
        final EditText descricao = findViewById(R.id.editAltDescProd);
        final EditText dt_inicio = findViewById(R.id.editAltDtInicioProd);
        final Spinner categoria = findViewById(R.id.spinnerAltCatProd);

        Button btnSalvarAlt = findViewById(R.id.buttonSalvarAltProducao);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getCategorias());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(arrayAdapter);


        final String select = CurriculoContract.Producao._ID + " = ?";
        final String[] selectArgs = {IDProducao};
        cursor = db.query(CurriculoContract.Producao.TABLE_NAME, visaoProducoes, select, selectArgs, null, null, null);

        int idxTitulo= cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_TITULO);
        int idxDescricao = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_DESCRICAO);
        int idxDtInicio = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_INICIO);
        int idxCategoria = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_CATEGORIA);

        cursor.moveToFirst();
        titulo.setText(cursor.getString(idxTitulo));
        descricao.setText(cursor.getString(idxDescricao));
        dt_inicio.setText(cursor.getString(idxDtInicio));
        categoria.setSelection(arrayAdapter.getPosition(dbHelper.getCategoria(cursor.getString(idxCategoria))));



        btnSalvarAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(CurriculoContract.Producao.COLUMN_TITULO, titulo.getText().toString());
                values.put(CurriculoContract.Producao.COLUMN_DESCRICAO, descricao.getText().toString());
                values.put(CurriculoContract.Producao.COLUMN_INICIO, dt_inicio.getText().toString());
                values.put(CurriculoContract.Producao.COLUMN_CATEGORIA, dbHelper.getCategoriaID(categoria.getSelectedItem().toString()));

                long id = db.update(CurriculoContract.Producao.TABLE_NAME, values, select, selectArgs);

                finish();
            }
        });


        RecyclerView rv = findViewById(R.id.ListaAtividades);

        Cursor c = db.query(CurriculoContract.Atividade.TABLE_NAME, visaoAtividade, null, null, null, null, null);
        atividadeAdapter = new AtividadeAdapter(c);
        rv.setAdapter(atividadeAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

          atividadeAdapter.setOnItemClickListener(new AtividadeAdapter.OnItemClickListener() {
              @Override
              public void onItemClick(View itemView, int position) {
                  TextView txtID = (TextView) itemView.findViewById(R.id.textIDAtividade);
                  Intent intent = new Intent(DetalhesProducaoActivity.this, AtividadeActivity.class);
                  intent.putExtra("IDAtividade", txtID.getText().toString());
                  startActivityForResult(intent, DETALHES_ATIVIDADE);

              }
          });


        Button btnNovaAtividade = findViewById(R.id.buttonNovaAtividade);

        btnNovaAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesProducaoActivity.this, NovaAtividadeActivity.class);
                startActivityForResult(intent, NOVA_ATIVIDADE);
            }
        });
    }
}
