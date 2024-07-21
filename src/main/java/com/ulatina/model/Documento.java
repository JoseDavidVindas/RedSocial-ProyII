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
public class Documento extends Archivo implements Serializable{

    public Documento() {
    }

    public Documento(int id, String url, Publicacion publicacion) {
        super(id, url, publicacion);
    }
    
    
    
}
