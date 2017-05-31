/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitecontornos;

/**
 *
 * @author ricky_000
 */
public class SQLiteContornos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Conexion cnxn = new Conexion();
        cnxn.conectar();
        cnxn.insertar("123", "gonzalo", "sanchez");
        
    }
    
}
