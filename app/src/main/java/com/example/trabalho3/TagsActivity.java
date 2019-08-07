package com.example.trabalho3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TagsActivity extends AppCompatActivity {
    CurriculoDBHelper dbHelper;
    SQLiteDatabase db;
    CategoriaAdapter adapter;

    Cursor c;
    String[] visao = {
            CurriculoContract.Categoria._ID,
            CurriculoContract.Categoria.COLUMN_TITULO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        dbHelper = new CurriculoDBHelper(this);
        db = dbHelper.getReadableDatabase();

        final EditText tag = findViewById(R.id.editNovaTag);
        Button btnConfirmar = findViewById(R.id.buttonSalvarTag);

        RecyclerView lista_tags = findViewById(R.id.ListaTags);


        c = db.query(CurriculoContract.Categoria.TABLE_NAME, visao, null, null, null, null, null);
        adapter = new CategoriaAdapter(c);
        lista_tags.setAdapter(adapter);
        lista_tags.setLayoutManager(new LinearLayoutManager(this));

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(CurriculoContract.Categoria.COLUMN_TITULO, tag.getText().toString());

                long id = db.insert(CurriculoContract.Categoria.TABLE_NAME, null, values);
                Toast.makeText(TagsActivity.this, "id: " + id, Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}
