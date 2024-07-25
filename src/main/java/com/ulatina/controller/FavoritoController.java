package com.ulatina.controller;

import com.ulatina.model.Publicacion;
import com.ulatina.model.UsuarioTO;
import com.ulatina.service.ServicioFavorito;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "favoritoController")
@SessionScoped

public class FavoritoController implements Serializable{

    private ServicioFavorito servicioFavorito = new ServicioFavorito(); 
    private List<Publicacion> publicacionesFavoritas;
    private UsuarioTO usuario;
    
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;
    
     @PostConstruct
    public void init() {
        usuario = loginController.getUsuarioTO();
        this.servicioFavorito = new ServicioFavorito();
        if (usuario != null) {
            try {
                publicacionesFavoritas = servicioFavorito.obtenerFavoritosPorUsuario(usuario.getId());
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los favoritos"));
                e.printStackTrace();
            }
        }
    }

public void agregarFavorito(int idPublicacion) {
    int idUsuario = obtenerIdUsuarioActual();
    if (idUsuario != -1) {
        servicioFavorito.agregarFavorito(idUsuario, idPublicacion); // Usa agregarFavorito en lugar de agregarArchivoFavorito
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Publicación añadida a favoritos");
        facesContext.addMessage(null, message);
        // Redirigir a favorito.xhtml
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Usuario no autenticado"));
    }
}

    private int obtenerIdUsuarioActual() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");
        return (idUsuario != null) ? idUsuario : -1; // Retorna -1 si no se encuentra el usuario
    }
    
    

    public List<Publicacion> getPublicacionesFavoritas() {
        return publicacionesFavoritas;
    }

    public ServicioFavorito getServicioFavorito() {
        return servicioFavorito;
    }

    public void setServicioFavorito(ServicioFavorito servicioFavorito) {
        this.servicioFavorito = servicioFavorito;
    }
    
    public void setPublicacionesFavoritas(List<Publicacion> publicacionesFavoritas) {
        this.publicacionesFavoritas = publicacionesFavoritas;
    }
    
    public UsuarioTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioTO usuario) {
        this.usuario = usuario;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    
}
