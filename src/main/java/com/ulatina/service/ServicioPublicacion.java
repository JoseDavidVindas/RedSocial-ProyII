
package com.ulatina.service;

import com.ulatina.model.Archivo;
import com.ulatina.model.Documento;
import com.ulatina.model.Imagen;
import com.ulatina.model.Publicacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author josem
 */
public class ServicioPublicacion extends Servicio implements CRUD<Publicacion> {

    @Override
    public Boolean insertar(Publicacion t) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ServicioArchivo servA = new ServicioArchivo();

        try {
            Conectar();

            String sql = "INSERT INTO publicacion (descripcion,usuario_id,numero_favoritos, categoria) VALUES (?,?,?,?)";
            stmt = getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, t.getDescripcion());
            stmt.setInt(2, t.getUsuario().getId());
            stmt.setInt(3, t.getNumero_favoritos());
            stmt.setString (4, t.getCategoria());

            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int usuarioId = rs.getInt(1);
                    t.setId(usuarioId);
                    if (t.getDocumentos() != null) {
                        for (Documento documento : t.getDocumentos()) {
                            documento.setPublicacion(t);
                            servA.insertarDocumento(documento);
                        }
                    }
                    if (t.getImagenes() != null) {
                        for (Imagen imagen : t.getImagenes()) {
                            imagen.setPublicacion(t);
                            servA.insertarImagen(imagen);
                        }
                    }
                }
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            CerrarResultSet(rs);
            CerrarStatement(stmt);
            Desconectar();
        }
    }

    @Override
    public Boolean modificar(Publicacion t) {
        return null;
    }

    @Override
    public Boolean eliminar(Publicacion t) {
        return null;
    }

    @Override
    public List<Publicacion> findAll() {
        return null;
    }

    @Override
    public Optional<Publicacion> findPK() {
        return null;
    }

    public List<Publicacion> findAll(int cantidadPublicaciones) throws ClassNotFoundException {
        List<Publicacion> publicaciones = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ServicioUsuario servUsuario = new ServicioUsuario();
        ServicioArchivo servA = new ServicioArchivo();

        try {
            Conectar();
            
            String query = "SELECT id, descripcion, usuario_id, fecha_publicacion, fecha_actualizacion, numero_favoritos, categoria "
                    + "FROM publicacion "
                    + "ORDER BY fecha_publicacion DESC "
                    + "LIMIT ?";

            stmt = getConexion().prepareStatement(query);
            stmt.setInt(1, cantidadPublicaciones);  // Límite
            rs = stmt.executeQuery();

            while (rs.next()) {
                Publicacion publicacion = new Publicacion();
                publicacion.setId(rs.getInt("id"));
                publicacion.setDescripcion(rs.getString("descripcion"));
                publicacion.setUsuario(servUsuario.usuarioPK(rs.getInt("usuario_id")));
                publicacion.setFecha_publicacion(rs.getTimestamp("fecha_publicacion"));
                publicacion.setFecha_actualizacion(rs.getTimestamp("fecha_actualizacion"));
                publicacion.setNumero_favoritos(rs.getInt("numero_favoritos"));
                publicacion.setCategoria(rs.getString("categoria"));
                publicacion.setDocumentos(servA.buscarDocumento(publicacion));
                publicacion.setImagenes(servA.buscarImagen(publicacion));
                publicaciones.add(publicacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CerrarResultSet(rs);
            CerrarStatement(stmt);
            Desconectar();
        }
        return publicaciones;
    }

}
