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
public class Detallado implements Serializable {

    private Venta venta;
    private Producto producto; 
    private int cantProd;

    public Detallado() {
    }

    public Detallado(Venta venta, Producto ProSuper, int cantp) {
        this.venta = venta;
        this.producto = ProSuper; 
        this.cantProd = cantp;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantProd() {
        return cantProd;
    }

    public void setCantProd(int cantProd) {
        this.cantProd = cantProd;
    }
 

}
