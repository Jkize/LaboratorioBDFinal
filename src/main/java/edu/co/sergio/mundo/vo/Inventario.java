package edu.co.sergio.mundo.vo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.SQLException;

/**
 *
 * @author Jhoan Saavedra
 */
public class Inventario implements Serializable {
   private String idInventario;
   private int cantidad;
   private double precio;
   private Producto producto;
   private Supermercado supermercado;

    public Inventario() {
    }

    public Inventario(String idProSuper, int cantidad,double precio, Producto producto, Supermercado supermercado) throws SQLException, ClassNotFoundException {
        this.idInventario = idProSuper;
        this.cantidad = cantidad;
        this.producto = producto;
        this.supermercado = supermercado;
        this.precio= precio;
    }

    public Inventario(String idProSuper) {
        this.idInventario = idProSuper;
    }
 
    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idProSuper) {
        this.idInventario = idProSuper;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }
   
    public String getCodigoBarras() {
        return producto.getCodigoBarras();
    }

    public void setCodigoBarras(String codigoBarras) {
        producto.setCodigoBarras(codigoBarras);
    }

    public String getNombreProducto() {
        return producto.getNombreProducto();
    }

    public void setNombreProducto(String nombreProducto) {
        producto.setNombreProducto(nombreProducto);
    }
    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
