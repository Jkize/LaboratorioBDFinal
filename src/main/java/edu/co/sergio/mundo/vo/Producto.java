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
public class Producto implements Serializable {

    private String codigoBarras;
    private String nombreProducto;
    

    public Producto() {
    }

    public Producto(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Producto(String codigoBarras, String nombreProducto) {
        this.codigoBarras = codigoBarras;
        this.nombreProducto = nombreProducto;

    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    

}
