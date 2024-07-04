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
    private int usuario_id; // 
    private Timestamp fecha_publicacion; 
    private Timestamp fecha_actualizacion; 
    private String imagen_url; 
    private String documento_url; 
    private int numero_favoritos; 
    

    // Constructor vacío
    public Publicacion() {
    }

    // Constructor con parámetros
    public Publicacion(String Descripcion, int usuarioId, Timestamp fechaPublicacion, Timestamp fechaActualizacion, String imagenUrl, String documentoUrl, int numeroFavoritos) {
        this.descripcion = Descripcion;
        this.usuario_id = usuarioId;
        this.fecha_publicacion = fechaPublicacion;
        this.fecha_actualizacion = fechaActualizacion;
        this.imagen_url = imagenUrl;
        this.documento_url = documentoUrl;
        this.numero_favoritos = numeroFavoritos;
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

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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
