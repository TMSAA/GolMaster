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

        // Inicializar componentes
        rvPartidos = findViewById(R.id.rvPartidos);
        Spinner spinnerOptions = findViewById(R.id.spinnerOptions);
        repositorio = new Repositorio();

        // Configurar RecyclerView
        rvPartidos.setLayoutManager(new LinearLayoutManager(this));
        adapterPartido = new PartidoAdapter(new ArrayList<>()); // Inicializa el adaptador con una lista vacía
        rvPartidos.setAdapter(adapterPartido);

        // Configurar el Spinner con las opciones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Jornadas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        // Listener para el Spinner
        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                Toast.makeText(MenuJornada.this, "Seleccionado: " + selectedOption, Toast.LENGTH_SHORT).show();

                // Convertir la opción seleccionada en un número de jornada
                int jornadaSeleccionada = position + 1; // Supone que las jornadas están numeradas consecutivamente
                cargarPartidosPorJornada(jornadaSeleccionada); // Cargar partidos para la jornada seleccionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo si no se selecciona ninguna opción
            }
        });

        // Cargar partidos de la jornada inicial (puedes cambiar por la primera jornada que quieras mostrar)
        cargarPartidosPorJornada(1);
    }

    private void cargarPartidosPorJornada(int idJornada) {
        repositorio.cargarJornada(idJornada, new Callback<List<Partido>>() {
            @Override
            public void onSuccess(List<Partido> partidos) {
                Log.d("Firestore", "Partidos obtenidos para jornada " + idJornada + ": " + partidos.size());
                for (Partido partido : partidos) {
                    Log.d("Partido", partido.getNombreEquipoLocal() + " vs " + partido.getNombreEquipoVisitante());
                }

                // Preparar los elementos para el adaptador agrupados por fecha
                List<ElementoLista> elementos = prepararElementosPorFecha(partidos);

                // Actualizar los datos del adaptador y notificar cambios
                adapterPartido.actualizarLista(elementos);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Error", "Error al cargar partidos de la jornada: " + e.getMessage());
                Toast.makeText(MenuJornada.this, "Error al cargar los partidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ElementoLista> prepararElementosPorFecha(List<Partido> partidos) {
        List<ElementoLista> elementos = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        String fechaActual = "";

        for (Partido partido : partidos) {
            String fecha = sdf.format(partido.getHorario().toDate());
            if (!fecha.equals(fechaActual)) {
                fechaActual = fecha;
                elementos.add(new ElementoLista(ElementoLista.TIPO_FECHA, fecha, null));
            }
            elementos.add(new ElementoLista(ElementoLista.TIPO_PARTIDO, null, partido));
        }

        return elementos;
    }
}
