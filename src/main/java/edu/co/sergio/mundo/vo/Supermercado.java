package edu.co.sergio.mundo.vo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author PC02
 */
public class Supermercado implements Serializable{
    private String idSM;
    private String nombreSM;
    private String direccionSM;

    public Supermercado() {
    }

    public Supermercado(String idSM, String nombreSM, String direccionSM) {
        this.idSM = idSM;
        this.nombreSM = nombreSM;
        this.direccionSM = direccionSM;
    }

    public Supermercado(String idSM) {
        this.idSM = idSM;
    }

    
    
    
    public String getIdSM() {
        return idSM;
    }

    public void setIdSM(String idSM) {
        this.idSM = idSM;
    }

    public String getNombreSM() {
        return nombreSM;
    }

    public void setNombreSM(String nombreSM) {
        this.nombreSM = nombreSM;
    }

    public String getDireccionSM() {
        return direccionSM;
    }

    public void setDireccionSM(String direccionSM) {
        this.direccionSM = direccionSM;
    }
    
}
