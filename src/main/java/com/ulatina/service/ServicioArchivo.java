/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.service;

import com.ulatina.model.Archivo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author josem
 */
public class ServicioArchivo extends Servicio{
    
    public Boolean insertar(Archivo t) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            Conectar();
            
            String sql = "INSERT INTO archivo (Url,IdPublicacion,Tipo) VALUES (?,?,?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, t.getUrl());
            stmt.setInt(2, t.getPublicacion().getId());
            stmt.setString(3, t.getTipo());
            
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
    
}
