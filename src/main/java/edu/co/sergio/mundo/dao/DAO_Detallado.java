package edu.co.sergio.mundo.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.util.List;
import edu.co.sergio.mundo.vo.Inventario; 
import java.net.URISyntaxException;
/**
 *
 * @author PC02
 */
public class DAO_Detallado {
 private final Connection connection;

    public DAO_Detallado() throws SQLException, ClassNotFoundException, URISyntaxException {
        connection = Conexion.getConnection();
    }

    /**
     * Funcion que permite añadir todo el detallado de una venta, y a su vez a
     * cada producto de inventario de cierto supermercado se le quita lo que se
     * vendio.
     *
     * @param detallado arreglo.
     * @param idVenta .
     * @throws SQLException .
     * @throws java.lang.ClassNotFoundException
     */
    public void addAll(List<Inventario> detallado, int idVenta) throws SQLException, ClassNotFoundException, URISyntaxException {
       
        for (Inventario det : detallado) {
            String query = "insert into Detallado (idVenta,idProd,cantidadProd)" + " values (?,?,?)";
            PreparedStatement preparedStmt;
            try {
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, idVenta);
                preparedStmt.setString(2, det.getProducto().getCodigoBarras());
                preparedStmt.setInt(3, det.getCantidad());
                preparedStmt.execute();

                DAO_Inventario daoInventario = new DAO_Inventario();
                Inventario prosuper = daoInventario.buscar(det.getProducto().getCodigoBarras()+det.getSupermercado().getIdSM());
                
                System.out.println(prosuper.getCantidad());
                prosuper.setCantidad(prosuper.getCantidad() - det.getCantidad());
                daoInventario.actualizar(prosuper);
                
                preparedStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
