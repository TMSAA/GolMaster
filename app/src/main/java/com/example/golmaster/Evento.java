package com.example.golmaster;

import com.google.firebase.firestore.PropertyName;

public class Evento {

    @PropertyName("tipo")
    private String tipo;
    @PropertyName("minuto")
    private int minuto;
    @PropertyName("jugador")
    private String jugador;
    @PropertyName("equipo")
    private String equipo;
    @PropertyName("descripcion")
    private String descripcion;

    public Evento() {}

    @PropertyName("tipo")
    public String getTipo() {
        return tipo;
    }
    @PropertyName("tipo")
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    @PropertyName("minuto")
    public int getMinuto() {
        return minuto;
    }
    @PropertyName("minuto")
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    @PropertyName("jugador")
    public String getJugador() {
        return jugador;
    }
    @PropertyName("jugador")
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    @PropertyName("equipo")
    public String getEquipo() {
        return equipo;
    }
    @PropertyName("equipo")
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    @PropertyName("descripcion")
    public String getDescripcion() {
        return descripcion;
    }
    @PropertyName("descripcion")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}