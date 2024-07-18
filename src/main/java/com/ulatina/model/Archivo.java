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
public class Archivo implements Serializable{
    
    private int id;
    private String url;
    private Publicacion publicacion;
    private String tipo;

    public Archivo() {
    }

    public Archivo(int id, String url, Publicacion publicacion, String tipo) {
        this.id = id;
        this.url = url;
        this.publicacion = publicacion;
        this.tipo = tipo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
}
