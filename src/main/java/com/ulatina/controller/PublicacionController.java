/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import com.ulatina.model.Publicacion;
import com.ulatina.service.ServicioPublicacion;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author josem
 */
@ManagedBean(name="publicacionController")
@SessionScoped
public class PublicacionController {
    
    private Publicacion publicacion;
    private String descripcion;
    private ServicioPublicacion servPublicacion;

    public PublicacionController() {
    }
    
    public void crearPublicacion(){
        try{
        servPublicacion = new ServicioPublicacion();
                
        publicacion = new Publicacion();
        publicacion.setDescripcion(descripcion);
        publicacion.setIdUsuario(1);
        
        if(!servPublicacion.insertar(publicacion)){
            
            descripcion = "";
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la publicacion"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicacion creada satisfactoriamente"));
        }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
