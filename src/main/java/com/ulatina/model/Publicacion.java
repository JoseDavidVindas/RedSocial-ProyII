/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author josem
 */
public class Publicacion implements Serializable {

    private int id; 
    private String descripcion; 
    private UsuarioTO usuario; // 
    private Timestamp fecha_publicacion; 
    private Timestamp fecha_actualizacion; 
    private int numero_favoritos; 
    private List<Archivo> archivo;
    

    // Constructor vacío
    public Publicacion() {
    }

    // Constructor con parámetros

    public Publicacion(int id, String descripcion, UsuarioTO usuario, Timestamp fecha_publicacion, Timestamp fecha_actualizacion, int numero_favoritos, List<Archivo> archivo) {
        this.id = id;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.fecha_publicacion = fecha_publicacion;
        this.fecha_actualizacion = fecha_actualizacion;
        this.numero_favoritos = numero_favoritos;
        this.archivo = archivo;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioTO usuario) {
        this.usuario = usuario;
    }

    public Timestamp getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Timestamp fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public Timestamp getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Timestamp fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
    public int getNumero_favoritos() {
        return numero_favoritos;
    }

    public void setNumero_favoritos(int numero_favoritos) {
        this.numero_favoritos = numero_favoritos;
    }

    public List<Archivo> getArchivo() {
        return archivo;
    }

    public void setArchivo(List<Archivo> archivo) {
        this.archivo = archivo;
    }
   
    

}
