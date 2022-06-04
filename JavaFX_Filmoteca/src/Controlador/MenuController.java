package Controlador;

import Modelo.ConnectDb;
import Modelo.Menu;
import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController<Item> extends ConnectDb implements Initializable{

    private AnchorPane rootPane;  //SALIR
    @FXML
    private Button salirButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button insertButton;
    @FXML
    private Button buscarButton;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn<Menu, Integer> clmId;
    @FXML
    private TableColumn<Menu, String> clmTitulo;
    @FXML
    private TableColumn<Menu, Date> clmFechaEstreno;
    @FXML
    private TableColumn<Menu, Integer> clmDuracion;
    @FXML
    private TableColumn<Menu, String> clmDirector;
    @FXML
    private TextField textTituloPelicula;
    @FXML
    private TextField textNombreDirector;  
    @FXML
    private TextField textTitulo;
    @FXML
    private DatePicker fechaEstreno;
    @FXML
    private TextField textDuracionPelicula;
    @FXML
    private ChoiceBox boxDirectorPelicula;
    @FXML
    private Button NewDirector;
    
    private String idPeli;
    ObservableList listaPelicula = FXCollections.observableArrayList();
    @FXML
    private Label error;
    @FXML
    private Label existe;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarLista();
    }  
    //OK
    public void iniciarLista() {
        try {
            listaPelicula = Menu.listAllPeliculas(connectDb());
        } catch (SQLException | ClassNotFoundException throwables) {
        }
        
        //enlazar la lista con la tabla
        tabla.setItems(listaPelicula);
        clmId.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("id"));
        clmTitulo.setCellValueFactory(new PropertyValueFactory<Menu,String>("titulo"));
        clmFechaEstreno.setCellValueFactory(new PropertyValueFactory<Menu,Date>("fechaEstreno"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("duracion"));
        clmDirector.setCellValueFactory(new PropertyValueFactory<Menu,String>("director"));
        
        // EVENTO PARA CAPTURAR DATOS DE LA FILA SELECCIONADA 
        tabla.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2){
                    Menu rowData = (Menu) row.getItem();
                    iniciarFormularios(rowData);
                }
            });
            return row;
        });
    }
    //OK
    @FXML
    public void buscar(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(textTituloPelicula.getText().isEmpty() == true && textNombreDirector.getText().isEmpty() == true){
            iniciarLista();
        }else{
            listaPelicula = Menu.buscarPeliculas(connectDb(),textTituloPelicula.getText(),textNombreDirector.getText());
            tabla.setItems(listaPelicula);
            clmId.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("id"));
            clmTitulo.setCellValueFactory(new PropertyValueFactory<Menu,String>("titulo"));
            clmFechaEstreno.setCellValueFactory(new PropertyValueFactory<Menu,Date>("fechaEstreno"));
            clmDuracion.setCellValueFactory(new PropertyValueFactory<Menu,Integer>("duracion"));
            clmDirector.setCellValueFactory(new PropertyValueFactory<Menu,String>("director"));
        }
    }
    //OK
    @FXML
    public void crearDirector(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AddDirectorVista.fxml"));
            Parent root = loader.load();
            AddDirectorController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void updPelicula(ActionEvent event) {
    }

    @FXML
    public void deletePelicula(ActionEvent event) {
    }

    @FXML
    public void addPelicula(ActionEvent actionEvent) throws IOException, SQLException{
        //INSTANCIAR EL DIRECTOR EN CUESTION
        Menu p = new Menu(
            (String)textTitulo.getText(),   
            java.sql.Date.valueOf(fechaEstreno.getValue()),
            (Integer)textDuracionPelicula.getLength()
//            (Integer) boxDirectorPelicula.getValue()
        );
        //COMPROBAR SI EXISTE
        boolean peliculaExist = p.exists();
        
        if(peliculaExist){
            existe.setVisible(true);
        }else if(textTitulo.getText().isEmpty() == true || textDuracionPelicula.getText().isEmpty() == true || fechaEstreno.getValue() == null ){
            error.setVisible(true);
        }else {
            //INSERTAR NUEVO DIRECTOR
            p.nuevaPelicula();
            //REFRESH
            existe.setVisible(false);
            error.setVisible(false);
            iniciarLista();
        }
    }
    //OK    
    @FXML
    public void volver(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.salirButton.getScene().getWindow();
        stage.close();
    }

    
    //FALTA FECHA Y DIRECTOR
    public void iniciarFormularios(Menu rowData) {
        idPeli = String.valueOf(rowData.getId());
        textTitulo.setText(String.valueOf(rowData.getTitulo()));
//        fechaEstreno.setValue(String.valueOf(rowData.getFechaEstreno()));
        textDuracionPelicula.setText(String.valueOf(rowData.getDuracion()));
//        boxDirectorPelicula.setValue(rowData.getNum_director());
    }
    
    

}

