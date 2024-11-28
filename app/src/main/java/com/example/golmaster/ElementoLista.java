package com.example.golmaster;
public class ElementoLista {
    public static final int TIPO_FECHA = 0;
    public static final int TIPO_PARTIDO = 1;

    private int tipo;
    private String fecha;
    private Partido partido;

    public ElementoLista(int tipo, String fecha, Partido partido) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.partido = partido;
    }

    public int getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public Partido getPartido() {
        return partido;
    }
}

