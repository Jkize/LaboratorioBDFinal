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
import java.util.HashMap;
import java.util.List;
import edu.co.sergio.mundo.vo.EvolucionVentas;
import java.net.URISyntaxException;

/**
 *
 * @author PC02
 */
public class DAO_EvolucionVentas {

    private Connection conexion;
    private HashMap<Integer, String> mes;

    public DAO_EvolucionVentas() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
        String[] Mes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        mes = new HashMap<Integer,String>();
        for (int i = 0; i < Mes.length; i++) {
            mes.put(i + 1, Mes[i]);
        }
    }

       public List<EvolucionVentas> crearInforme(String año, String tienda) {
        List<EvolucionVentas> infoMeses = new ArrayList<EvolucionVentas>();

        String query = "SELECT  month(fecha) as mes, SUM(monto) as ventames FROM Venta WHERE year(fecha)=" + año + "  and idSM='" + tienda + "' GROUP  BY (month(fecha)) ORDER BY month(fecha) ASC";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query); 
            double ventas = 0;
            if (!rs.wasNull()) {
                while (rs.next()) {
                    EvolucionVentas evo = new EvolucionVentas();
                    evo.setMes(this.mes.get(rs.getInt("mes")));
                    evo.setVentasMes(rs.getDouble("ventames")); 
                    infoMeses.add(evo);
                }
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return infoMeses;
    }

}
