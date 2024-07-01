/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.service;

import com.ulatina.model.Publicacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author josem
 */
public class ServicioPublicacion extends Servicio implements CRUD<Publicacion>{

    @Override
    public Boolean insertar(Publicacion t) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            Conectar();
            
            String sql = "INSERT INTO publicacion (descripcion,usuario_id,imagen_url,documento_url,numero_favoritos) VALUES (?,?,?,?,?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, t.getDescripcion());
            stmt.setInt(2, t.getIdUsuario());
            stmt.setString(3, t.getImagen());
            stmt.setString(4, t.getDocumento());
            stmt.setInt(5, t.getFavoritos());
            
            int filasInsertadas = stmt.executeUpdate();
            
            if(filasInsertadas >0){
                return true;
            }else{
                return false;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
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
    
}
