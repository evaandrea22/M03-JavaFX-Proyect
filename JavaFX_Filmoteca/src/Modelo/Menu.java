package Modelo;

import java.sql.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Menu extends ConnectDb {
    //OBJETOS DE PELICULAS
    private final IntegerProperty idPelicula = new SimpleIntegerProperty();
    private final StringProperty tituloPelicula = new SimpleStringProperty();
    private final StringProperty fechaEstrenoPelicula = new SimpleStringProperty();
    private final IntegerProperty duracionPelicula = new SimpleIntegerProperty();
    private final IntegerProperty num_director = new SimpleIntegerProperty();
    
    //ATRIBUTOS
    private int id ;
    private String titulo;
    private Date fechaEstreno;
    private int duracion;
    private String director;
    private int id_director;

    //CONSTRUCTOR
    public Menu(){
        
    }

    public Menu(String titulo, Date fechaEstreno, int duracion) {
        this.titulo = titulo;
        this.fechaEstreno = fechaEstreno;
        this.duracion = duracion;
    }

    public Menu(int id, String titulo, Date fechaEstreno, int duracion, String director)throws SQLException, ClassNotFoundException {
        this.id = id;
        this.titulo = titulo;
        this.fechaEstreno = fechaEstreno;
        this.duracion = duracion;
        this.director = director;
    }

//    public Menu(int id, String titulo, Date fechaEstreno, int duracion, String director, int id_director) {
//        this.id = id;
//        this.titulo = titulo;
//        this.fechaEstreno = fechaEstreno;
//        this.duracion = duracion;
//        this.director = director;
//        this.id_director = id_director;
//    }
    
    //GETTERS AND SETTERS
    public int getId(){    
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Date fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {    
        this.duracion = duracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    
    
    // METODOS
    public static ObservableList<Menu> listAllPeliculas(Connection  connection) throws SQLException, ClassNotFoundException {
        String sql ="SELECT p.id_pelicula , p.titulo , p.fechaEstreno , p.duracion , d.nombre FROM peliculas p \n" +
                    "	JOIN peliculas_directores pd ON pd.fk_pelicula = p.id_pelicula\n" +
                    "    JOIN directores d ON d.id_director = pd.fk_director";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        ObservableList<Menu> lista = FXCollections.observableArrayList();
        Menu pelicula;
        while (result.next()){
            lista.add(
                    pelicula = new Menu(
                        result.getInt("id_pelicula"),
                        result.getString("titulo"),
                        result.getDate("fechaEstreno"),
                        result.getInt("duracion"),
                        result.getString("nombre")
                    )
                );
                pelicula.setId(result.getInt("id_pelicula"));
                pelicula.setTitulo(result.getString("titulo"));
                pelicula.setFechaEstreno(result.getDate("fechaEstreno"));
                pelicula.setDuracion(result.getInt("duracion"));
                pelicula.setDirector(result.getString("nombre"));
        }
        return lista;
    }
    
    //OK
    public static ObservableList<Menu> buscarPeliculas(Connection connection, String peli , String dir) throws SQLException, ClassNotFoundException {
        String sql = "";
        PreparedStatement stmt;
        ResultSet result = null;
        if(!peli.trim().isEmpty() && !dir.trim().isEmpty()){
            sql = "SELECT p.id_pelicula , p.titulo , p.fechaEstreno , p.duracion , d.nombre FROM peliculas p \n" +
                    " JOIN peliculas_directores pd ON pd.fk_pelicula = p.id_pelicula\n" +
                    " JOIN directores d ON d.id_director = pd.fk_director"+
                    " WHERE p.titulo Like ? OR " +
                    " d.nombre like ? " ;
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, peli + "%");
            stmt.setString(2, dir + "%");
            result = stmt.executeQuery();
        }else if(!peli.trim().isEmpty() && dir.trim().isEmpty()){
            sql = "SELECT p.id_pelicula , p.titulo , p.fechaEstreno , p.duracion , d.nombre FROM peliculas p \n" +
                    " JOIN peliculas_directores pd ON pd.fk_pelicula = p.id_pelicula\n" +
                    " JOIN directores d ON d.id_director = pd.fk_director"+
                    " WHERE p.titulo Like ? " ;
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, peli + "%");
            result = stmt.executeQuery();
        }else if(peli.trim().isEmpty() && !dir.trim().isEmpty()){
            sql = "SELECT p.id_pelicula , p.titulo , p.fechaEstreno , p.duracion , d.nombre FROM peliculas p \n" +
                    " JOIN peliculas_directores pd ON pd.fk_pelicula = p.id_pelicula\n" +
                    " JOIN directores d ON d.id_director = pd.fk_director"+
                    " WHERE d.nombre like ? ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, dir + "%");
            result = stmt.executeQuery();
        }
        ObservableList<Menu> lista = FXCollections.observableArrayList();
        Menu pelicula;
        while (result.next()){
            lista.add(
                    pelicula = new Menu(
                        result.getInt("id_pelicula"),
                        result.getString("titulo"),
                        result.getDate("fechaEstreno"),
                        result.getInt("duracion"),
                        result.getString("nombre")
                    )
                );
                pelicula.setId(result.getInt("id_pelicula"));
                pelicula.setTitulo(result.getString("titulo"));
                pelicula.setFechaEstreno(result.getDate("fechaEstreno"));
                pelicula.setDuracion(result.getInt("duracion"));
                pelicula.setDirector(result.getString("nombre"));
        }
        return lista;
    }

    public boolean exists() throws SQLException {
        String sql = "SELECT count(id_pelicula) as count FROM peliculas WHERE id_pelicula = ? ";
        PreparedStatement stmt = connectDb().prepareStatement(sql);
        stmt.setInt(1, this.id);
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

    public void nuevaPelicula() throws SQLException {
        String sql = "INSERT INTO peliculas(titulo, fechaEstreno, duracion)\n" +
                    "VALUES (?,?,?)";
        PreparedStatement stmt = connectDb().prepareStatement(sql);
        stmt.setString(1, this.titulo);
        stmt.setDate(2, this.fechaEstreno);
        stmt.setInt(3, this.duracion);
        stmt.execute();
        
    }
}
