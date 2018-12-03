package edu.co.sergio.mundo.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.co.sergio.mundo.vo.Inventario;
import edu.co.sergio.mundo.vo.Producto; 
import java.net.URISyntaxException;

/**
 *
 * @author PC02
 */
public class DAO_Inventario {

    private final Connection connection;
    private DAO_Producto dao_prod;
    private DAO_Supermercado dao_super;

    public DAO_Inventario() throws SQLException, ClassNotFoundException, URISyntaxException {
        connection = Conexion.getConnection();
        dao_prod = new DAO_Producto();
        dao_super= new DAO_Supermercado();
    }

    public boolean crear(Inventario producto) throws SQLException, ClassNotFoundException {

        boolean result = false;
        if (dao_prod.crear(producto.getProducto()) || dao_prod.Buscar(producto.getCodigoBarras()) != null) {
            String query = " insert into Inventario " + " values (?,?,?,?,?)";
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, producto.getIdInventario());
                preparedStmt.setInt(2, producto.getCantidad());
                preparedStmt.setDouble(3, producto.getPrecio());
                preparedStmt.setString(4, producto.getProducto().getCodigoBarras());
                preparedStmt.setString(5, producto.getSupermercado().getIdSM());
                if (preparedStmt.executeUpdate() > 0) {
                    result = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Inventario buscar(String idProSuper) throws SQLException, ClassNotFoundException {
        Inventario prosuper = null;
        String query = "SELECT cantidad,precio, idProducto, idSM FROM Inventario WHERE idInventario='" + idProSuper + "'";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                prosuper = new Inventario(idProSuper, rs.getInt("cantidad"), rs.getDouble("precio"), dao_prod.Buscar(rs.getString("idProducto")), dao_super.buscar(rs.getString("idSM")));
            }
            if (prosuper != null) {
                query = "SELECT nombre FROM Producto WHERE codigoBarras=" + prosuper.getProducto().getCodigoBarras();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    prosuper.getProducto().setNombreProducto(rs.getString("nombre"));
                }
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prosuper;

    }

    public boolean actualizar(Inventario det) throws SQLException {

        boolean result = false;
        String query = "update Inventario set cantidad=?, precio=? where idInventario = ? ";
        PreparedStatement preparedStmt = null;

        if (dao_prod.actualizar(det.getProducto())) {

            try {
                System.out.println(det.getCantidad() + " " + det.getPrecio() + " " + det.getIdInventario());
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, det.getCantidad());
                preparedStmt.setDouble(2, det.getPrecio());
                preparedStmt.setString(3, det.getIdInventario());
                if (preparedStmt.executeUpdate() > 0) {
                    result = true;

                }
                preparedStmt.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean eliminar(String codigoB) throws SQLException {
        boolean result = false;
        String query = "delete from Inventario where idInventario = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, codigoB);
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Obtiene todo el inventario.
     *
     * @return ArrayList<Producto>: La lista de todos los productos que se
     * tienen registardos.
     * @throws IOException
     */
    public List<Inventario> getProductos(String idSM) throws SQLException {
        List<Inventario> productos = new ArrayList<Inventario>();
        String query = "SELECT Inventario.idProducto, Inventario.cantidad,  Producto.nombre, Inventario.precio FROM Inventario,Producto WHERE Producto.codigoBarras=Inventario.idProducto AND Inventario.idSM='" + idSM + "'";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Inventario prosuper = new Inventario();
                Producto pro = new Producto(rs.getString("idProducto"), rs.getString("nombre"));
                prosuper.setCantidad(rs.getInt("cantidad"));
                prosuper.setPrecio(rs.getDouble("precio"));
                prosuper.setProducto(pro);
                productos.add(prosuper);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

}
