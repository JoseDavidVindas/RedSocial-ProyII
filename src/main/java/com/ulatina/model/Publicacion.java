/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
    private String imagen_url; 
    private String documento_url; 
    private int numero_favoritos; 
    

    // Constructor vacío
    public Publicacion() {
    }

    // Constructor con parámetros

    public Publicacion(int id, String descripcion, UsuarioTO usuario, Timestamp fecha_publicacion, Timestamp fecha_actualizacion, String imagen_url, String documento_url, int numero_favoritos) {
        this.id = id;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.fecha_publicacion = fecha_publicacion;
        this.fecha_actualizacion = fecha_actualizacion;
        this.imagen_url = imagen_url;
        this.documento_url = documento_url;
        this.numero_favoritos = numero_favoritos;
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

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public String getDocumento_url() {
        return documento_url;
    }

    public void setDocumento_url(String documento_url) {
        this.documento_url = documento_url;
    }

    public int getNumero_favoritos() {
        return numero_favoritos;
    }

    public void setNumero_favoritos(int numero_favoritos) {
        this.numero_favoritos = numero_favoritos;
    }
   
    

}
