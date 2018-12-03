package edu.co.sergio.mundo.vo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Jhoan Saavedra
 */
public class Caja implements Serializable{

    private String idCaja;
    private double montoActual;
    private Supermercado superMercado;
    private int disponible;

    public Caja(String idCaja, double montoActual,int disponible,Supermercado superMercado ) {
        this.idCaja = idCaja;
        this.montoActual = montoActual;
        this.superMercado = superMercado;
        this.disponible=disponible;
    }

    public Caja() {
    }

    public Caja(String idCaja) {
        this.idCaja = idCaja;
    }

    
    
    
    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
    
    

    public String getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(String idCaja) {
        this.idCaja = idCaja;
    }

    public double getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(double montoActual) {
        this.montoActual = montoActual;
    }

    public Supermercado getSuperMercado() {
        return superMercado;
    }

    public void setSuperMercado(Supermercado superMercado) {
        this.superMercado = superMercado;
    }

    @Override
    public String toString() {
        return "\"Caja\":{" + "\"idCaja\":\"" + idCaja + "\", \"montoActual\":" + montoActual + ", \"superMercado\":\"" + superMercado.getIdSM() + "\",\"disponible\":" + disponible + "}";
    }

    public static void main(String[] args) throws JSONException {
        Caja caja=new Caja("sd", 322, 3, new Supermercado("sd"));
        
        System.out.println("{"+caja.toString()+"}");
        JSONObject ob=new  JSONObject("{"+caja.toString()+"}");
        System.out.println(ob.getJSONObject("Caja").getInt("disponible"));
    }
}