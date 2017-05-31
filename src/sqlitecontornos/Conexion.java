/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitecontornos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ricky_000
 */
public class Conexion {

    String ruta = "registro.db";
    Connection conexion;
    Statement consulta;

    /**
     * Constructor por defecto
     */
    public Conexion() {
    }

    /**
     * Construcotr que recibe ruta como string.
     * @param ruta 
     */
    public Conexion(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Metodo que conecta la base de datos.
     * @return 
     */
    public boolean conectar() {
        boolean buleano = false;
        try {
            Class.forName("org.sqlite.JDBC");
            buleano = true;
        } catch (ClassNotFoundException ex) {
            buleano = false;
            JOptionPane.showMessageDialog(null, "Error Drivers    " + ex.getMessage());
        }
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:" + ruta);
            consulta = conexion.createStatement();
            buleano = true;
        } catch (SQLException ex) {
            buleano = false;
            JOptionPane.showMessageDialog(null, "Error conexion    " + ex.getMessage());
        }
        return buleano;
    }

    /**
     * Metodo que registra usuarios.
     * @param id
     * @param nombre
     * @param apellido
     * @return 
     */
    public boolean insertar(String id, String nombre, String apellido) {
        boolean valor = true;
        conectar();
        try {
            String sql = "Insert into registro values('" + id + "','" + nombre + "','" + apellido + "');";
            consulta.executeUpdate(sql);
        } catch (SQLException ex) {
            valor = false;
            JOptionPane.showMessageDialog(null, "Error insertar" + ex.getMessage());
        } finally {
            try {
                consulta.close();
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return valor;
    }

    /**
     * Metodo que consulta la base de datos.
     * @throws SQLException 
     */
    public void consultar() throws SQLException {
        consulta = conexion.createStatement();
        ResultSet rs = consulta.executeQuery("select * from registro");
        while (rs.next()) {
            System.out.println("id=" + rs.getString("id"));
            System.out.println("nombre=" + rs.getString("nombre"));
            System.out.println("apellido=" + rs.getString("apellido"));
        }
    }

    /**
     * Metodo que elimina usuarios recibiendo la id.
     * @param id
     * @throws SQLException 
     */
    public void eliminar(String id) throws SQLException {
        String query = "DELETE FROM REGISTRO WHERE id = " + id;
        PreparedStatement rs = conexion.prepareStatement(query);
        rs.execute();
    }

    /**
     * Método que modifica usuario.
     * @param id
     * @param nombre
     * @param apellido
     * @throws SQLException 
     */
    public void modificar(String id, String nombre, String apellido) throws SQLException {
        conectar();
        String sql = "update registro set id='" + id + "',nombre='" + nombre + "',apellido='" + apellido + "' where dni='" + id + "';";
        consulta.executeUpdate(sql);
        conexion.commit();
    }

}
