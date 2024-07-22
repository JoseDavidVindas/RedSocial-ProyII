/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ulatina.controller;

import com.ulatina.model.UsuarioTO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author josem
 */
@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        // Obtén el objeto UsuarioTO a partir del valor del id
        BusquedaController controller = context.getApplication().evaluateExpressionGet(context, "#{busquedaController}", BusquedaController.class);
        return controller.obtenerUsuarioPorId(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof UsuarioTO) {
            return String.valueOf(((UsuarioTO) value).getId());
        } else {
            throw new IllegalArgumentException("El valor no es un UsuarioTO válido: " + value);
        }
    }
}
