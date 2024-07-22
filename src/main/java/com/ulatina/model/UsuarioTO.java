/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Usuario
 */
public class UsuarioTO implements Serializable{
    
    private int id;
    private String correo;
    private String contrasena;
    private String nombre;
    private int rol;
    private String cvUrl;       
    private String biografia;  
    private String fotoPerfil;

    public UsuarioTO(int id, String correo, String contrasena, String nombre, int rol) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.rol = rol;
    }

    public UsuarioTO(int id, String correo, String contrasena, String nombre, int rol, String biografia) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.rol = rol;
        this.biografia = biografia;
    }
    
    

    public UsuarioTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.correo);
        hash = 29 * hash + Objects.hashCode(this.contrasena);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + this.rol;
        hash = 29 * hash + Objects.hashCode(this.cvUrl);
        hash = 29 * hash + Objects.hashCode(this.biografia);
        hash = 29 * hash + Objects.hashCode(this.fotoPerfil);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioTO other = (UsuarioTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.rol != other.rol) {
            return false;
        }
        if (!Objects.equals(this.correo, other.correo)) {
            return false;
        }
        if (!Objects.equals(this.contrasena, other.contrasena)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.cvUrl, other.cvUrl)) {
            return false;
        }
        if (!Objects.equals(this.biografia, other.biografia)) {
            return false;
        }
        return Objects.equals(this.fotoPerfil, other.fotoPerfil);
    }

    @Override
    public String toString() {
        return "" + id;
    }

    

    
    
}