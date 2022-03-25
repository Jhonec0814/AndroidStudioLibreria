package com.example.prestamolibros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SqlConexion extends SQLiteOpenHelper {


    public SqlConexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TblLibro(codigoLibro text primary key," +
                "tituloLibro text not null, autor text not null, genero text not null,activo text not null default 'si')");
        sqLiteDatabase.execSQL("CREATE TABLE TblPrestamo(codigoPrestamo text primary key," +
                "fecha text not null, nomCliente text not null, codigoLibro text not null,activo text not null default 'si',"+
                "constraint pkPrestamo foreign key (codigoLibro) references TblLibro(codigoLibro))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE TblLibro");{
            onCreate(sqLiteDatabase);
        }

        sqLiteDatabase.execSQL("DROP TABLE TblPrestamo");{
            onCreate(sqLiteDatabase);
        }



    }


}

