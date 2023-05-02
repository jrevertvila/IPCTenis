/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmlapplication.controller.RegisterController;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author joel
 */
public class InicioController implements Initializable {

    @FXML
    private Button login;
    @FXML
    private Button register;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
    }    

    @FXML
    private void loadLogin(ActionEvent event) throws IOException, ClubDAOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        LoginController controladorLogin = loader.getController();
       
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesión");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        
        
        if (controladorLogin.isLogged()) {
            System.out.println("ENTRA EN IS LOGGEDD");
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
            Parent root2 = loader2.load();
            javafxmlapplication.JavaFXMLApplication.setRoot(root2);
        }
    }

    @FXML
    private void loadRegister(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/Register.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        RegisterController controladorRegister = loader.getController();
        stage.setScene(scene);
        stage.setTitle("Registrarse");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
    }
    
    


    
}
