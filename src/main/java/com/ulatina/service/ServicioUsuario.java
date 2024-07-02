/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.service;

import com.ulatina.model.UsuarioTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Usuario
 */
public class ServicioUsuario extends Servicio implements CRUD<UsuarioTO>{
    
   public UsuarioTO validarUsuario(String correo, String contrasena) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioTO usuarioTORetorno = null;
        
        try {
            
            Conectar();
            
            String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("correo");
                String nom = rs.getString("nombre");
                int rol = rs.getInt("rol_id");
                String pass = rs.getString("contrasena");
               
                usuarioTORetorno = new UsuarioTO(id, mail, pass, nom, rol);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Paso 5
            CerrarResultSet(rs);
            CerrarStatement(stmt);
            Desconectar();
        }
        return usuarioTORetorno;
    }

    @Override
    public Boolean insertar(UsuarioTO t) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Conectar();

            String sql = "INSERT INTO usuario (nombre,correo,contrasena,rol_id) VALUES (?,?,?,?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, t.getNombre());
            stmt.setString(2, t.getCorreo());
            stmt.setString(3, t.getContrasena());
            stmt.setInt(4, t.getRol());
         

            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
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
    public Boolean modificar(UsuarioTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminar(UsuarioTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UsuarioTO> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<UsuarioTO> findPK() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 
    
}
