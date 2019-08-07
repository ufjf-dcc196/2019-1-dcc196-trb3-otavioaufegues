package com.example.trabalho3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    CurriculoDBHelper dbhelper;
    CandidatoAdapter adapter;
    SQLiteDatabase db;
    String[] visao = {
            CurriculoContract.Candidato._ID,
            CurriculoContract.Candidato.COLUMN_NOME,
            CurriculoContract.Candidato.COLUMN_NASCIMENTO,
            CurriculoContract.Candidato.COLUMN_PERFIL,
            CurriculoContract.Candidato.COLUMN_TELEFONE,
            CurriculoContract.Candidato.COLUMN_EMAIL,
    };
    public static final int NOVO_CANDIDATO = 1;
    public static final int DETALHES_CANDIDATO = 2;
    public static final int GERENCIAR_CATEGORIAS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView rv = findViewById(R.id.ListaCandidatos);
        Button btnNovo = findViewById(R.id.buttonNovoCandidato);
        Button btnTags = findViewById(R.id.buttonTags);

        dbhelper = new CurriculoDBHelper(this);
        db = dbhelper.getReadableDatabase();

        Cursor c = db.query(CurriculoContract.Candidato.TABLE_NAME, visao, null, null, null, null, null);
        adapter = new CandidatoAdapter(c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new CandidatoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView txtID = (TextView) itemView.findViewById(R.id.textViewId);
                Intent intent = new Intent(MainActivity.this, DetalhesCandidatoActivity.class);
                intent.putExtra("IDCandidato", txtID.getText().toString());

                startActivityForResult(intent, DETALHES_CANDIDATO);

            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NovoCandidatoActivity.class);
                startActivityForResult(intent, NOVO_CANDIDATO);

            }
        });

        btnTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TagsActivity.class);
                startActivityForResult(intent, GERENCIAR_CATEGORIAS);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        adapter.setCursor(db.query(CurriculoContract.Candidato.TABLE_NAME, visao, null, null, null, null, null));
        adapter.notifyDataSetChanged();
    }
}
