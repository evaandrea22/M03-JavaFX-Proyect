package Controlador;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Vistas/LoginVista.fxml"));
            
            //CARGAR LA VENTANA
            Pane root = (Pane) loader.load();
            
            //CARGAR LA SCENE
            Scene scene = new Scene(root);
            
            //SETEAR LA SCENE Y MOSTRAR
            primaryStage.setScene(scene);
            primaryStage.show();
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
