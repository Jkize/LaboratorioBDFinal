package edu.co.sergio.mundo.dao;

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.co.sergio.mundo.vo.Cliente;
import edu.co.sergio.mundo.vo.Persona;
import java.net.URISyntaxException;

public class DAO_Cliente {

    private Connection conexion;
    private DAO_Persona dao_persona;

    public DAO_Cliente() throws SQLException, ClassNotFoundException, URISyntaxException {
        conexion = Conexion.getConnection();
        dao_persona = new DAO_Persona();
    }

    public boolean crear(Cliente cliente) throws SQLException {

        boolean Resultado = false;
        if(this.dao_persona.Buscar(cliente.getIdPersona())!=null){
            String query = " insert into cliente (idCliente,direccion)" + " values (?,?)";
            PreparedStatement Smt = null;
            try {
                Smt = conexion.prepareStatement(query);
                Smt.setLong(1, cliente.getIdPersona());
                Smt.setString(2, cliente.getDireccion());
                if (Smt.executeUpdate() > 0) {
                    Resultado = true;
                }
                Smt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
                
        if (this.dao_persona.crear(cliente)) {

            String query = " insert into cliente (idCliente,direccion)" + " values (?,?)";
            PreparedStatement Smt = null;
            try {
                Smt = conexion.prepareStatement(query);
                Smt.setLong(1, cliente.getIdPersona());
                Smt.setString(2, cliente.getDireccion());
                if (Smt.executeUpdate() > 0) {
                    Resultado = true;
                }
                Smt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
        return Resultado;
    }

    public Cliente Buscar(Long id) {
        Cliente clie = null;
        try {
            PreparedStatement Smt = conexion.prepareStatement("select * from Cliente where idCliente=?");
            Smt.setLong(1, id); 
            ResultSet rs = Smt.executeQuery();
            if (rs.next()) {
                clie=new Cliente();
                clie.setIdPersona(rs.getLong("idCliente")); 
                clie.setDireccion(rs.getString("direccion"));   
            }
          
            if (clie!= null) {
                Persona per = this.dao_persona.Buscar(clie.getIdPersona());
                clie.setNombre(per.getNombre());
              
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clie;
    }

    public boolean actualizar(Cliente ob) throws SQLException {

        Boolean Resultado = false;
        if (this.dao_persona.actualizar(ob)) {
            String query = "update Cliente set direccion=? where idCliente=?";
            PreparedStatement Smt = null;

            try {
                Smt = conexion.prepareStatement(query);
                Smt.setString(1, ob.getDireccion());
                Smt.setLong(2, ob.getIdPersona());

                if (Smt.executeUpdate() > 0) {
                    Resultado = true;
                }
                Smt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return Resultado;

    }

    public boolean eliminar(Long id) throws SQLException {

        boolean result = false;
        String query = "delete from Cliente where idCliente=?";
        PreparedStatement Smt = null;

        try {
            Smt = conexion.prepareStatement(query);
            Smt.setLong(1, (Long) id);

            if (Smt.executeUpdate() > 0) {
                result = true;
                this.dao_persona.eliminar(id);
            }
            Smt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

}
