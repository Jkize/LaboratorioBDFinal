package edu.co.sergio.mundo.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.co.sergio.mundo.vo.Producto;
import java.net.URISyntaxException;

/**
 *
 * @author kathe
 */
public class DAO_Producto {

    private Connection conexion;

    public DAO_Producto() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
    }

    public boolean crear(Producto prod) throws SQLException {
        boolean Resultado = false;
        String query = "insert into Producto " + " values (?,?)";
        PreparedStatement Smt = null;
        try {
            Smt = conexion.prepareStatement(query);
            Smt.setString(1, prod.getCodigoBarras());
            Smt.setString(2, prod.getNombreProducto());

            if (Smt.executeUpdate() > 0) {
                Resultado = true;
            }

            Smt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Resultado;

    }

    public Producto Buscar(String id) {
        Producto pro = null;

        try {
            PreparedStatement Smt = conexion.prepareStatement("select * from Producto where codigoBarras=?");
            Smt.setString(1, id);
            ResultSet rs = Smt.executeQuery();

            if (rs.next()) {
                pro = new Producto();
                pro.setCodigoBarras(rs.getString("codigoBarras"));
                pro.setNombreProducto(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pro;
    }

    public boolean actualizar(Producto ob) throws SQLException {

        Boolean Resultado = false;

        String query = "update Producto set nombre=? where codigoBarras=?";
        PreparedStatement Smt = null;

        try {
            Smt = conexion.prepareStatement(query);

            Smt.setString(1, ob.getNombreProducto());
            Smt.setString(2, ob.getCodigoBarras());
            if (Smt.executeUpdate() > 0) {
                Resultado = true;
            }
            Smt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Resultado;

    }

    public boolean eliminar(String id) throws SQLException {

        boolean result = false;
        String query = "delete from Producto where codigoBarras=?";
        PreparedStatement Smt = null;

        try {
            Smt = conexion.prepareStatement(query);
            Smt.setString(1, id);

            if (Smt.executeUpdate() > 0) {
                result = true;
            }

            Smt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

}
