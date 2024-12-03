package com.example.golmaster;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

public class Partido implements Parcelable {

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
    private String rutaEscudoLocal;
    private String rutaEscudoVisitante;

    @PropertyName("GolesEquipoLocal")
    private int golesEquipoLocal;

    @PropertyName("GolesEquipoVisitante")
    private int golesEquipoVisitante;

    public Partido() {}

    protected Partido(Parcel in) {
        id = in.readInt();
        idJornada = in.readInt();
        equipoLocal = in.readInt();
        equipoVisitante = in.readInt();
        horario = in.readParcelable(Timestamp.class.getClassLoader());
        nombreEquipoLocal = in.readString();
        nombreEquipoVisitante = in.readString();
        rutaEscudoLocal = in.readString();
        rutaEscudoVisitante = in.readString();
        golesEquipoLocal = in.readInt();
        golesEquipoVisitante = in.readInt();
    }

    public static final Creator<Partido> CREATOR = new Creator<Partido>() {
        @Override
        public Partido createFromParcel(Parcel in) {
            return new Partido(in);
        }

        @Override
        public Partido[] newArray(int size) {
            return new Partido[size];
        }
    };

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

    public String getRutaEscudoLocal() {
        return rutaEscudoLocal;
    }

    public void setRutaEscudoLocal(String rutaEscudoLocal) {
        this.rutaEscudoLocal = rutaEscudoLocal;
    }

    public String getRutaEscudoVisitante() {
        return rutaEscudoVisitante;
    }

    public void setRutaEscudoVisitante(String rutaEscudoVisitante) {
        this.rutaEscudoVisitante = rutaEscudoVisitante;
    }

    @PropertyName("GolesEquipoLocal")
    public int getGolesEquipoLocal() {
        return golesEquipoLocal;
    }

    @PropertyName("GolesEquipoLocal")
    public void setGolesEquipoLocal(int golesEquipoLocal) {
        this.golesEquipoLocal = golesEquipoLocal;
    }

    @PropertyName("GolesEquipoVisitante")
    public int getGolesEquipoVisitante() {
        return golesEquipoVisitante;
    }

    @PropertyName("GolesEquipoVisitante")
    public void setGolesEquipoVisitante(int golesEquipoVisitante) {
        this.golesEquipoVisitante = golesEquipoVisitante;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idJornada);
        dest.writeInt(equipoLocal);
        dest.writeInt(equipoVisitante);
        dest.writeParcelable(horario, flags);
        dest.writeString(nombreEquipoLocal);
        dest.writeString(nombreEquipoVisitante);
        dest.writeString(rutaEscudoLocal);
        dest.writeString(rutaEscudoVisitante);
        dest.writeInt(golesEquipoLocal);
        dest.writeInt(golesEquipoVisitante);
    }
}
