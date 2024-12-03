package com.example.golmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuJornada extends AppCompatActivity {

    private RecyclerView rvPartidos;
    private PartidoAdapter adapterPartido;
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jornada);

        rvPartidos = findViewById(R.id.rvPartidos);
        Spinner spinnerOptions = findViewById(R.id.spinnerOptions);
        repositorio = new Repositorio();

        rvPartidos.setLayoutManager(new LinearLayoutManager(this));
        adapterPartido = new PartidoAdapter(this,new ArrayList<>());
        rvPartidos.setAdapter(adapterPartido);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Jornadas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        cargarPartidosPorJornada(1);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int jornadaSeleccionada = position + 1;
                cargarPartidosPorJornada(jornadaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void cargarPartidosPorJornada(int idJornada) {
        repositorio.cargarJornada(idJornada, new Callback<List<ElementoLista>>() {
            @Override
            public void onSuccess(List<ElementoLista> elementos) {
                adapterPartido.actualizarLista(elementos);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Error", "Error al cargar jornada: " + e.getMessage());
                Toast.makeText(MenuJornada.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
