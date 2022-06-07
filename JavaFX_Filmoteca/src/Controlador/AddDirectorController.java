
package Controlador;

import Modelo.ConnectDb;
import Modelo.Directores;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class AddDirectorController<Item> extends ConnectDb implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnVolver;
    @FXML
    private TableColumn<Directores, Integer> colCodDirector;
    @FXML
    private TableColumn<Directores, String> colNombre;
    @FXML
    private TextField txtNombre;
    @FXML
    private TableView tblDirectores;
    @FXML
    private Button buscarButton;
    @FXML
    private Label errorNomDirector;
    @FXML
    private Label existDirector;
    
    private String idDirector;
    ObservableList listaDirector = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarLista();
    }    
    //OK
    public void iniciarLista() {
        try {
            listaDirector = Directores.listAllDirectores(connectDb());
        } catch (SQLException | ClassNotFoundException throwables) {
        }
        //enlazar la lista con la tabla
        tblDirectores.setItems(listaDirector);
        colCodDirector.setCellValueFactory(new PropertyValueFactory<Directores,Integer>("id_director"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Directores,String>("nombre"));
        
        // EVENTO PARA CAPTURAR DATOS DE LA FILA SELECCIONADA
        tblDirectores.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Directores rowData = (Directores) row.getItem();
                    iniciarFormularios(rowData);
                }
            });
            return row;
        });
    }
    //OK
    @FXML
    public void buscar(ActionEvent event) throws SQLException, ClassNotFoundException{
        if(txtNombre.getText().isEmpty() == true){
            errorNomDirector.setVisible(true);
            iniciarLista();
        }else{
            errorNomDirector.setVisible(false);
            listaDirector = Directores.buscarDirectores(connectDb(),txtNombre.getText());
            tblDirectores.setItems(listaDirector);
            colCodDirector.setCellValueFactory(new PropertyValueFactory<Directores,Integer>("id_director"));
            colNombre.setCellValueFactory(new PropertyValueFactory<Directores,String>("nombre"));
        }
    }
    //OK
    public  void iniciarFormularios(Directores rowData) {
        idDirector = String.valueOf(rowData.getId_director());
        txtNombre.setText(String.valueOf(rowData.getNombre()));
    }
    //OK
    @FXML
    public void editDirector(ActionEvent event) throws SQLException, ClassNotFoundException {
        //INSTANCIAR EL DIRECTOR EN CUESTION
        Directores d = new Directores(
                Integer.parseInt(idDirector),
                (String)txtNombre.getText()                
        );
        //ACTUALIZAR LOS DATOS
        d.actualizarDirector();
        //REFRESCAR LA TABLA
        iniciarLista();
    }
    //OK
    @FXML
    public void addDirector(ActionEvent event) throws SQLException {
        //INSTANCIAR EL DIRECTOR EN CUESTION
        Directores d = new Directores(
            (String)txtNombre.getText()                
        );
        //COMPROBAR SI EXISTE
        boolean directorExist = d.exists();
        
        if(directorExist){
            existDirector.setVisible(true);
        }else if(txtNombre.getText().isEmpty() == true){
            errorNomDirector.setVisible(true);
        }else {
            //INSERTAR NUEVO DIRECTOR
            d.nuevoDirector();
            //REFRESH
            existDirector.setVisible(false);
            errorNomDirector.setVisible(false);
            iniciarLista();
        }
    }
    //OK - falta el confirmar eliminacion
    @FXML
    public void deleteDirector(ActionEvent event)throws SQLException, ClassNotFoundException  {
        //INSTANCIAR EL DIRECTOR EN CUESTION
        Directores d = new Directores(
            Integer.parseInt(idDirector),
            (String)txtNombre.getText()                
        );
        //CONFIRMAR ELIMINACION
        //Alert alert = new Alert();    No se encuentra el alert
        
        // Eliminar habitación
        d.deleteDirector();
        //REFRESH
        iniciarLista();
    }
    //OK
    @FXML
    public void volver(ActionEvent event) {
        Stage stage = (Stage) this.btnVolver.getScene().getWindow();
        stage.close();
    }
    
}
