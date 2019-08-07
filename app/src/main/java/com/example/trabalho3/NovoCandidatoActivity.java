package com.example.trabalho3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NovoCandidatoActivity extends AppCompatActivity {
    CurriculoDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_candidato);

        dbHelper = new CurriculoDBHelper(this);

        final EditText nome = findViewById(R.id.editNovoNome);
        final EditText nascimento = findViewById(R.id.editNovoNascimento);
        final EditText perfil = findViewById(R.id.editNovoPerfil);
        final EditText telefone = findViewById(R.id.editNovoTelefone);
        final EditText email = findViewById(R.id.editNovoEmail);
        Button btnConfirmar = findViewById(R.id.buttonSalvar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultado = new Intent();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(CurriculoContract.Candidato.COLUMN_NOME, nome.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_NASCIMENTO, nascimento.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_PERFIL, perfil.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_TELEFONE, telefone.getText().toString());
                values.put(CurriculoContract.Candidato.COLUMN_EMAIL, email.getText().toString());

                long id = db.insert(CurriculoContract.Candidato.TABLE_NAME, null, values);
                Toast.makeText(NovoCandidatoActivity.this, "id: "+id, Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
