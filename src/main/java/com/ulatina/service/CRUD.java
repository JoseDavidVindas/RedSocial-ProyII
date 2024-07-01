/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ulatina.service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author josem
 */
public interface CRUD<T> {
    
    public Boolean insertar(T t);
    public Boolean modificar(T t);
    public Boolean eliminar(T t);
    public List<T> findAll();
    public Optional<T> findPK();
    
    
}
