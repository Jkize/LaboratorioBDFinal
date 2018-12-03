package edu.co.sergio.mundo.vo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jhoan Saavedra
 */
public class Venta implements Serializable {
 
    private Empleado vendedor;
    private Cliente cliente;
    private Supermercado superM;
    private Date date;
    private double monto;

    public Venta() {
    }

    public Venta(  Empleado vendedor,Supermercado superM, Cliente cliente, Date date, double monto) {
        
        this.superM= superM;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.date = date;
        this.monto = monto;
    } 
    
    public Empleado getVendedor() {
        return vendedor;
    }

    public void setVendedor(Empleado vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Supermercado getSuperM() {
        return superM;
    }

    public void setSuperM(Supermercado superM) {
        this.superM = superM;
    }

    
}
