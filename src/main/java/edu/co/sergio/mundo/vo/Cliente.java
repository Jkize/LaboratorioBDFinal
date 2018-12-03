package edu.co.sergio.mundo.vo;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author Jkize .
 */
public class Cliente extends Persona implements Serializable {

    private String direccion;

    public Cliente(long idPersona) {
        super.idPersona = idPersona;
    }

    public Cliente() {
    }

    /**
     * getDireccion.
     *
     * @return direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * setDireccion.
     *
     * @param direccion .
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
