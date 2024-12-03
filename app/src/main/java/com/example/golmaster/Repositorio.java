package com.example.golmaster;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import android.util.Log;

public class Repositorio {

    private final FirebaseFirestore firestore;

    public Repositorio() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void cargarJornada(int jornadaId, Callback<List<ElementoLista>> callback) {
        firestore.collection("Jornadas").whereEqualTo("Id", jornadaId).get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        Jornada jornada = querySnapshot.getDocuments().get(0).toObject(Jornada.class);
                        if (jornada != null) {
                            cargarPartidosConEquiposPorJornada(jornadaId, callback);
                        } else {
                            callback.onFailure(new Exception("Jornada no encontrada o datos inválidos."));
                        }
                    } else {
                        callback.onFailure(new Exception("Jornada no encontrada en la base de datos."));
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e));
    }

    public void cargarPartidosConEquiposPorJornada(int idJornada, Callback<List<ElementoLista>> callback) {
        firestore.collection("Partidos").whereEqualTo("IdJornada", idJornada).get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        List<Partido> partidos = new ArrayList<>();
                        List<Task<Void>> tareas = new ArrayList<>();

                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            Partido partido = document.toObject(Partido.class);
                            Log.d("Repositorio", "Documento Firebase: " + document.getData());
                            if (partido != null) {
                                TaskCompletionSource<Void> tarea = new TaskCompletionSource<>();
                                tareas.add(tarea.getTask());

                                firestore.collection("Equipos").whereEqualTo("Id", partido.getEquipoLocal()).get()
                                        .addOnSuccessListener(localSnapshot -> {
                                            if (!localSnapshot.isEmpty()) {
                                                Equipo equipoLocal = localSnapshot.getDocuments().get(0).toObject(Equipo.class);
                                                partido.setNombreEquipoLocal(equipoLocal.getNombre());
                                                partido.setRutaEscudoLocal(equipoLocal.getRutaEscudo());
                                            }

                                            firestore.collection("Equipos").whereEqualTo("Id", partido.getEquipoVisitante()).get()
                                                    .addOnSuccessListener(visitanteSnapshot -> {
                                                        if (!visitanteSnapshot.isEmpty()) {
                                                            Equipo equipoVisitante = visitanteSnapshot.getDocuments().get(0).toObject(Equipo.class);
                                                            partido.setNombreEquipoVisitante(equipoVisitante.getNombre());
                                                            partido.setRutaEscudoVisitante(equipoVisitante.getRutaEscudo());
                                                        }
                                                        partidos.add(partido);
                                                        tarea.setResult(null);
                                                    })
                                                    .addOnFailureListener(tarea::setException);
                                        })
                                        .addOnFailureListener(tarea::setException);
                            }
                        }

                        Tasks.whenAllComplete(tareas).addOnSuccessListener(aVoid -> {
                            List<ElementoLista> elementos = prepararElementosPorFecha(partidos);
                            callback.onSuccess(elementos);
                        }).addOnFailureListener(callback::onFailure);
                    } else {
                        callback.onFailure(new Exception("No se encontraron partidos para la jornada " + idJornada));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }
    private List<ElementoLista> prepararElementosPorFecha(List<Partido> partidos) {
        Map<String, List<Partido>> mapaPartidos = new TreeMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (Partido partido : partidos) {
            Date fechaCompleta = partido.getHorario().toDate();
            if (fechaCompleta != null) {
                String fechaSinHora = sdf.format(fechaCompleta);
                mapaPartidos.putIfAbsent(fechaSinHora, new ArrayList<>());
                mapaPartidos.get(fechaSinHora).add(partido);
            }
        }
        List<ElementoLista> elementos = new ArrayList<>();
        SimpleDateFormat fechaMostradaSdf = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());

        for (Map.Entry<String, List<Partido>> entry : mapaPartidos.entrySet()) {
            Date fecha = null;
            try {
                fecha = sdf.parse(entry.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fecha != null) {
                String fechaFormateada = fechaMostradaSdf.format(fecha);
                elementos.add(new ElementoLista(ElementoLista.TIPO_FECHA, fechaFormateada, null));
            }
            for (Partido partido : entry.getValue()) {
                elementos.add(new ElementoLista(ElementoLista.TIPO_PARTIDO, null, partido));
            }
        }

        return elementos;
    }

}
