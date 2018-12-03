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
public class VentasVendedor implements Serializable {

   String idVendedor;
    double totalventas;
    float porcentaje;

    public VentasVendedor() {
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }
    
    public double getTotalventas() {
        return totalventas;
    }

    public void setTotalventas(double totalventas) {
        this.totalventas = totalventas;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

}
