/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import com.ulatina.model.Publicacion;
import com.ulatina.model.UsuarioTO;
import com.ulatina.service.ServicioPublicacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    private List<Publicacion> publicaciones;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 10;
    private UsuarioTO usuario;
    
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    public PublicacionController() {
        servPublicacion = new ServicioPublicacion();
        publicaciones = new ArrayList<>();
        cargarPublicaciones();
    }

    public void crearPublicacion() {
        try {
            publicacion = new Publicacion();
            publicacion.setDescripcion(descripcion);
            publicacion.setUsuario_id(loginController.getUsuarioTO().getId());

            if (!servPublicacion.insertar(publicacion)) {
                descripcion = "";
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la publicacion"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicacion creada satisfactoriamente"));
               // cargarPublicaciones(); // Actualiza la lista de publicaciones
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void cargarPublicaciones() {
        try {
            publicaciones = servPublicacion.findAll(currentPage, PAGE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMore() {
        currentPage++;
        cargarPublicaciones();
    }

    public void verDocumento(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo abrir el documento."));
            e.printStackTrace();
        }
    }

    public void descargarDocumento(String url) {
        // Implementación del método para descargar documento
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    
    
}
