package com.example.golmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvEventos;
    private EventoAdapter eventoAdapter;
    private List<Evento> listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton buttonAtras = findViewById(R.id.button_arrow_back);
        ImageView ivEscudoLocal = findViewById(R.id.ivEscudoLocal);
        ImageView ivEscudoVisitante = findViewById(R.id.ivEscudoVisitante);
        TextView tvResultado = findViewById(R.id.tvResultado);
        TextView tvFechaHora = findViewById(R.id.tvFechaHora);

        rvEventos = findViewById(R.id.rvEventos);
        listaEventos = new ArrayList<>();
        eventoAdapter = new EventoAdapter(this, listaEventos);

        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setAdapter(eventoAdapter);

        buttonAtras.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuJornada.class);
            startActivity(intent);
            finish();
        });

        Partido partido = getIntent().getParcelableExtra("partido");

        if (partido != null) {
            Log.d("Partido", "Datos del partido: Goles Local - "
                    + partido.getGolesEquipoLocal() + ", Goles Visitante - "
                    + partido.getGolesEquipoVisitante());

            Glide.with(this)
                    .load("file:///android_asset/escudos/" + partido.getRutaEscudoLocal() + ".png")
                    .into(ivEscudoLocal);

            Glide.with(this)
                    .load("file:///android_asset/escudos/" + partido.getRutaEscudoVisitante() + ".png")
                    .into(ivEscudoVisitante);

            Timestamp horario = partido.getHorario();
            if (horario != null) {
                Date partidoDate = horario.toDate();
                Date now = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMM", Locale.getDefault());

                if (now.before(partidoDate)) {
                    tvFechaHora.setVisibility(View.VISIBLE);
                    tvResultado.setVisibility(View.GONE); // Ocultar el resultado
                    tvFechaHora.setText(sdf.format(partidoDate));
                } else {
                    tvFechaHora.setVisibility(View.GONE); // Ocultar la fecha
                    tvResultado.setVisibility(View.VISIBLE); // Mostrar el resultado
                    tvResultado.setText(partido.getGolesEquipoLocal() + " - " + partido.getGolesEquipoVisitante());
                }
            } else {
                tvFechaHora.setVisibility(View.GONE);
                tvResultado.setVisibility(View.GONE);
            }
            cargarEventos(partido.getId());
        }
    }

    private void cargarEventos(int partidoId) {
        FirebaseFirestore.getInstance()
                .collection("Eventos")
                .whereEqualTo("partidoId", partidoId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaEventos.clear(); // Limpiar lista antes de agregar nuevos datos
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Evento evento = document.toObject(Evento.class);
                        if (evento != null) {
                            listaEventos.add(evento);
                        }
                    }
                    eventoAdapter.notifyDataSetChanged(); // Actualizar RecyclerView
                })
                .addOnFailureListener(e -> Log.e("Firebase", "Error al cargar eventos", e));
    }
}
