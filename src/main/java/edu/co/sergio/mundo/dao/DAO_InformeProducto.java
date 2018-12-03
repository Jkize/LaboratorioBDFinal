package edu.co.sergio.mundo.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.co.sergio.mundo.vo.InformeProducto;
import java.net.URISyntaxException;

/**
 *
 * @author sergi
 */
public class DAO_InformeProducto {

    private Connection conexion;

    public DAO_InformeProducto() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
    }

   
    public List<InformeProducto> crearInforme(String mes, String ano, String tienda) {
        List<InformeProducto> infoProds = new ArrayList<InformeProducto>();
        String query = "SELECT idProd,SUM(cantidadProd) as cantidad FROM Detallado JOIN Venta USING(idVenta)  WHERE year(fecha)=" + ano + " and month(fecha)=" + mes + " and idSM='" + tienda + "' GROUP BY(idProd) ";
        String query2 = "SELECT SUM(cantidadProd) FROM Detallado JOIN Venta USING(idVenta) WHERE year(fecha)=" + ano + " and month(fecha)=" + mes + " and idSM='" + tienda + "'";
        try {
            Statement st2 = conexion.createStatement();
            ResultSet rs2 = st2.executeQuery(query2);
            int total = 0;
            while (rs2.next()) {
                total = (int) (rs2.getDouble("SUM(cantidadProd)"));
            }
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            String id_producto = "";
            int venta = 0;
            float porcentage = 0;
            if (!rs.wasNull()) {
                while (rs.next()) {

                    InformeProducto infoProd = new InformeProducto();
                    venta = rs.getInt("cantidad");
                    porcentage = (100*venta / total);
                    id_producto = rs.getString("idProd");
                    
                    String queryy = "SELECT nombre FROM producto WHERE codigoBarras = "+id_producto;
                    Statement stt = conexion.createStatement();
                    ResultSet rss = stt.executeQuery(queryy);
                    rss.next();
                    id_producto = rss.getString("nombre");
                     
                    //System.out.println(String.format("%.2f", porcentage));
                    infoProd.setId_producto(id_producto);
                    infoProd.setPorcentage(porcentage);
                    infoProd.setVenta(venta); 
                    infoProds.add(infoProd);
                }
            } else {
                infoProds = new ArrayList<InformeProducto>();
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return infoProds;
    }
    
}
