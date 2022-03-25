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

public class Prestamos extends AppCompatActivity {

    TextView jtvPrestamo;
    EditText jetCodigoPrestamo, jetFecha, jetNombreCliente,jetCodigoLibro,jetTituloLibro;
    Button jbtnAdicionarPrestamos, jbtnConsultarPrestamos, jbtnModificar, jbtnEliminar , jbtnRegresarPrestamos;
    String codigo, fecha, nombreCliente, codigoLibro,tituloLibro;
    long sw, resp, respAnulado;
    boolean validacion1, validacion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);
        getSupportActionBar().hide();

        jtvPrestamo = findViewById(R.id.tvPrestamo);
        jetCodigoPrestamo = findViewById(R.id.etCodigoPrestamo);
        jetFecha = findViewById(R.id.etFecha);
        jetNombreCliente = findViewById(R.id.etNombreCliente);
        jetCodigoLibro = findViewById(R.id.etCodigoLibro);
        jbtnAdicionarPrestamos = findViewById(R.id.btnAdicionarPrestamos);
        jbtnConsultarPrestamos = findViewById(R.id.btnConsultarPrestamos);
        jbtnModificar = findViewById(R.id.btnModificar);
        jbtnEliminar = findViewById(R.id.btnEliminar);
        jbtnRegresarPrestamos = findViewById(R.id.btnRegresarPrestamos);
        jetTituloLibro = findViewById(R.id.etTituloLibro);
    }

    public void GuardarPrestamo(View view){

        fecha=jetFecha.getText().toString();
        codigo=jetCodigoPrestamo.getText().toString();
        nombreCliente=jetNombreCliente.getText().toString();
        codigoLibro=jetCodigoLibro.getText().toString();
        tituloLibro= jetTituloLibro.getText().toString();

        if(fecha.isEmpty() || codigo.isEmpty() || nombreCliente.isEmpty() || codigoLibro.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetCodigoPrestamo.requestFocus();
        }else {


            SqlConexion admin = new SqlConexion(this, "libreria.db", null, 1);
            SQLiteDatabase leer = admin.getReadableDatabase();
            Cursor libro = leer.rawQuery("select * from TblLibro where tituloLibro='" + tituloLibro + "'", null);
            Cursor prestamo = leer.rawQuery("select * from TblPrestamo where codigoPrestamo='" + nombreCliente + "'", null);


            if (libro.moveToNext()) {
                validacion1 = true;
            }

            if(prestamo.moveToNext()){
                if ("si".equals(libro.getString(5))) {
                    validacion2 = true;
                }
            }

            leer.close();

            //Guardar riegistro
            if (validacion1 == true) {
                if (validacion2 == true) {
                    SqlConexion admin1 = new SqlConexion(this, "libreria.db", null, 1);
                    SQLiteDatabase escribir = admin1.getWritableDatabase();

                    ContentValues registro = new ContentValues();
                    registro.put("codigoPrestamo", codigo);
                    registro.put("fecha", fecha);
                    registro.put("nomCliente", nombreCliente);
                    registro.put("codigoLibro", codigoLibro);
                    resp = escribir.insert("TblPrestamo", null, registro);
                    if (resp > 0) {

                        Toast.makeText(this, "Prestamo guardado", Toast.LENGTH_SHORT).show();

                        ContentValues registroAnulado = new ContentValues();
                        registroAnulado.put("codigoPrestamo", codigo);
                        registroAnulado.put("activo", "no");
                        respAnulado = escribir.update("TblPrestamo", registroAnulado, "codigoPrestamo='" + codigo + "'", null);
                        if (respAnulado > 0) {
                            Toast.makeText(this, "Prestamo anulado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al anular el prestamo", Toast.LENGTH_SHORT).show();
                        }
                        Limpiar_campos();
                    } else {
                        Toast.makeText(this, "Error en guardar el prestamo", Toast.LENGTH_SHORT).show();
                    }
                    escribir.close();
                } else {
                    Toast.makeText(this, "El prestamo no esta disponible para realizarse", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El prestamo no se encuentra en el  registro", Toast.LENGTH_SHORT).show();
            }
        }   }


    public void Consulta_prestamo(View view){
        ConsultarPrestamo();
    }

    public void ConsultarPrestamo(){

        codigo=jetCodigoPrestamo.getText().toString();
        if (codigo.isEmpty()){
            Toast.makeText(this, "El codigo del prestamo es necesario", Toast.LENGTH_SHORT).show();
            jetCodigoPrestamo.requestFocus();
        }
        else{
            SqlConexion admin=new SqlConexion(this,"libreria.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblPrestamo where codigoPrestamo='" + codigo + "'",null);
            if (fila.moveToNext()){
                sw=1;
                jetTituloLibro.setText(fila.getString(1));
                jetNombreCliente.setText(fila.getString(2));
                jetFecha.setText(fila.getString(3));
            }
            else{
                Toast.makeText(this, "Prestamo no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void AnularPrestamo(View view){
        ConsultarPrestamo();
        if (sw == 1){
            String identificacion=jetCodigoPrestamo.getText().toString();
            SqlConexion admin=new SqlConexion(this,"libreria.db",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues dato=new ContentValues();
            dato.put("codigoPrestamo",identificacion);
            dato.put("activo","no");
            resp=db.update("TblPrestamo",dato,"codigoPrestamo='" + identificacion + "'",null);
            if (resp > 0){
                Toast.makeText(this, "Prestamo anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else {
                Toast.makeText(this, "Error anulando el prestamo", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else{
            Toast.makeText(this, "El codigo del prestamo no fue encontrado", Toast.LENGTH_SHORT).show();
        }

    }


    public void Limpiar_campos(){
        sw=0;
        jetCodigoPrestamo.setText("");
        jetTituloLibro.setText("");
        jetNombreCliente.setText("");
        jetFecha.setText("");
        validacion1=false;
        validacion2=false;
        jetCodigoPrestamo.requestFocus();
    }
    public void RegresarPrestamo(View view){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}