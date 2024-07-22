/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import com.ulatina.model.UsuarioTO;
import com.ulatina.service.ServicioUsuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Jose
 */
@ManagedBean(name="editarPerfilController")
@SessionScoped
public class EditarPerfilController implements Serializable {

    private UsuarioTO usuario;
    private ServicioUsuario servUsuario;
    private String biografia;
    private String fotoPerfilUrl;
    private UploadedFile nuevoCV;

    @PostConstruct
    public void init() {
        servUsuario = new ServicioUsuario();
        usuario = servUsuario.usuarioPK(getUsuarioId()); // Método para obtener el id del usuario actual
        biografia = usuario.getBiografia();
        fotoPerfilUrl = usuario.getFotoPerfil();
    }

    public void guardarCambios() {
        try {
            if (servUsuario.actualizarBiografia(usuario.getId(), biografia) &&
                servUsuario.actualizarFotoPerfil(usuario.getId(), fotoPerfilUrl)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil actualizado satisfactoriamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron guardar los cambios en el perfil."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarCV() {
        try {
            if (nuevoCV != null) {
                String url = saveFile(nuevoCV); // Método para guardar el archivo y obtener la URL
                if (servUsuario.actualizarCV(usuario.getId(), url)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("CV actualizado satisfactoriamente"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el CV."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void atras(){
        this.redireccionar("/verperfil.xhtml");
    }
    
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    // Getters y setters para los atributos

    private int getUsuarioId() {
        // Implementa la lógica para obtener el id del usuario actual
        return 1; // Ejemplo de retorno; reemplaza con la lógica real
    }

    private String saveFile(UploadedFile archivo) {
        // Implementa la lógica para guardar el archivo y devolver la URL
        return ""; // Retorna la URL del archivo guardado
    }
}

