package com.example.trabalho3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NovaProducaoActivity extends AppCompatActivity {
    CurriculoDBHelper dbHelper;
    String IDCandidato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_producao);

        dbHelper = new CurriculoDBHelper(this);

        IDCandidato = getIntent().getStringExtra("IDCandidato");
        final EditText titulo = findViewById(R.id.editTextTituloProd);
        final EditText descricao = findViewById(R.id.editTextDescProd);
        final EditText dt_inicio = findViewById(R.id.editTextDtInicioProd);
        final Spinner categoria = findViewById(R.id.spinnerCatProd);

        Button btnConfirmar = findViewById(R.id.buttonSalvarNovaProducao);

        categoria.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, dbHelper.getCategorias()));

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getCategorias());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categoria.setAdapter(adapter);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultado = new Intent();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(CurriculoContract.Producao.COLUMN_TITULO, titulo.getText().toString());
                values.put(CurriculoContract.Producao.COLUMN_DESCRICAO, descricao.getText().toString());
                values.put(CurriculoContract.Producao.COLUMN_INICIO, dt_inicio.getText().toString());
//                values.put(CurriculoContract.Producao.COLUMN_CATEGORIA, dbHelper.getCategoriaID(categoria.getSelectedItem().toString()));
                values.put(CurriculoContract.Producao.COLUMN_CANDIDATO, IDCandidato);

                long id = db.insert(CurriculoContract.Producao.TABLE_NAME, null, values);
                Toast.makeText(NovaProducaoActivity.this, "id: "+id, Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
