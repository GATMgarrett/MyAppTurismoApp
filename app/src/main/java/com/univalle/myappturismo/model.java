package com.univalle.myappturismo;

public class model {

    String Nombre, Descripcion;
    Double Latitud, Longitud;

    public model(){

    }

    public model(String nombre, String descripcion){
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }
    public model(Double latitud, Double longitud){
        this.Latitud = latitud;
        this.Longitud = longitud;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public Double getLatitud() { return Latitud; }

    public void setLatitud(Double latitud) {
        this.Latitud = latitud;
    }

    public Double getLongitud() { return Longitud; }

    public void setLongitud(Double longitud) {
        this.Longitud = longitud;
    }
}
