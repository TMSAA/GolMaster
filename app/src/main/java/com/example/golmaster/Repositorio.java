package com.example.golmaster;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Repositorio {

    private final FirebaseFirestore firestore;

    public Repositorio() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void cargarJornada(int jornadaId, Callback<List<Partido>> callback) {
        firestore.collection("Jornadas").whereEqualTo("Id", jornadaId).get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        Jornada jornada = querySnapshot.getDocuments().get(0).toObject(Jornada.class);
                        if (jornada != null) {
                            // Filtrar partidos por el Id de Jornada
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

    public void cargarPartidosConEquiposPorJornada(int idJornada, Callback<List<Partido>> callback) {
        firestore.collection("Partidos").whereEqualTo("IdJornada", idJornada).get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        List<Partido> partidos = new ArrayList<>();
                        List<Task<Void>> tareas = new ArrayList<>();

                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            Partido partido = document.toObject(Partido.class);
                            if (partido != null) {
                                // Crear una tarea para manejar las consultas de equipo
                                TaskCompletionSource<Void> tarea = new TaskCompletionSource<>();
                                tareas.add(tarea.getTask());

                                // Obtener equipo local y visitante en paralelo
                                firestore.collection("Equipos").whereEqualTo("Id", partido.getEquipoLocal()).get()
                                        .addOnSuccessListener(localSnapshot -> {
                                            if (!localSnapshot.isEmpty()) {
                                                // Obtener el equipo local
                                                Equipo equipoLocal = localSnapshot.getDocuments().get(0).toObject(Equipo.class);
                                                partido.setNombreEquipoLocal(equipoLocal.getNombre());
                                            }

                                            // Consultar el equipo visitante
                                            firestore.collection("Equipos").whereEqualTo("Id", partido.getEquipoVisitante()).get()
                                                    .addOnSuccessListener(visitanteSnapshot -> {
                                                        if (!visitanteSnapshot.isEmpty()) {
                                                            // Obtener el equipo visitante
                                                            Equipo equipoVisitante = visitanteSnapshot.getDocuments().get(0).toObject(Equipo.class);
                                                            partido.setNombreEquipoVisitante(equipoVisitante.getNombre());
                                                        }
                                                        // Agregar partido completo a la lista
                                                        partidos.add(partido);
                                                        tarea.setResult(null); // Marca la tarea como completada
                                                    })
                                                    .addOnFailureListener(tarea::setException); // Error en equipo visitante
                                        })
                                        .addOnFailureListener(tarea::setException); // Error en equipo local
                            }
                        }

                        // Esperar a que todas las tareas de consultas se completen
                        Tasks.whenAllComplete(tareas).addOnSuccessListener(aVoid -> {
                            // Ordenar partidos por fecha antes de retornar
                            Collections.sort(partidos, new Comparator<Partido>() {
                                @Override
                                public int compare(Partido p1, Partido p2) {
                                    if (p1.getHorario() == null || p2.getHorario() == null) {
                                        return 0; // Evitar errores con fechas nulas
                                    }
                                    return p1.getHorario().toDate().compareTo(p2.getHorario().toDate());
                                }
                            });

                            callback.onSuccess(partidos); // Retornar los partidos con los nombres de los equipos
                        }).addOnFailureListener(callback::onFailure);
                    } else {
                        callback.onFailure(new Exception("No se encontraron partidos para la jornada " + idJornada));
                    }
                })
                .addOnFailureListener(callback::onFailure); // Error en la consulta de partidos
    }

    public void cargarJugadores(List<String> idJugadores, Callback<List<Jugador>> callback) {
        List<Jugador> jugadores = new ArrayList<>();

        for (String idJugador : idJugadores) {
            firestore.collection("Jugadores").document(idJugador).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Jugador jugador = documentSnapshot.toObject(Jugador.class);
                        if (jugador != null) {
                            jugadores.add(jugador);

                            if (jugadores.size() == idJugadores.size()) {
                                callback.onSuccess(jugadores);
                            }
                        }
                    })
                    .addOnFailureListener(e -> callback.onFailure(e));
        }
    }
}
