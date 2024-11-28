package com.example.golmaster;

public class Jornada {

    private int Id;
    private String[] partidos;

    public Jornada(){}

    public Jornada(int Id, String[] partidos)
    {
        this.Id = Id;
        this.partidos = partidos;
    }

    public int getId(){return Id;}
    public void setId(int Id){this.Id = Id;}
    public String[] getPartidos() {return partidos;}
    public void setPartidos(String[] partidos) {this.partidos = partidos;}
}
