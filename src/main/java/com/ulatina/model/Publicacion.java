/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.model;

import java.io.Serializable;

/**
 *
 * @author josem
 */
public class Publicacion implements Serializable{
    
    private int id;
    private String descripcion;
    private int idUsuario;
    private String imagen;
    private String documento;
    private int favoritos;
    
    public Publicacion(){
        
    }

    public Publicacion(String descripcion, int idUsuario, String imagen, String documento, int favoritos) {
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.imagen = imagen;
        this.documento = documento;
        this.favoritos = favoritos;
    }

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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(int favoritos) {
        this.favoritos = favoritos;
    }
    
    
}
