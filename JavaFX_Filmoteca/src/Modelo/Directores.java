
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Directores extends ConnectDb {

    //OBJETOS DE DIRECTORES
    private final IntegerProperty  idDirector = new SimpleIntegerProperty();
    private final StringProperty nombreDirector = new SimpleStringProperty();
    
    //ATRIBUTOS
    private int id_director;
    private String nombre;
    
    //CONSTRUCTOR
    
    public Directores() {
    }

    public Directores(String nombre) {
        this.nombre = nombre;
    }

    public Directores(int id_director, String nombre) {
        this.id_director = id_director;
        this.nombre = nombre;
    }
    
    //GETTERS & SETTERS
    public int getId_director() {
        return id_director;
    }

    public void setId_director(int id_director) {
        this.id_director = id_director;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //EQUALS & HASHCODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id_director;
        hash = 43 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Directores other = (Directores) obj;
        if (this.id_director != other.id_director) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
  
    //METODOS
    public static ObservableList<Directores> listAllDirectores(Connection  connection) throws SQLException, ClassNotFoundException {
        String sql ="SELECT * FROM directores";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        ObservableList<Directores> lista = FXCollections.observableArrayList();
        Directores director;
        while (result.next()){
            lista.add(
                    director = new Directores(
                            result.getInt("id_director"),
                            result.getString("nombre")
                    )
                );
            director.setId_director(result.getInt("id_director"));
            director.setNombre(result.getString("nombre"));

        }
        return lista;
    }
    
    public static ObservableList<Directores> buscarDirectores(Connection connectDb, String text) throws SQLException {
        String sql ="SELECT * FROM directores"+
                    " WHERE nombre Like ? " ;
        PreparedStatement stmt = connectDb.prepareStatement(sql);
        stmt.setString(1,  text + "%" );
        System.out.println(stmt);
        ResultSet result = stmt.executeQuery();
        
        ObservableList<Directores> lista = FXCollections.observableArrayList();
        Directores director;
        while (result.next()){
            lista.add(
                    director = new Directores(
                            result.getInt("id_director"),
                            result.getString("nombre")
                    )
                );
            director.setId_director(result.getInt("id_director"));
            director.setNombre(result.getString("nombre"));
        }
        return lista;
    }

    public void actualizarDirector() throws SQLException {
        String sql = "UPDATE directores"+
                    " SET nombre = ? " +
                    " WHERE id_director = ?";
        PreparedStatement stmt = connectDb().prepareStatement(sql);
        stmt.setString(1, this.nombre);
        stmt.setInt(2, this.id_director);
        System.out.println("UPDATE "+stmt);
        stmt.execute();
    }

    public boolean exists() throws SQLException {
        String sql = "SELECT count(id_director) as count FROM directores WHERE id_director = ? ";
        PreparedStatement stmt = connectDb().prepareStatement(sql);
        stmt.setInt(1, this.id_director);
        ResultSet result = stmt.executeQuery();
        int count = 0;
        while(result.next()){
            count = result.getInt("count");
        }
        if(count != 0){
            return true;
        }else{
            return false;
        }
    }

    public void nuevoDirector() throws SQLException {
        String sql = "INSERT INTO directores(nombre)\n" +
                    "VALUES (?)";
        PreparedStatement stmt = connectDb().prepareStatement(sql);
        stmt.setString(1, this.nombre);
        stmt.execute();
        
    }

    public void deleteHabitacion() throws SQLException {
        String sql = "DELETE FROM directores WHERE id_director = ?";
        PreparedStatement stmt = connectDb().prepareStatement(sql);
        stmt.setInt(1, this.id_director);
        System.out.println("DELETE "+stmt);
        stmt.execute();
    }
    
}
