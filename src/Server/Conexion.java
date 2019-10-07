/*
 * Tomada de https://fernando-gaitan.com.ar/conectar-java-con-mysql-en-netbeans/
 * 05 de septiembre de 2019
 * Modified by Master Degree Students
 */
package Server;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author david
 */
public class Conexion {
    //Cadena de conexion
    private static Connection cnx = null;

    public Conexion() {
    }
    
    /**
     * Método constructor
     */
    public static Connection obtener() throws SQLException, ClassNotFoundException {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection("jdbc:mysql://localhost/msc", "root", "");
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
        }
        return cnx;
    }
    
    /**
     * Método para cerrar la conexion
     */
    public static void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }
    
    public static int insertarTrabajador(int clave, String name, int sald, int diasl) throws SQLException{
        try {
            obtener();
            try{
                PreparedStatement consulta = cnx.prepareStatement("SELECT * FROM empleados WHERE clave = ?" );
                consulta.setInt(1, clave);
                ResultSet resultado = consulta.executeQuery();
                resultado.next();
                int cl = resultado.getInt("clave");
                return 1;
            } catch (SQLException ex) {
                String cadsql = "insert into empleados values(?,?,?,?,?)";
                PreparedStatement consulta = cnx.prepareStatement(cadsql);
                consulta.setInt(1, clave);
                consulta.setString(2, name);
                consulta.setFloat(3, sald);
                consulta.setInt(4, diasl);
                int total = sald*diasl;
                consulta.setFloat(5, total);
                int res = consulta.executeUpdate();
                return 0;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return 2;
        }
    }
    
    public static ResultSet ReadAll() throws SQLException{
        try {
            obtener();
            PreparedStatement consulta = cnx.prepareStatement("select * from empleados");
            ResultSet res=consulta.executeQuery();
            return res;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ResultSet getEmpleado(int fila) throws SQLException{
        try {
            obtener();
            PreparedStatement consulta = cnx.prepareStatement("select * from empleados where clave="+fila);
            ResultSet res=consulta.executeQuery();
            res.next();
            return res;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static int editarTrabajador(int clave, String name, int sald, int diasl) throws SQLException{
        try {
            obtener();
            String cadsql = "update empleados set nombre=?, sald=?, diasl=?, total=? where clave=?";
            PreparedStatement consulta = cnx.prepareStatement(cadsql);
            consulta.setString(1, name);
            consulta.setFloat(2, sald);
            consulta.setInt(3, diasl);
            int total = sald*diasl;
            consulta.setFloat(4, total);
            consulta.setInt(5, clave);
            int res = consulta.executeUpdate();
            return 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }
    
    public static void borrarTrabajador(int clave) throws SQLException{
        try {
            obtener();
            String cadsql = "delete from empleados where clave="+clave;
            PreparedStatement consulta = cnx.prepareStatement(cadsql);
            int res = consulta.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
