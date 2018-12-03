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
public class Empleado extends Persona implements Serializable {

    //  static final long serialVersionUID = 1L;
    private String contrasena;
    private String cargo;
    private Caja caja;
    private Supermercado supermercado;

    public Empleado(long idPersona) {
        super.idPersona = idPersona;
    }

    public Empleado() {
    }

    /**
     * getContrasena()
     *
     * @return contaseña.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     *
     * @param contrasena .
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;

    }

    /**
     *
     * @return .
     */
    public String getCargo() {
        return cargo;
    }

    /**
     *
     * @param cargo .
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public String getIdCaja(){
        return caja.getIdCaja();
    }
    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    @Override
    public String toString() {
        if(this.cargo.equals("VD")){
        return "\"Empleado\":{" + "\"contrasena\":\"" + contrasena + "\",\"cargo\":\"" + cargo + "\",\"caja\":\"" + caja.getIdCaja() + "\",\"supermercado\":\"" + supermercado.getIdSM() + "\","+"\"cedula\":"  +  super.idPersona+",\"nombre\":\""+super.nombre  +  "\"}";
        }else{
           return "\"Empleado\":{" + "\"contrasena\":\"" + contrasena + "\",\"cargo\":\"" + cargo + "\",\"caja\":\"" + "\",\"supermercado\":\"" + supermercado.getIdSM() + "\","+"\"cedula\":"  +  super.idPersona+",\"nombre\":\""+super.nombre  +  "\"}";
        
        }
    }
    
    public static void main(String[] args) {
        Empleado emp=new Empleado(2);
        emp.setContrasena("sd");
        emp.setCargo("hola");
        emp.setCaja(new Caja("caja"));
        emp.setNombre("holam undo");
        emp.setSupermercado(new Supermercado("holamundo"));
        
        System.out.println(emp.toString());
    }

}
