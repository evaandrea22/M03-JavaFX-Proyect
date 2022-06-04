package Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {
    @FXML 
    private TextField userSingIn;
    @FXML 
    private VBox AnchorPane;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private PasswordField passSingIn;
    @FXML
    private TextField passSingInMask;
    @FXML
    private CheckBox verPassSingIn;
    @FXML
    private Label txtMsgError;
    @FXML
    private Button btnLimpiar;
   
    @FXML
    public void validateLogin() {
        try{
            String userName = userSingIn.getText();
            String pass = passSingInMask.getText();
            if("eva".equals(userName))
            {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/Vistas/MenuVista.fxml"));
                this.AnchorPane.getChildren().setAll(pane);
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
}
