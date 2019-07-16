package com.example.trabalho3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurriculoDBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Agenda.db";

    public CurriculoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CurriculoContract.Categoria.CREATE_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Atividade.CREATE_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Candidato.CREATE_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Producao.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CurriculoContract.Categoria.DROP_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Atividade.DROP_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Producao.DROP_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Candidato.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
