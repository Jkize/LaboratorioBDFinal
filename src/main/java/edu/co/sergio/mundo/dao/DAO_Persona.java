package edu.co.sergio.mundo.dao;



import edu.co.sergio.mundo.vo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.net.URISyntaxException;

public class DAO_Persona {

    private Connection conexion;

    public DAO_Persona() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
    }

    public  boolean crear(Persona persona) throws SQLException {

        boolean Resultado = false;
        String query = " insert into persona (idPersona,nombre)" + " values (?,?)";
        PreparedStatement Smt = null;
        try {
            Smt = conexion.prepareStatement(query);
            Smt.setLong(1, persona.getIdPersona());
            Smt.setString(2, persona.getNombre());
            
            if (Smt.executeUpdate() > 0) {
                Resultado = true;
            }
            
            
            Smt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Resultado;
    }

    public Persona Buscar(Long id) {

        Persona per=null;

        try {
            PreparedStatement Smt = conexion.prepareStatement("select * from Persona where idPersona=?");
            Smt.setLong(1,  id);
            ResultSet rs = Smt.executeQuery();

            if (rs.next()) {
                per=new Persona();
                per.setIdPersona(rs.getLong("idPersona"));
                per.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return per;
    }

    public boolean actualizar(Persona ob) throws SQLException {

        Boolean Resultado = false;

        String query = "update Persona set nombre=? where idPersona=?";
        PreparedStatement Smt = null;

        try {
            Smt = conexion.prepareStatement(query);

            Smt.setString(1, ob.getNombre());
            Smt.setLong(2, ob.getIdPersona());
            if (Smt.executeUpdate() > 0) {
                Resultado = true;
            }
            Smt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Resultado;

    }

    public boolean eliminar(Long id) throws SQLException {

        boolean result = false;
        String query = "delete from Persona where idPersona=?";
        PreparedStatement Smt = null;

        try {
            Smt = conexion.prepareStatement(query);
            Smt.setLong(1,  id);
           
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