package com.example.trabalho3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CurriculoDBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Agenda.db";

    public CurriculoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CurriculoContract.Categoria.CREATE_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Candidato.CREATE_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Producao.CREATE_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Atividade.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CurriculoContract.Categoria.DROP_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Candidato.DROP_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Producao.DROP_TABLE);
        sqLiteDatabase.execSQL(CurriculoContract.Atividade.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }


    public List<String> getCategorias(){
        List<String> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CurriculoContract.Categoria.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public String getCategoriaID(String cat){
        String selectQuery = "SELECT _ID FROM " + CurriculoContract.Categoria.TABLE_NAME + " WHERE " + CurriculoContract.Categoria.COLUMN_TITULO + "= ?";
        String[] queryArgs = {cat};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, queryArgs);
        String id = null;

        int idxID = cursor.getColumnIndexOrThrow(CurriculoContract.Categoria._ID);
        if (cursor.moveToFirst()) {
            id = cursor.getString(idxID);
        }

        cursor.close();
        db.close();

        return id;
    }

    public String getCategoria(String id){
        String selectQuery = "SELECT " + CurriculoContract.Categoria.COLUMN_TITULO + " FROM " + CurriculoContract.Categoria.TABLE_NAME + " WHERE _ID = ?";
        String[] queryArgs = {id};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, queryArgs);
        String titulo = null;

        int idxTitulo = cursor.getColumnIndexOrThrow(CurriculoContract.Categoria.COLUMN_TITULO);
        if (cursor.moveToFirst()) {
            titulo = cursor.getString(idxTitulo);
        }

        cursor.close();
        db.close();

        return titulo;
    }

}
