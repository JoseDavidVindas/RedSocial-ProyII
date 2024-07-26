/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import com.ulatina.model.Rol;
import com.ulatina.model.UsuarioTO;
import com.ulatina.service.ServicioUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author josem
 */
@ManagedBean(name = "busquedaController")
@SessionScoped
public class BusquedaController implements Serializable {

    private String query;
    private ServicioUsuario servU;
    private UsuarioTO usuarioSeleccionado;
    private List<UsuarioTO> resultados;
    private Rol rol;

    public BusquedaController() {
        servU = new ServicioUsuario();
        usuarioSeleccionado = new UsuarioTO();
        resultados = new ArrayList<>();
        rol = new Rol();

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<UsuarioTO> obtenerResultados(String query) {
        resultados = servU.buscarUsuarios(query);
        
        return resultados;
    }
    
    public void redirigirUsuario() {
        rol = servU.rolPK(usuarioSeleccionado.getRol());
        this.redireccionar("/VerUsuario.xhtml");
       
    }

    public UsuarioTO obtenerUsuarioPorId(int id) {
        for (UsuarioTO usuario : resultados) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }
    
    public UsuarioTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public List<UsuarioTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<UsuarioTO> resultados) {
        this.resultados = resultados;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
}
