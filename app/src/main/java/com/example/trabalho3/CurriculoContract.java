package com.example.trabalho3;

import android.provider.BaseColumns;

public class CurriculoContract {

    public static final class Candidato implements BaseColumns{

        public static final String TABLE_NAME = "Candidato";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_NASCIMENTO = "nascimento";
        public static final String COLUMN_TELEFONE = "telefone";
        public static final String COLUMN_PERFIL = "perfil";
        public static final String COLUMN_EMAIL = "email";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s DATE, %s TEXT, %s TEXT, %s TEXT)",TABLE_NAME, _ID, COLUMN_NOME, COLUMN_NASCIMENTO,
                COLUMN_TELEFONE,COLUMN_PERFIL,COLUMN_EMAIL);
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }

    public static final class Producao implements BaseColumns{

        public static final String TABLE_NAME = "Producao";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_INICIO = "inicio";
        public static final String COLUMN_CATEGORIA = "id_categoria";
        public static final String COLUMN_CANDIDATO = "id_candidato";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER)",TABLE_NAME, _ID, COLUMN_TITULO, COLUMN_DESCRICAO,
                COLUMN_INICIO,COLUMN_CATEGORIA,COLUMN_CANDIDATO);

        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }

    public static final class Categoria implements BaseColumns{

        public static final String TABLE_NAME = "Categoria";
        public static final String COLUMN_TITULO = "titulo";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT)",TABLE_NAME, _ID, COLUMN_TITULO);
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }

    public static final class Atividade implements BaseColumns{

        public static final String TABLE_NAME = "Atividade";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_HORAS = "horas";
        public static final String COLUMN_PRODUCAO = "id_producao";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                       "%s TEXT, %s DATE, %s INTEGER)",TABLE_NAME, _ID, COLUMN_DESCRICAO,
                COLUMN_HORAS,COLUMN_PRODUCAO);
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }

}
