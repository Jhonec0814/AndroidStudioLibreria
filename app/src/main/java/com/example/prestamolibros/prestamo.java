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

public class prestamo extends AppCompatActivity {

    EditText jetCodigoPrestamo,jetFecha,jetNombreCliente,jetCodigoLibro;
    Button jbtnAdicionar,jbtnConsultar,jbtnEliminar,jbtnRegresar;
    String codigoPrestamo, fecha, nombreCliente, codigoLibro;
    long sw, resp, respAnulado;
    boolean existeLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo);

        getSupportActionBar().hide();

        jetCodigoPrestamo=findViewById(R.id.etCodigoPrestamo);
        jetFecha=findViewById(R.id.etFecha);
        jetNombreCliente=findViewById(R.id.etNombreCliente);
        jetCodigoLibro=findViewById(R.id.etCodigoLibro);
        jbtnAdicionar=findViewById(R.id.btnAdicionar);
        jbtnConsultar=findViewById(R.id.btnConsultar);
        jbtnEliminar=findViewById(R.id.btnEliminar);
        jbtnRegresar=findViewById(R.id.btnRegresar);





    }
    public void Adicionar(View view){


        codigoPrestamo=jetCodigoPrestamo.getText().toString();
        fecha=jetFecha.getText().toString();
        nombreCliente=jetNombreCliente.getText().toString();
        codigoLibro=jetCodigoLibro.getText().toString();

        if(codigoPrestamo.isEmpty() || fecha.isEmpty() || nombreCliente.isEmpty() || codigoLibro.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetCodigoPrestamo.requestFocus();
        }else{
            SqlConexion admin = new SqlConexion(this, "libreria1.db", null, 1);
            SQLiteDatabase leer = admin.getReadableDatabase();
           Cursor libro = leer.rawQuery("select * from TblLibro where codigoLibro='" +  codigoLibro + "'", null);


            if (libro.moveToNext()) {
                existeLibro = true;
            }
            leer.close();

            if (existeLibro == true) {
                SqlConexion admin1 = new SqlConexion(this, "libreria1.db", null, 1);
                SQLiteDatabase escribir = admin1.getWritableDatabase();

                ContentValues registro = new ContentValues();
                registro.put("codigoPrestamo", codigoPrestamo);
                registro.put("fecha", fecha);
                registro.put("nomCliente", nombreCliente);
                registro.put("codigoLibro", codigoLibro);

                ConsultarPrestamo();
                if(sw==1){
                    sw=0;
                    resp=escribir.update("TblPrestamo", registro, "codigoPrestamo='"+codigoPrestamo+"'", null);
                }else{
                    resp = escribir.insert("TblPrestamo", null, registro);
                }

                if (resp > 0) {

                    Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show();

                    SqlConexion admin2 = new SqlConexion(this, "libreria1.db", null, 1);
                    SQLiteDatabase escribir2 = admin2.getWritableDatabase();

                    ContentValues registroAnulado = new ContentValues();
                    registroAnulado.put("codigoLibro", codigoLibro);
                    registroAnulado.put("activo", "no");

                    respAnulado = escribir2.update("TblLibro", registroAnulado, "codigoLibro='" + codigoLibro + "'", null);
                    if (respAnulado > 0) {
                        Toast.makeText(this, "libro anulado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error al anular el libro", Toast.LENGTH_SHORT).show();
                    }
                    escribir2.close();
                    Limpiar_campos();
                } else {
                    Toast.makeText(this, "Error en guardar el prestamo", Toast.LENGTH_SHORT).show();
                }//guardando prestamo
                escribir.close();
            }//si existe el libro
        }

    }


    public void Consulta_prestamo(View view){
        ConsultarPrestamo();
    }


    public void ConsultarPrestamo(){

        codigoPrestamo=jetCodigoPrestamo.getText().toString();
        if (codigoPrestamo.isEmpty()){
            Toast.makeText(this, "El codigo del prestamo es necesario", Toast.LENGTH_SHORT).show();
            jetCodigoPrestamo.requestFocus();
        }
        else{
            SqlConexion admin=new SqlConexion(this,"libreria1.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblPrestamo where codigoPrestamo='" + codigoPrestamo + "'",null);
            if (fila.moveToNext()){
                sw=1;
                jetFecha.setText(fila.getString(1));
                jetNombreCliente.setText(fila.getString(2));
                jetCodigoLibro.setText(fila.getString(3));
            }else{
                Toast.makeText(this, "Prestamo no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }


    public void AnularPrestamo(View view){
        ConsultarPrestamo();
        if (sw == 1){
            String identificacion=jetCodigoPrestamo.getText().toString();
            SqlConexion admin=new SqlConexion(this,"libreria1.db",null,1);
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
        jetNombreCliente.setText("");
        jetFecha.setText("");
        jetCodigoPrestamo.requestFocus();
    }
    public void RegresarPrestamo(View view){
       Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }



}
