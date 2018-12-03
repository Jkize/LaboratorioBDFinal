package edu.co.sergio.mundo.vo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author Jhoan Saavedra
 */
public class Persona implements Serializable {

    protected long idPersona;
    protected String nombre;

    public Persona() {
    }

    public Persona(long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     *
     * @return .
     */
    public long getIdPersona() {
        return idPersona;
    }

    /**
     *
     * @param idPersona .
     */
    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     *
     * @return .
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre .
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
