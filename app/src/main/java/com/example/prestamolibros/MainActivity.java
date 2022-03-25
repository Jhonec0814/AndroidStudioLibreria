package com.example.prestamolibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button jbtnMenuGaleriaLibros, jbtnPrestamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        jbtnMenuGaleriaLibros= findViewById(R.id.btnMenuGaleriaLibros);
        jbtnPrestamos = findViewById(R.id.btnPrestamos);
    }

    public void abrirLibros(View view){
        Intent libros = new Intent(this, Libros.class);
        startActivity(libros);
    }

    public void abrirPrestamos(View view){
        Intent prestamos = new Intent(this,Prestamos.class);
        startActivity(prestamos);
    }

    public void Consulta_libro(View view) {
    }
}


