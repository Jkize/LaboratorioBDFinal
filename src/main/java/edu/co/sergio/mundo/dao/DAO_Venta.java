/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import edu.co.sergio.mundo.vo.Venta;
import java.net.URISyntaxException;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Venta {

    private final Connection connection;

    public DAO_Venta() throws SQLException, ClassNotFoundException, URISyntaxException {
        connection = Conexion.getConnection();
    }

    public boolean crear(Venta venta) throws SQLException {
        boolean result = false;
        String query = " insert into Venta (fecha,monto,idCliente,idVendedor,idSM)" + " values (?,?,?,?,?)";
        PreparedStatement preparedStmt=null;
        try {
            preparedStmt = connection.prepareStatement(query);
            java.sql.Date fechaSQL = new java.sql.Date(venta.getDate().getTime());
            preparedStmt.setDate(1, fechaSQL);
            preparedStmt.setDouble(2, venta.getMonto());
            preparedStmt.setLong(3, venta.getCliente().getIdPersona());
            preparedStmt.setLong(4, venta.getVendedor().getIdPersona());            
            preparedStmt.setString(5, venta.getSuperM().getIdSM());
            
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public int obtLastVenta() throws SQLException {
        int id = 0;

        String query = "SELECT idVenta FROM Venta ORDER BY idVenta DESC LIMIT 1";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("idVenta");
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

 
}
