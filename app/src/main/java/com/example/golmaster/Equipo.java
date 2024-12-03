package com.example.golmaster;

public class Equipo {

    private int Id;
    private String Nombre;
    private String ciudad;
    private String entrenador;
    private Jugador[] Jugadores;
    private String RutaEscudo;

    public Equipo(){}

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { this.Nombre = nombre; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getEntrenador() { return entrenador; }
    public void setEntrenador(String entrenador) { this.entrenador = entrenador; }

    public Jugador[] getJugadores() { return Jugadores; }
    public void setJugadores(Jugador[] jugadores) { Jugadores = jugadores; }
    public String getRutaEscudo() { return RutaEscudo; }
    public void setRutaEscudo(String RutaEscudo) { this.RutaEscudo = RutaEscudo; }

}
