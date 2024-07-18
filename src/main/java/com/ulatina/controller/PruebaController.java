/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author josem
 */
@ManagedBean(name = "pruebaController")
@SessionScoped
public class PruebaController implements Serializable {

    FileUploadEvent event;
    List<UploadedFile> files;

    public PruebaController() {
        files = new ArrayList<UploadedFile>();
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded."));
        files.add(event.getFile());
    }

    public void crearPublicacion() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded."));
    }

    public FileUploadEvent getEvent() {
        return event;
    }

    public void setEvent(FileUploadEvent event) {
        this.event = event;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    
    
    
}
