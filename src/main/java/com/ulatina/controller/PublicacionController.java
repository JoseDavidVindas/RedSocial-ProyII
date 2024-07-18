/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import com.ulatina.model.Archivo;
import com.ulatina.model.Publicacion;
import com.ulatina.model.UsuarioTO;
import com.ulatina.service.ServicioPublicacion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

/**
 *
 * @author josem
 */
@ManagedBean(name="publicacionController")
@SessionScoped
public class PublicacionController implements Serializable{
    
    private Publicacion publicacion;
    private String descripcion;
    private ServicioPublicacion servPublicacion;
    private List<Publicacion> publicaciones;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 10;
    private UsuarioTO usuario;
    //private UploadedFiles files;
    private List<Archivo> archivos;
    private Archivo archivo;
    List<UploadedFile> files;
    
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    public PublicacionController() {
        servPublicacion = new ServicioPublicacion();
        publicaciones = new ArrayList<>();
        cargarPublicaciones();
    }
    
    public void handleFileUploadEvent(FileUploadEvent event) throws IOException {
        System.out.println("===>>> " + event.getFile().getFileName() + " size: " + event.getFile().getSize());
        archivo = new Archivo();
        this.copyFile(event.getFile().getFileName(), event.getFile().getInputStream(), false);
        archivos.add(archivo);
        
//files.add(event.getFile());
        /*for (UploadedFile file : files) {
            System.out.println("===>>> " + file.getFileName() + " size: " + file.getSize());
        }*/
    }
    
    public void nuevaPublicacion(){
        descripcion = "";
        files = new ArrayList<>();
        archivos = new ArrayList<Archivo>();
    }
    
    public void handleFileUpload() {
        try {
            if (files != null) {
                for (UploadedFile file : files) {
                    archivo = new Archivo();
                    System.out.println("===>>> " + file);
                    System.out.println("===>>> " + file.getFileName() + " size: " + file.getSize());
                    this.copyFile(file.getFileName(), file.getInputStream(), false);
                    //Guardar en la base de datos los datos relacionados al archivo (no el archivo)
                    archivos.add(archivo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void copyFile(String fileName, InputStream in, boolean esTemporal) {
        try {
            if (fileName != null) {
                

                String[] partesArchivo = fileName.split(Pattern.quote("."));
                String nombreArchivo = partesArchivo[0];
                String extensionArchivo = partesArchivo[1];
                if (esTemporal) {
                    nombreArchivo += "_TMP";
                }
                String url;
                // Determine the destination path based on the file extension
                String destinationFile;
                if (extensionArchivo.equalsIgnoreCase("jpg") || extensionArchivo.equalsIgnoreCase("jpeg") ||
                    extensionArchivo.equalsIgnoreCase("png") || extensionArchivo.equalsIgnoreCase("gif")) {
                    destinationFile = "C:/Users/josem/OneDrive/Documentos/Proyecto Ingenieria Software 2/Proyecto Pagina/Red_Social_Academica/archivos/imagenes/";
                    url = ""+destinationFile + nombreArchivo + "." + extensionArchivo;
                    archivo.setUrl(url);
                    archivo.setTipo("Imagen");
                } else if (extensionArchivo.equalsIgnoreCase("pdf") || extensionArchivo.equalsIgnoreCase("docx")) {
                    destinationFile = "C:/Users/josem/OneDrive/Documentos/Proyecto Ingenieria Software 2/Proyecto Pagina/Red_Social_Academica/archivos/documentos/";
                    url = ""+destinationFile + nombreArchivo + "." + extensionArchivo;
                    archivo.setUrl(url);
                    archivo.setTipo("Documento");
                } else {
                    throw new IOException("Unsupported file type: " + extensionArchivo);
                }
            
                
                

                //File tmp = new File(destinationFile + fileName);
                File tmp = new File(destinationFile + nombreArchivo + "." + extensionArchivo);
                tmp.getParentFile().mkdirs();
                OutputStream out = new FileOutputStream(tmp);
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void crearPublicacion() {
        try {
            
            //handleFileUpload();
            publicacion = new Publicacion();
            publicacion.setDescripcion(descripcion);
            publicacion.setUsuario(loginController.getUsuarioTO());
            publicacion.setArchivo(archivos);
            
            if (!servPublicacion.insertar(publicacion)) {
                descripcion = "";
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la publicacion"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicacion creada satisfactoriamente"));
               cargarPublicaciones(); // Actualiza la lista de publicaciones
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

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioTO usuario) {
        this.usuario = usuario;
    }
/*
    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }
*/
    public List<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivo> archivos) {
        this.archivos = archivos;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }
    
    
}
