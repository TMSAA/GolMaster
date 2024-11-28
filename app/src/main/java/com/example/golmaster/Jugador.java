package com.example.golmaster;

public class Jugador {

    private int Id;
    private int Id_Equipo;
    private String Nombre;
    private String posicion;
    private String Nacionalidad;
    private int edad;
    private int dorsal;
    private String valor;
    private boolean lesion;

    public Jugador(){}

    public Jugador(int Id, int Id_Equipo, String Nombre, String posicion, String Nacionalidad, int edad, int dorsal, String valor, boolean lesion) {
        this.Id = Id;
        this.Id_Equipo = Id_Equipo;
        this.Nombre = Nombre;
        this.posicion = posicion;
        this.Nacionalidad = Nacionalidad;
        this.edad = edad;
        this.dorsal = dorsal;
        this.valor = valor;
        this.lesion = lesion;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public int getId_Equipo() { return Id_Equipo; }
    public void setId_Equipo(int id_Equipo) { Id_Equipo = id_Equipo; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public String getNacionalidad() { return Nacionalidad; }
    public void setNacionalidad(String nacionalidad) { Nacionalidad = nacionalidad; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getDorsal() { return dorsal; }
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public boolean isLesion() { return lesion; }
    public void setLesion(boolean lesion) { this.lesion = lesion; }
}
