
package com.ulatina.controller;

import com.ulatina.model.Archivo;
import com.ulatina.model.Documento;
import com.ulatina.model.Imagen;
import com.ulatina.model.Publicacion;
import com.ulatina.model.UsuarioTO;
import com.ulatina.model.Categoria;
import com.ulatina.service.ServicioPublicacion;
import com.ulatina.service.ServicioCategoria;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.ResponsiveOption;
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
    private ServicioCategoria servCategoria;
    private String categoria;
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
    private List<Categoria> categorias;
    private String categoriaSeleccionada;

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    public PublicacionController() {
        servPublicacion = new ServicioPublicacion();
        servCategoria = new ServicioCategoria();
        publicaciones = new ArrayList<>();
        responsiveOptions1 = new ArrayList<>();
        responsiveOptions1.add(new ResponsiveOption("1024px", 5));
        responsiveOptions1.add(new ResponsiveOption("768px", 3));
        responsiveOptions1.add(new ResponsiveOption("560px", 1));
        photo = "No se cargo la imagen";
        cargarPublicaciones(0);
        cargarCategorias();
    }
    @PostConstruct
    public void init(){
        user = loginController.getUsuarioTO();
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
        categoriaSeleccionada = null;
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
            publicacion.setCategoria(categoriaSeleccionada);

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

    public void favoritos(Publicacion publicacion) {
        publicacion.setNumero_favoritos(publicacion.getNumero_favoritos() + 1);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You favorited a post."));
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public String getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(String categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    private void cargarCategorias() {
        try {
            categorias = servCategoria.obtenerTodasCategorias(); // Carga todas las categorías
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}