package com.example.trabalho3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetalhesCandidatoActivity extends AppCompatActivity {
    CurriculoDBHelper dbHelper;
    SQLiteDatabase db;
    ProducaoAdapter adapter;
    String IDCandidato;
    Cursor cursor;

    final String[] visao = {
            CurriculoContract.Candidato._ID,
            CurriculoContract.Candidato.COLUMN_NOME,
            CurriculoContract.Candidato.COLUMN_NASCIMENTO,
            CurriculoContract.Candidato.COLUMN_PERFIL,
            CurriculoContract.Candidato.COLUMN_TELEFONE,
            CurriculoContract.Candidato.COLUMN_EMAIL
    };

    final String[] visaoProducoes = {
            CurriculoContract.Producao._ID,
            CurriculoContract.Producao.COLUMN_TITULO,
            CurriculoContract.Producao.COLUMN_DESCRICAO,
            CurriculoContract.Producao.COLUMN_INICIO,
            CurriculoContract.Producao.COLUMN_CATEGORIA,
            CurriculoContract.Producao.COLUMN_CANDIDATO
    };

    public static final int NOVA_PRODUCAO = 2;
    public static final int NOVA_ATIVIDADE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_candidato);


        dbHelper = new CurriculoDBHelper(this);
        db = dbHelper.getReadableDatabase();

        IDCandidato = getIntent().getStringExtra("IDCandidato");


        final EditText nome = findViewById(R.id.editAltNome);
        final EditText nascimento = findViewById(R.id.editAltNascimento);
        final EditText perfil = findViewById(R.id.editAltPerfil);
        final EditText telefone = findViewById(R.id.editAltTelefone);
        final EditText email = findViewById(R.id.editAltEmail);
        Button btnConfirmar = findViewById(R.id.buttonEditar);

        Button btnNovaProducao = findViewById(R.id.buttonNovaProducao);

        btnNovaProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, NovaProducaoActivity.class);
                startActivityForResult(intent, NOVA_PRODUCAO);
            }
        });

        final String select = CurriculoContract.Candidato._ID + " = ?";
        final String[] selectArgs = {IDCandidato};
        cursor = db.query(CurriculoContract.Candidato.TABLE_NAME, visao, select, selectArgs, null, null, null);

        int idxNome = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_NOME);
        int idxNascimento = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_NASCIMENTO);
        int idxPerfil = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_PERFIL);
        int idxTelefone = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_TELEFONE);
        int idxEmail = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_EMAIL);

        cursor.moveToFirst();
        nome.setText(cursor.getString(idxNome));
        nascimento.setText(cursor.getString(idxNascimento));
        perfil.setText(cursor.getString(idxPerfil));
        telefone.setText(cursor.getString(idxTelefone));
        email.setText(cursor.getString(idxEmail));

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(CurriculoContract.Candidato.COLUMN_NOME, nome.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_NASCIMENTO, nascimento.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_PERFIL, perfil.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_TELEFONE, telefone.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_EMAIL, email.getText().toString());

                long id = db.update(CurriculoContract.Candidato.TABLE_NAME, values, select, selectArgs);

                finish();
            }
        });



        RecyclerView rv = findViewById(R.id.ListaProducoes);

        Cursor c = db.query(CurriculoContract.Producao.TABLE_NAME, visaoProducoes, null, null, null, null, null);
        adapter = new ProducaoAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new ProducaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView txtID = (TextView) itemView.findViewById(R.id.textIDProducao);
                Intent intent = new Intent(DetalhesCandidatoActivity.this, DetalhesProducaoActivity.class);
                intent.putExtra("IDProducao", txtID.getText().toString());
                startActivityForResult(intent, NOVA_ATIVIDADE);

            }
        });

    }
}
