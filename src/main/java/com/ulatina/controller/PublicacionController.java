/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import java.util.zip.*;
import com.ulatina.model.Archivo;
import com.ulatina.model.Documento;
import com.ulatina.model.Imagen;
import com.ulatina.model.Publicacion;
import com.ulatina.model.UsuarioTO;
import com.ulatina.service.ServicioArchivo;
import com.ulatina.service.ServicioFavorito;
import com.ulatina.service.ServicioPublicacion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.ResponsiveOption;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.DefaultStreamedContent;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

/**
 *
 * @author josem
 */
@ManagedBean(name = "publicacionController")
@SessionScoped
@ViewScoped
public class PublicacionController implements Serializable {
    
    private ServicioFavorito servicioFavorito;
    private ServicioArchivo servicioArchivo;
    private StreamedContent file;
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
    private Documento documento;
    private Imagen imagen;
    private List<Documento> documentos;
    private List<Imagen> imagenes;
    private List<ResponsiveOption> responsiveOptions1;
    private String photo;
    private UsuarioTO user;

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    public PublicacionController() {
        
        
        servicioArchivo = new ServicioArchivo();
        servPublicacion = new ServicioPublicacion();
        publicaciones = new ArrayList<>();
        responsiveOptions1 = new ArrayList<>();
        responsiveOptions1.add(new ResponsiveOption("1024px", 5));
        responsiveOptions1.add(new ResponsiveOption("768px", 3));
        responsiveOptions1.add(new ResponsiveOption("560px", 1));
        photo = "No se cargo la imagen";
        cargarPublicaciones(0);
    }

    @PostConstruct
    public void init() {
        user = loginController.getUsuarioTO();
        this.servicioFavorito = new ServicioFavorito();
    }

    public void handleFileUploadEvent(FileUploadEvent event) throws IOException {
        System.out.println("===>>> " + event.getFile().getFileName() + " size: " + event.getFile().getSize());
        documento = new Documento();
        imagen = new Imagen();
        this.copyFile(event.getFile().getFileName(), event.getFile().getInputStream(), false);

    }

    public void nuevaPublicacion() {
        descripcion = "";
        files = new ArrayList<>();
        imagenes = new ArrayList<Imagen>();
        documentos = new ArrayList<Documento>();
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
                if (extensionArchivo.equalsIgnoreCase("jpg") || extensionArchivo.equalsIgnoreCase("jpeg")
                        || extensionArchivo.equalsIgnoreCase("png") || extensionArchivo.equalsIgnoreCase("gif")) {
                    destinationFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/archivos/imagenes/");
                    url = "" + "http://localhost:8080/Red_Social_Academica/archivos/imagenes/" + nombreArchivo + "." + extensionArchivo;
                    imagen.setUrl(url);
                    imagenes.add(imagen);
                } else if (extensionArchivo.equalsIgnoreCase("pdf") || extensionArchivo.equalsIgnoreCase("docx")) {
                    destinationFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/archivos/documentos/");
                    url = "" + "http://localhost:8080/Red_Social_Academica/archivos/documentos/" + nombreArchivo + "." + extensionArchivo;
                    documento.setUrl(url);
                    documentos.add(documento);
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
            publicacion.setDocumentos(documentos);
            publicacion.setImagenes(imagenes);

            if (!servPublicacion.insertar(publicacion)) {
                descripcion = "";
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la publicacion"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicacion creada satisfactoriamente"));
                cargarPublicaciones(0); // Actualiza la lista de publicaciones
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void cargarPublicaciones(int size) {
        try {
            size = size + 10;
            publicaciones = servPublicacion.findAll(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarMasPublicaciones() {
        try {
            int size = publicaciones.size() + 10;
            publicaciones = servPublicacion.findAll(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMore() {
        currentPage++;
        cargarPublicaciones(0);
    }

    public String obtenerIconoDocumento(String url) {
        if (url != null && !url.isEmpty()) {
            String extension = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
            switch (extension) {
                case "pdf":
                    return "pi pi-file-pdf";
                case "doc":
                case "docx":
                    return "pi pi-file-word";
                case "xls":
                case "xlsx":
                    return "pi pi-file-excel";
                // Agrega más casos según sea necesario
                default:
                    return "pi pi-file";
            }
        }
        return "pi pi-file";
    }

    public String obtenerNombreDocumento(String url) {
        if (url != null && !url.isEmpty()) {
            // Extraer el nombre del archivo desde la URL
            return url.substring(url.lastIndexOf('/') + 1);
        }
        return "Documento";
    }

    public void verDocumento(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo abrir el documento."));
            e.printStackTrace();
        }
    }
 
    public void agregarFavoritos(Publicacion publicacion) {
    try {
        int idUsuario = obtenerIdUsuarioActual();
        servicioFavorito.agregarArchivoFavorito(idUsuario, publicacion.getId());
        for (Documento doc : publicacion.getDocumentos()) {
            servicioFavorito.agregarArchivoFavorito(idUsuario, doc.getId());
        }
        for (Imagen img : publicacion.getImagenes()) {
            servicioFavorito.agregarArchivoFavorito(idUsuario, img.getId());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicación y archivos agregados a favoritos"));
        this.redireccionar("/Favorito.xhtml");
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar la publicación a favoritos"));
        e.printStackTrace();
    }
}


    private int obtenerIdUsuarioActual() {
        // Aquí debes obtener el ID del usuario actual, por ejemplo, desde la sesión
        UsuarioTO usuarioActual = loginController.getUsuarioTO();
        return usuarioActual != null ? usuarioActual.getId() : -1; // O manejar caso de usuario no logueado
    }
    
    public List<Publicacion> cargarFavoritos() {
    try {
        int idUsuario = obtenerIdUsuarioActual();
        return servicioFavorito.obtenerFavoritosPorUsuario(idUsuario);
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}

    
    // Inyección de dependencias

    /*public void setServicioArchivo(ServicioArchivo servicioArchivo) {
        this.servicioArchivo = servicioArchivo;
    }

    public StreamedContent prepararDescarga(int archivoId) {
        try {
            String urlArchivo = servicioArchivo.obtenerUrlArchivo(archivoId);
            InputStream stream = new FileInputStream(new File(urlArchivo));
            String extension = urlArchivo.substring(urlArchivo.lastIndexOf('.') + 1);
            return DefaultStreamedContent.builder()
                    .name("archivo." + extension)
                    .contentType(Files.probeContentType(Paths.get(urlArchivo)))
                    .stream(() -> stream)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void descargarArchivos(int idPublicacion) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        // Buscar la publicación en la lista
        Publicacion publicacionEncontrada = null;
        for (Publicacion p : publicaciones) {
            if (p.getId() == idPublicacion) {
                publicacionEncontrada = p;
                break;
            }
        }

        // Asegúrate de que publicacionEncontrada no es null antes de continuar
        if (publicacionEncontrada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Publicación no encontrada."));
            return;
        }

        // Establece el tipo de contenido y el nombre del archivo ZIP
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"publicacion_" + idPublicacion + ".zip\"");

        // Crear flujo de salida
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            // Agregar documentos al ZIP
            for (Documento doc : publicacionEncontrada.getDocumentos()) {
                File fileDownload = new File(doc.getUrl());
                if (fileDownload.exists()) {
                    try (InputStream docInputStream = new FileInputStream(fileDownload)) {
                        String id = "" + doc.getId();
                        ZipEntry zipEntry = new ZipEntry(id + getFileExtension(doc.getUrl()));
                        zipOut.putNextEntry(zipEntry);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = docInputStream.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, len);
                        }
                        zipOut.closeEntry();
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found: " + doc.getUrl());
                    } catch (IOException e) {
                        System.err.println("Error processing file: " + doc.getUrl());
                    }
                } else {
                    System.err.println("File does not exist: " + doc.getUrl());
                }
            }

            // Agregar imágenes al ZIP
            for (Imagen img : publicacionEncontrada.getImagenes()) {
                File fileDownload = new File(img.getUrl());
                if (fileDownload.exists()) {
                    try (InputStream imgInputStream = new FileInputStream(fileDownload)) {
                        String id = "" + img.getId();
                        ZipEntry zipEntry = new ZipEntry(id + getFileExtension(img.getUrl()));
                        zipOut.putNextEntry(zipEntry);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = imgInputStream.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, len);
                        }
                        zipOut.closeEntry();
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found: " + img.getUrl());
                    } catch (IOException e) {
                        System.err.println("Error processing file: " + img.getUrl());
                    }
                } else {
                    System.err.println("File does not exist: " + img.getUrl());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Completar el flujo de respuesta
            facesContext.responseComplete();
        }
    }

    private String getFileExtension(String filePath) {
        if (filePath == null) {
            return "";
        }
        int dotIndex = filePath.lastIndexOf('.');
        return (dotIndex > 0) ? filePath.substring(dotIndex) : "";
    }
*/
     public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
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

    public List<ResponsiveOption> getResponsiveOptions1() {
        return responsiveOptions1;
    }

    public void setResponsiveOptions1(List<ResponsiveOption> responsiveOptions1) {
        this.responsiveOptions1 = responsiveOptions1;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public UsuarioTO getUser() {
        return user;
    }

    public void setUser(UsuarioTO user) {
        this.user = user;
    }

    public ServicioFavorito getServicioFavorito() {
        return servicioFavorito;
    }

    public void setServicioFavorito(ServicioFavorito servicioFavorito) {
        this.servicioFavorito = servicioFavorito;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public ServicioPublicacion getServPublicacion() {
        return servPublicacion;
    }

    public void setServPublicacion(ServicioPublicacion servPublicacion) {
        this.servPublicacion = servPublicacion;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

}