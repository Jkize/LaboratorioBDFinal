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
import edu.co.sergio.mundo.vo.Supermercado;
import java.net.URISyntaxException;

/**
 *
 * @author PC02
 */
public class DAO_Supermercado {

    private final Connection conexion;

    public DAO_Supermercado() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
    }

    public Supermercado buscar(String idSP) throws SQLException {
        Supermercado sup = null;
        try {
            PreparedStatement Smt = conexion.prepareStatement("select * from Supermercado where idSM=?");
            Smt.setString(1, idSP);
            ResultSet rs = Smt.executeQuery();
            if (rs.next()) {

                sup = new Supermercado(idSP, rs.getString("nombreSM"), rs.getString("direccionSM"));

            }
        } catch (SQLException e) {
            System.out.println("ERROR");
            e.printStackTrace();
            
        }

        return sup;

    }

}
