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
import edu.co.sergio.mundo.vo.VentasVendedor;
import java.net.URISyntaxException;

/**
 *
 * @author PC02
 */
public class DAO_VentasVendedor {

    private Connection conexion;

    public DAO_VentasVendedor() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
    }

   public List<VentasVendedor> crearInforme(String mes, String año, String tienda) {
        List<VentasVendedor> infoVends = new ArrayList<VentasVendedor>();
        String query = "SELECT idVendedor,SUM(Monto) FROM Venta WHERE year(fecha)=" + año + " and month(fecha)=" + mes + " and idSM='" + tienda + "'GROUP BY(idVendedor)";
        String query1 = "SELECT SUM(Monto) FROM Venta WHERE year(fecha)=" + año + " and month(fecha)=" + mes + " and idSM='" + tienda + "'";
        try {
            Statement st1 = conexion.createStatement();
            ResultSet rs1 = st1.executeQuery(query1);
            double total = 0;
            while (rs1.next()) {
                total = rs1.getDouble("SUM(monto)");
            }
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            long idVendedor = 0;
            double totalventas = 0;
            float porcentaje = 0;

            while (rs.next()) {
                VentasVendedor vend = new VentasVendedor();
                idVendedor = rs.getLong("idVendedor"); 
                totalventas = rs.getDouble("SUM(monto)");
                porcentaje = (((float) (totalventas / total)) * 100);

                query="SELECT nombre FROM Persona Where idPersona="+idVendedor; 
                
                Statement st2=conexion.createStatement();
                ResultSet rs2=st2.executeQuery(query);
                
                while(rs2.next()){
                    vend.setIdVendedor(rs2.getString("nombre"));
                }
                 
                vend.setPorcentaje(porcentaje);
                vend.setTotalventas(totalventas);

                infoVends.add(vend);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return infoVends;
    }

}
