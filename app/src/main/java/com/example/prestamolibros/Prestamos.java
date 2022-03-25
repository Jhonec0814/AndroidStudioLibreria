package com.example.prestamolibros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Prestamos extends AppCompatActivity {

    TextView jtvPrestamo;
    EditText jetCodigoPrestamo, jetFecha, jetNombreCliente,etCodigoLibro;
    Button jbtnAdicionarPrestamos, jbtnConsultarPrestamos, jbtnModificar, jbtnEliminar , jbtnRegresarPrestamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);
        getSupportActionBar().hide();

        jtvPrestamo = findViewById(R.id.tvPrestamo);
        jetCodigoPrestamo = findViewById(R.id.etCodigoPrestamo);
        jetFecha = findViewById(R.id.etFecha);
        jetNombreCliente = findViewById(R.id.etNombreCliente);
        etCodigoLibro = findViewById(R.id.etCodigoLibro);
        jbtnAdicionarPrestamos = findViewById(R.id.btnAdicionarPrestamos);
        jbtnConsultarPrestamos = findViewById(R.id.btnConsultarPrestamos);
        jbtnModificar = findViewById(R.id.btnModificar);
        jbtnEliminar = findViewById(R.id.btnEliminar);
        jbtnRegresarPrestamos = findViewById(R.id.btnRegresarPrestamos);
    }
}