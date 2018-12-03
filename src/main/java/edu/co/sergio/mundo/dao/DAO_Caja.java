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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.co.sergio.mundo.vo.Caja; 
import java.net.URISyntaxException;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Caja {

    private final Connection connection;
    private DAO_Supermercado dao_supermercado;

    public DAO_Caja() throws SQLException, ClassNotFoundException, URISyntaxException {
        connection = Conexion.getConnection();
        dao_supermercado= new DAO_Supermercado();
    }

    public boolean crear(Caja ob) throws SQLException {
        boolean result = false; 
        String query = " insert into Caja (idCaja,montoActual,disponible,idSM)" + " values (?,?,?,?)";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, ob.getIdCaja());
            preparedStmt.setDouble(2, ob.getMontoActual());
            preparedStmt.setInt(3, 0);
            preparedStmt.setString(4, ob.getSuperMercado().getIdSM());
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public Caja buscar(Object id, String idSM) throws SQLException {
        Caja caja = null;
        String idCaja = (String) id; 
        String query = "SELECT * FROM Caja WHERE idCaja='" + idCaja + "' AND idSM='" + idSM + "' ";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                caja = new Caja(rs.getString("idCaja"), rs.getDouble("montoActual"), rs.getInt("disponible"),dao_supermercado.buscar(rs.getString("idSM")));

            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caja;
    }

    public boolean actualizarMonto(Caja ob, String idSM) throws SQLException {
        boolean result = false; 
        String query = "update Caja set montoActual=? where idCaja = ? AND idSM=?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setDouble(1, ob.getMontoActual()); 
            preparedStmt.setString(2, ob.getIdCaja());
            preparedStmt.setString(3, ob.getSuperMercado().getIdSM());
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public boolean eliminar(Object id, String idSM) throws SQLException {
        boolean result = false; 
        String query = "delete from Caja where idCaja = ? AND idSM=?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, (String) id);
            preparedStmt.setString(2, idSM);
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public List<Caja> getCajas(String idSM) throws SQLException {
        List<Caja> cajas = new ArrayList<Caja>();
        String query = "SELECT * FROM Caja WHERE idSM='" + idSM + "'"; 
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Caja caja = new Caja(rs.getString("idCaja"), rs.getDouble("montoActual"), rs.getInt("disponible"),dao_supermercado.buscar(rs.getString("idSM")));
                cajas.add(caja);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de Cajas");
            e.printStackTrace();
        }

        return cajas;
    }

    public List<String> disponibles(String idSM) throws SQLException {
        List<String> cajas = new ArrayList<String>();
        String query = "SELECT idCaja FROM Caja WHERE idSM='" + idSM + "' AND disponible=0"; 
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                cajas.add(rs.getString("idCaja"));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de Cajas");
            e.printStackTrace();
        }

        return cajas;
    }

}
