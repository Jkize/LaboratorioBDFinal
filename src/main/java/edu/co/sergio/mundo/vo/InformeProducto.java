package edu.co.sergio.mundo.vo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author sergi
 */
public class InformeProducto implements Serializable {

    String id_producto;
    int venta;
    float porcentage;

    public InformeProducto() {
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }

    public float getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(float porcentage) {
        this.porcentage = porcentage;
    }
    
}
