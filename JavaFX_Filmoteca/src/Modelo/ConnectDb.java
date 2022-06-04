
package Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
    private String db="filmoteca";
    private String url="jdbc:mysql://localhost:3306/"+db;
    private String user="root";
    private String pass="";
    private javax.swing.JOptionPane JOptionPane;

    public  ConnectDb(){
    }
    
    public Connection connectDb(){
        Connection connection = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (ClassNotFoundException | SQLException e){
            JOptionPane.showConfirmDialog(null, "No se ah podido conectar a la bd"+e);
        } 
        return connection;
    }

}
