package com.example.gymcross;

import java.io.Serializable;
import java.util.Comparator;

public class Gimnasio implements Serializable {
    private String id;
    private String nombre;
    private String provincia;
    private String municipio;
    private String cp;
    private String correo;
    private String direccion;
    private String comunidad_autonoma;
    private String latitud;
    private String longitud;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComunidad_autonoma() {
        return comunidad_autonoma;
    }

    public void setComunidad_autonoma(String comunidad_autonoma) {
        this.comunidad_autonoma = comunidad_autonoma;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Gimnasio(String id, String nombre, String provincia, String municipio, String cp, String correo, String direccion, String comunidad_autonoma, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.municipio = municipio;
        this.cp = cp;
        this.correo = correo;
        this.direccion = direccion;
        this.comunidad_autonoma = comunidad_autonoma;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Gimnasio(){}

    public static Comparator<Gimnasio> comparadorNombreDescendente = new Comparator<Gimnasio>() {
        public int compare(Gimnasio c1, Gimnasio c2) {
            return c2.getNombre().compareTo(c1.getNombre());
        }
    };

    public static Comparator<Gimnasio> comparadorNombreAscendente = new Comparator<Gimnasio>() {
        public int compare(Gimnasio c1, Gimnasio c2) {
            return c1.getNombre().compareTo(c2.getNombre());
        }
    };
}
