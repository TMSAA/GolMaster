package com.example.golmaster;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

public class Partido {

    @PropertyName("Id")
    private int id;
    @PropertyName("IdJornada")
    private int idJornada;
    @PropertyName("EquipoLocal")
    private int equipoLocal;
    @PropertyName("EquipoVisitante")
    private int equipoVisitante;
    @PropertyName("Horario")
    private Timestamp horario;
    private String nombreEquipoLocal;
    private String nombreEquipoVisitante;

    public Partido() {}

    @PropertyName("Id")
    public int getId() {
        return id;
    }
    @PropertyName("Id")
    public void setId(int id) {
        this.id = id;
    }
    @PropertyName("IdJornada")
    public int getIdJornada() {
        return idJornada;
    }
    @PropertyName("IdJornada")
    public void setIdJornada(int idJornada) {
        this.idJornada = idJornada;
    }
    @PropertyName("EquipoLocal")
    public int getEquipoLocal() {
        return equipoLocal;
    }
    @PropertyName("EquipoLocal")
    public void setEquipoLocal(int equipoLocal) {
        this.equipoLocal = equipoLocal;
    }
    @PropertyName("EquipoVisitante")
    public int getEquipoVisitante() {
        return equipoVisitante;
    }
    @PropertyName("EquipoVisitante")
    public void setEquipoVisitante(int equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }
    @PropertyName("Horario")
    public Timestamp getHorario() {
        return horario;
    }
    @PropertyName("Horario")
    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }
    public String getHorarioFormatted() {
        return (horario != null) ? horario.toDate().toString() : "Sin horario";
    }
    public String getNombreEquipoLocal() {
        return nombreEquipoLocal;
    }
    public void setNombreEquipoLocal(String nombreEquipoLocal) {
        this.nombreEquipoLocal = nombreEquipoLocal;
    }
    public String getNombreEquipoVisitante() {
        return nombreEquipoVisitante;
    }
    public void setNombreEquipoVisitante(String nombreEquipoVisitante) {
        this.nombreEquipoVisitante = nombreEquipoVisitante;
    }
}
