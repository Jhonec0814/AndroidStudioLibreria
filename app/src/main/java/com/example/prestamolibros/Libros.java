package com.example.prestamolibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Libros extends AppCompatActivity {

    Button jbtnAdicionarLibro, jbtnAdicionarConsultar, jbtnRegresar;
    TextView jtvGaleriaLibros;
    EditText jetCodigoGaleriaLibro,jetTituloLibro,jetNombreAutor,jetGeneroLibro;
    String codigo, titulo, nombreAutor, genero;
    long sw, resp, respAnulado;
    boolean validacion1, validacion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        jtvGaleriaLibros = findViewById(R.id.tvGaleriaLibros);
        jetCodigoGaleriaLibro = findViewById(R.id.etCodigoGaleriaLibro);
        jetTituloLibro = findViewById(R.id.etTituloLibro);
        jetNombreAutor = findViewById(R.id.etNombreAutor);
        jetGeneroLibro = findViewById(R.id.etGeneroLibro);
        jbtnAdicionarLibro = findViewById(R.id.btnAdicionarLibro);
        jbtnAdicionarConsultar = findViewById(R.id.btnAdicionarConsultar);
        jbtnRegresar = findViewById(R.id.btnRegresar);
        sw=0;
        validacion1=false;
        validacion2=false;
    }

    public void Guardar(View view){

        titulo=jetTituloLibro.getText().toString();
        codigo=jetCodigoGaleriaLibro.getText().toString();
        nombreAutor=jetNombreAutor.getText().toString();
        genero=jetGeneroLibro.getText().toString();

        if(titulo.isEmpty() || codigo.isEmpty() || nombreAutor.isEmpty() || genero.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetCodigoGaleriaLibro.requestFocus();
        }else {

            SqlConexion admin1 = new SqlConexion(this, "libreria.db", null, 1);
            SQLiteDatabase escribir = admin1.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("codigoLibro", codigo);
            registro.put("tituloLibro", titulo);
            registro.put("autor", nombreAutor);
            registro.put("genero", genero);
            resp = escribir.insert("TblLibro", null, registro);
            if (resp > 0) {
                Toast.makeText(this, "Libro guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error en guardar el libro", Toast.LENGTH_SHORT).show();
            }
            escribir.close();

        }
    }






    public void Consulta_libro(View view){
        ConsultarLibro();
    }

    public void ConsultarLibro(){

        codigo=jetCodigoGaleriaLibro.getText().toString();
        if (codigo.isEmpty()){
            Toast.makeText(this, "El codigo del libro es necesario", Toast.LENGTH_SHORT).show();
            jetCodigoGaleriaLibro.requestFocus();
        }
        else{
            SqlConexion admin=new SqlConexion(this,"libreria.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblLibro where codigoLibro='" + codigo + "'",null);
            if (fila.moveToNext()){
                sw=1;
                jetTituloLibro.setText(fila.getString(1));
                jetNombreAutor.setText(fila.getString(2));
                jetGeneroLibro.setText(fila.getString(3));
            }
            else{
                Toast.makeText(this, "Libro no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void Regresar(View view){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    public void Limpiar_campos(){
        sw=0;
        jetCodigoGaleriaLibro.setText("");
        jetTituloLibro.setText("");
        jetNombreAutor.setText("");
        jetGeneroLibro.setText("");
        validacion1=false;
        validacion2=false;
        jetCodigoGaleriaLibro.requestFocus();
    }
}

