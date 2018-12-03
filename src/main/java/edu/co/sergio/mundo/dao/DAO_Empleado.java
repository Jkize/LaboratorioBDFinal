/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;


import edu.co.sergio.mundo.vo.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 
import edu.co.sergio.mundo.vo.Caja; 
import edu.co.sergio.mundo.vo.Persona;
import edu.co.sergio.mundo.vo.Supermercado;
import java.net.URISyntaxException;

public class DAO_Empleado {

    private Connection conexion;
    private DAO_Persona dao_persona;
    private DAO_Caja dao_caja;
    private DAO_Supermercado daosuper;

    public DAO_Empleado() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
        dao_persona = new DAO_Persona();
         dao_caja = new DAO_Caja();
         daosuper= new DAO_Supermercado();
    }

    public boolean crear(Empleado emp) throws SQLException, ClassNotFoundException {
        boolean Resultado = false;
        if(this.dao_persona.Buscar(emp.getIdPersona())!=null){
            String query = " insert into Empleado (idEmpleado,contraseña,cargo,idCaja,idSM)" + " values (?,?,?,?,?)";
            PreparedStatement Smt = null;

            try {
                Smt = conexion.prepareStatement(query);
                Smt.setLong(1, emp.getIdPersona());
                Smt.setString(2, emp.getContrasena());
                Smt.setString(3, emp.getCargo());
                Smt.setString(4, (String) ((Caja) emp.getCaja()).getIdCaja());
                Smt.setString(5, emp.getSupermercado().getIdSM());

                if (Smt.executeUpdate() > 0) {
                    Resultado = true;
                    ActualizarCaja(emp.getCaja().getIdCaja(), 1);
                }
                Smt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
        if (this.dao_persona.crear(emp)) {
            String query = " insert into Empleado (idEmpleado,contraseña,cargo,idCaja,idSM)" + " values (?,?,?,?,?)";
            PreparedStatement Smt = null;

            try {
                Smt = conexion.prepareStatement(query);
                Smt.setLong(1, emp.getIdPersona());
                Smt.setString(2, emp.getContrasena());
                Smt.setString(3, emp.getCargo());
                Smt.setString(4, (String) ((Caja) emp.getCaja()).getIdCaja());
                Smt.setString(5, emp.getSupermercado().getIdSM());

                if (Smt.executeUpdate() > 0) {
                    Resultado = true;
                    ActualizarCaja(emp.getCaja().getIdCaja(), 1);
                }
                Smt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }

        return Resultado;
    }

    private boolean ActualizarCaja(String idcaja, int val) throws SQLException, ClassNotFoundException {

        boolean result = false;
        String query = "update Caja set disponible=? where idCaja=?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conexion.prepareStatement(query);
            preparedStmt.setInt(1, val);
            preparedStmt.setString(2, idcaja);
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Empleado Buscar(Long id, String idSM) throws SQLException {
        Empleado emp = null;
        try {
            PreparedStatement Smt = conexion.prepareStatement("select * from Empleado where idEmpleado=? AND idSM=?");
            Smt.setLong(1, id);
            Smt.setString(2, idSM);
            ResultSet rs = Smt.executeQuery();
            if (rs.next()) {
                emp = new Empleado();
                emp.setIdPersona(rs.getLong("idEmpleado"));
                emp.setContrasena(rs.getString("contraseña"));
                emp.setCargo(rs.getString("cargo"));
                emp.setCaja(dao_caja.buscar(rs.getString("idCaja"),rs.getString("idSM")));
                emp.setSupermercado(new Supermercado(rs.getString("idSM")));
            }

            if (emp != null) {
                Persona per = this.dao_persona.Buscar(emp.getIdPersona());
                emp.setNombre(per.getNombre());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emp;
    }

    public boolean actualizar(Empleado ob, String idSM) throws SQLException, ClassNotFoundException {

        Boolean Resultado = false;
        Empleado emps = Buscar(ob.getIdPersona(), idSM);
        if (emps != null) {
            String query = "update Empleado set contraseña=?, idcaja=? where idEmpleado=? AND idSM=?" + "AND cargo!='AD'";
            PreparedStatement Smt = null;
            try {
                Smt = conexion.prepareStatement(query);
                Smt.setString(1, ob.getContrasena());

                if (ob.getCaja() == null) {
                    Smt.setString(2, emps.getCaja().getIdCaja());
                } else {
                    Smt.setString(2, ob.getCaja().getIdCaja());
                }

                Smt.setLong(3, ob.getIdPersona());
                Smt.setString(4, idSM);

                if (Smt.executeUpdate() > 0) {
                    Resultado = true;
                    this.dao_persona.actualizar(ob);
                }
                Smt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (ob.getCaja() != null) {
                ActualizarCaja(ob.getCaja().getIdCaja(), 1);
                ActualizarCaja(emps.getCaja().getIdCaja(), 0);
            }

        }

        return Resultado;

    }

    public boolean eliminar(Long id, String idSM) throws SQLException, ClassNotFoundException {
        boolean result = false;

        Empleado emp = Buscar(id, idSM);
        if (emp != null) {
            String query = "delete from Empleado where idEmpleado=? AND idSM=?";
            PreparedStatement Smt = null;

            try {
                Smt = conexion.prepareStatement(query);
                Smt.setLong(1, id);
                Smt.setString(2, idSM);

                if (Smt.executeUpdate() > 0) {
                    result = true;
                }
                if (result) {
                    this.dao_persona.eliminar(id);
                }

                Smt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ActualizarCaja(emp.getCaja().getIdCaja(), 0);
        }
        return result;

    }

    public Empleado usuarioValido(Empleado empleado) throws ClassNotFoundException, URISyntaxException {
        System.out.println(empleado.getIdPersona()+" "+empleado.getContrasena());
        Empleado emp = null;
        try {
            PreparedStatement Smt = conexion.prepareStatement("select * from Empleado where idEmpleado=? AND contraseña=?");
            Smt.setLong(1, empleado.getIdPersona());
            Smt.setString(2, empleado.getContrasena());
            ResultSet rs = Smt.executeQuery();
            if (rs.next()) {
                System.out.println("Entra a validar USUARIO1");
                emp = new Empleado();
                emp.setIdPersona(rs.getLong("idEmpleado"));
                emp.setContrasena(rs.getString("contraseña"));
                emp.setCargo(rs.getString("cargo"));
                if(emp.getCargo().equals("VD")){
                emp.setCaja(dao_caja.buscar(rs.getString("idCaja"),rs.getString("idSM")));
                }else{
                    emp.setCaja(new Caja(rs.getString("idCaja")));
                }
                emp.setSupermercado(new Supermercado(rs.getString("idSM")));
            }
            if (emp != null) {
                DAO_Persona per = new DAO_Persona();
                Persona persona = per.Buscar(emp.getIdPersona());
                emp.setNombre(persona.getNombre());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emp;

    }

    
    public List<Empleado> getEmpleados(String idSM) throws SQLException {
        List<Empleado> emp = new ArrayList<Empleado>();
        String query = "SELECT * FROM EMPLEADO WHERE idSM='" + idSM + "'" + "AND cargo!='AD'"; 
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Empleado em = new Empleado();
                em.setIdPersona(rs.getLong("idEmpleado"));
                Persona per = this.dao_persona.Buscar(em.getIdPersona());
                em.setNombre(per.getNombre());
                em.setContrasena(rs.getString("contraseña"));
                em.setSupermercado(daosuper.buscar(rs.getString("idSM")));
                em.setCaja(dao_caja.buscar(rs.getString("idCaja"), rs.getString("idSM")));
                emp.add(em);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de Empleados");
            e.printStackTrace();
        }

        return emp;
    }
    
    
}
