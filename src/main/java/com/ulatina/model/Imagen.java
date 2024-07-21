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
public class Imagen extends Archivo implements Serializable{

    public Imagen() {
    }

    public Imagen(int id, String url, Publicacion publicacion) {
        super(id, url, publicacion);
    }
    
}
